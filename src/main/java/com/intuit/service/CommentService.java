package com.intuit.service;

import com.intuit.enums.CommentType;
import com.intuit.enums.EntityType;
import com.intuit.exceptions.CommentsNotFoundException;
import com.intuit.models.entity.common.Reaction;
import com.intuit.models.entity.dataModel.CommentDataModel;
import com.intuit.models.request.CommentRequest;
import com.intuit.models.response.CommentResponse;
import com.intuit.respository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class CommentService {
    public static final int INITIAL_COUNT = 0;

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostService postService;

    /**
     * This API will add comments to a post as well as add reply to the comments
     */
    public void addComment(CommentRequest commentRequest) {
        validateIfParentExists(commentRequest.getType(), commentRequest.getParentId());

        LocalDateTime now = LocalDateTime.now();
        CommentDataModel comment = CommentDataModel.builder()
                .id(UUID.randomUUID().toString())
                .parentId(commentRequest.getParentId())
                .postId(commentRequest.getPostId())
                .type(CommentType.fromString(commentRequest.getType()).getValue())
                .userId(commentRequest.getUserId())
                .content(commentRequest.getContent())
                .createdAt(now)
                .updatedAt(now)
                .likes(new Reaction(INITIAL_COUNT, new HashSet<>()))
                .dislikes(new Reaction(INITIAL_COUNT, new HashSet<>()))
                .build();

        commentRepository.save(comment);
    }

    /**
     * This method validates whether the parent exists in DB. No content found will be returned if the parent is not found
     *
     * @param entityType type of entity: comment or reply
     * @param parentId post id in case of comment and comment id in case of replies
     */
    private void validateIfParentExists(String entityType, String parentId) {
        if (EntityType.COMMENT.toString().equalsIgnoreCase(entityType)) {
            postService.getPostById(parentId);
        } else if (EntityType.REPLY.toString().equalsIgnoreCase(entityType)) {
            getCommentById(parentId);
        }
    }

    /**
     * @param postId   post id
     * @param parentId will be post id in case of comments and comment id in case of replies
     * @param type     indicates whether the entity to be returned is comment or reply
     * @param page     current page number
     * @param size     page size
     * @return paginated comments for a post or replies for a comment/reply
     */
    public CommentResponse getComments(String postId, String parentId, String type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        //Using postId in the query since sharding will be done on postId in future
        Page<CommentDataModel> commentsPage = commentRepository.findAllByPostIdAndTypeAndParentIdOrderByUpdatedAtDesc(postId, type, parentId, pageable);

        if (null == commentsPage || commentsPage.getContent().isEmpty()) {
            log.error("Comments not found for parentId: {} and page: {}", parentId, page);
            throw new CommentsNotFoundException("Comments not found for parentId: " + parentId + "and page: " + page);
        }

        return CommentResponse.builder()
                .comments(commentsPage.getContent())
                .currPage(commentsPage.getNumber())
                .count(commentsPage.getNumberOfElements())
                .pageSize(commentsPage.getSize())
                .totalPages(commentsPage.getTotalPages())
                .build();
    }

    /**
     * The output of this method will be cached based on the key i.e. commentId
     *
     * @param commentId comment id
     * @return comment by id
     */
    @Cacheable(value = "CommentCache", key = "#commentId")
    public CommentDataModel getCommentById(String commentId) {
        Optional<CommentDataModel> comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) {
            log.error("Could not find comment with id: {}", commentId);
            throw new CommentsNotFoundException("Could not find comment with id: " + commentId);
        }
        return comment.get();
    }

}
