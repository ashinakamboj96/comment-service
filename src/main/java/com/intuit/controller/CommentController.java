package com.intuit.controller;

import com.intuit.constants.FieldNames;
import com.intuit.enums.CommentType;
import com.intuit.enums.ContentType;
import com.intuit.models.request.CommentRequest;
import com.intuit.models.response.CommentResponse;
import com.intuit.service.CommentService;
import com.intuit.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * This API will add comments to a post as well as add reply to the comments
     */
    @PostMapping
    public ResponseEntity<Object> addComment(@RequestBody final CommentRequest commentRequest) {

        ValidationUtil.validateNullRequestObject(commentRequest, FieldNames.COMMENT_REQUEST);
        ValidationUtil.validateUuid(commentRequest.getPostId(), FieldNames.POST_ID);
        ValidationUtil.validateUuid(commentRequest.getParentId(), FieldNames.PARENT_ID);
        ValidationUtil.validateNullRequestObject(commentRequest.getContent(), FieldNames.CONTENT);
        ValidationUtil.validateEnum(commentRequest.getContent().getType(), FieldNames.CONTENT_TYPE, ContentType.class);
        ValidationUtil.validateEnum(commentRequest.getType(), FieldNames.COMMENT_TYPE, CommentType.class);

        //User id can be validated through user service call
        commentService.addComment(commentRequest);
        return ResponseEntity.ok().build();
    }

    /**
     * @param postId   post id
     * @param parentId will be post id in case of comments and comment id in case of replies
     * @param type     indicates whether the entity to be returned is comment or reply
     * @param page     current page number
     * @param size     page size
     * @return paginated comments for a post or replies for a comment/reply
     */
    @GetMapping
    public ResponseEntity<Object> getComments(@RequestParam final String postId, @RequestParam final String parentId,
                                              @RequestParam final String type,
                                              @RequestParam final int page, @RequestParam final int size) {

        ValidationUtil.validateUuid(postId, FieldNames.POST_ID);
        ValidationUtil.validateUuid(parentId, FieldNames.PARENT_ID);
        ValidationUtil.validatePageSize(size);
        ValidationUtil.validateEnum(type, FieldNames.COMMENT_TYPE, CommentType.class);

        CommentResponse comments = commentService.getComments(postId, parentId, type, page, size);
        return ResponseEntity.ok(comments);
    }
}
