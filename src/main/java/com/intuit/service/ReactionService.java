package com.intuit.service;

import com.intuit.enums.EntityType;
import com.intuit.enums.ReactionType;
import com.intuit.models.entity.common.Reaction;
import com.intuit.models.entity.dataModel.CommentDataModel;
import com.intuit.models.entity.dataModel.PostDataModel;
import com.intuit.models.request.ReactionRequest;
import com.intuit.respository.CommentRepository;
import com.intuit.respository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReactionService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    /**
     * This method will add reactions(likes/dislikes) to post/comment/replies.
     *
     * @param entityId        - will be post id for comments and comment id for replies
     * @param reactionRequest request body
     */
    public void updateReaction(String entityId, ReactionRequest reactionRequest) {
        String entityType = reactionRequest.getEntityType();
        if (EntityType.POST.toString().equalsIgnoreCase(entityType)) {
            updatePostReaction(entityId, reactionRequest);
        } else if (EntityType.COMMENT.toString().equalsIgnoreCase(entityType)) {
            updateCommentReaction(entityId, reactionRequest);
        }
    }

    /**
     * This method will update the like/dislike for a post and also update the data in PostCache
     */
    @CacheEvict(value = "PostCache", key = "#entityId")
    private void updatePostReaction(String entityId, ReactionRequest reactionRequest) {
        //This can be updated to get the post from post service
        PostDataModel post = postService.getPostById(entityId);
        if (ReactionType.LIKE.toString().equalsIgnoreCase(reactionRequest.getReactionType())) {
            updatePostLikes(reactionRequest.getUserId(), post);
        } else if (ReactionType.DISLIKE.toString().equalsIgnoreCase(reactionRequest.getReactionType())) {
            updatePostDislikes(reactionRequest.getUserId(), post);
        }
    }

    /**
     * This method will update the like/dislike for a comment/reply and also update the data in CommentCache
     */
    @CacheEvict(value = "CommentCache", key = "#entityId")
    private void updateCommentReaction(String entityId, ReactionRequest reactionRequest) {
        CommentDataModel comment = commentService.getCommentDataModel(entityId);
        if (ReactionType.LIKE.toString().equalsIgnoreCase(reactionRequest.getReactionType())) {
            updateCommentLikes(reactionRequest.getUserId(), comment);
        } else if (ReactionType.DISLIKE.toString().equalsIgnoreCase(reactionRequest.getReactionType())) {
            updateCommentDislikes(reactionRequest.getUserId(), comment);
        }
    }

    /**
     * This method will add like to a comment/reply.
     * Note: If the person has already liked and clicks on like again, it will remove the like.
     * Also, if the person has disliked already and clicks on like now, it will add the like and remove the dislike for this person
     *
     */
    private void updateCommentLikes(String userId, CommentDataModel comment) {
        Reaction likes = comment.getLikes();
        Reaction dislikes = comment.getDislikes();
        // Remove the like if the person has already liked
        if (likes.getUserIds().contains(userId)) {
            likes.setCount(likes.getCount() - 1);
            likes.getUserIds().remove(userId);
            comment.setLikes(likes);
        }
        // Remove the dislike and add like if the person has previously disliked the comment
        else if (dislikes.getUserIds().contains(userId)) {
            dislikes.setCount(dislikes.getCount() - 1);
            dislikes.getUserIds().remove(userId);
            likes.setCount(likes.getCount() + 1);
            likes.getUserIds().add(userId);
            comment.setDislikes(dislikes);
            comment.setLikes(likes);
            // Increase the likes
        } else {
            likes.setCount(likes.getCount() + 1);
            likes.getUserIds().add(userId);
            comment.setLikes(likes);
        }
        commentRepository.save(comment);
    }

    /**
     * This method will add dislike to a comment/reply.
     * Note: If the person has already liked and clicks on like again, it will remove the like.
     * Also, if the person has disliked already and clicks on like now, it will add the like and remove the dislike for this person
     *
     */
    private void updateCommentDislikes(String userId, CommentDataModel comment) {
        Reaction likes = comment.getLikes();
        Reaction dislikes = comment.getDislikes();
        // Remove the dislike if the person has already disliked the comment
        if (dislikes.getUserIds().contains(userId)) {
            dislikes.setCount(dislikes.getCount() - 1);
            dislikes.getUserIds().remove(userId);
            comment.setDislikes(dislikes);
        }
        // Remove the like and add dislike if the person has previously liked the comment
        else if (likes.getUserIds().contains(userId)) {
            likes.setCount(likes.getCount() - 1);
            likes.getUserIds().remove(userId);
            dislikes.setCount(dislikes.getCount() + 1);
            dislikes.getUserIds().add(userId);
            comment.setLikes(likes);
            comment.setDislikes(dislikes);
            // Increase the dislikes
        } else {
            dislikes.setCount(dislikes.getCount() + 1);
            dislikes.getUserIds().add(userId);
            comment.setDislikes(dislikes);
        }
        commentRepository.save(comment);
    }

    /**
     * This method will add like to a post.
     * Note: If the person has already liked and clicks on like again, it will remove the like.
     * Also, if the person has disliked already and clicks on like now, it will add the like and remove the dislike for this person
     *
     */
    private void updatePostLikes(String userId, PostDataModel post) {
        Reaction likes = post.getLikes();
        Reaction dislikes = post.getDislikes();
        // Remove the like if the person has already liked the post
        if (likes.getUserIds().contains(userId)) {
            likes.setCount(likes.getCount() - 1);
            likes.getUserIds().remove(userId);
            post.setLikes(likes);
        }
        // Remove the dislike and add like if the person has previously disliked the post
        else if (dislikes.getUserIds().contains(userId)) {
            dislikes.setCount(dislikes.getCount() - 1);
            dislikes.getUserIds().remove(userId);
            likes.setCount(likes.getCount() + 1);
            likes.getUserIds().add(userId);
            post.setDislikes(dislikes);
            post.setLikes(likes);
            // Increase the likes
        } else {
            likes.setCount(likes.getCount() + 1);
            likes.getUserIds().add(userId);
            post.setLikes(likes);
        }
        postRepository.save(post);
    }

    /**
     * This method will add dislike to a post.
     * Note: If the person has already liked and clicks on like again, it will remove the like.
     * Also, if the person has disliked already and clicks on like now, it will add the like and remove the dislike for this person
     *
     */
    private void updatePostDislikes(String userId, PostDataModel post) {
        Reaction likes = post.getLikes();
        Reaction dislikes = post.getDislikes();
        // Remove the dislike if the person has already disliked the post
        if (dislikes.getUserIds().contains(userId)) {
            dislikes.setCount(dislikes.getCount() - 1);
            dislikes.getUserIds().remove(userId);
            post.setDislikes(dislikes);
        }
        // Remove the like and add dislike if the person has previously liked the post
        else if (likes.getUserIds().contains(userId)) {
            likes.setCount(likes.getCount() - 1);
            likes.getUserIds().remove(userId);
            dislikes.setCount(dislikes.getCount() + 1);
            dislikes.getUserIds().add(userId);
            post.setLikes(likes);
            post.setDislikes(dislikes);
            // Increase the dislikes
        } else {
            dislikes.setCount(dislikes.getCount() + 1);
            dislikes.getUserIds().add(userId);
            post.setDislikes(dislikes);
        }
        postRepository.save(post);
    }
}
