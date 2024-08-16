package com.intuit.controller;

import com.intuit.constants.FieldNames;
import com.intuit.enums.EntityType;
import com.intuit.enums.ReactionType;
import com.intuit.models.request.ReactionRequest;
import com.intuit.service.ReactionService;
import com.intuit.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/reactions")
public class ReactionController {

    @Autowired
    private ReactionService reactionService;

    /**
     * This API will add reactions(likes/dislikes) to post/comment/replies
     */
    @PatchMapping("/{entityId}")
    public ResponseEntity<Object> updateReaction(@PathVariable final String entityId, @RequestBody final ReactionRequest reactionRequest) {

        ValidationUtil.validateUuid(entityId, FieldNames.ENTITY_ID);
        ValidationUtil.validateNullRequestObject(reactionRequest, FieldNames.REACTION_REQUEST);
        ValidationUtil.validateEnum(reactionRequest.getReactionType(), FieldNames.REACTION_TYPE, ReactionType.class);
        ValidationUtil.validateEnum(reactionRequest.getEntityType(), FieldNames.ENTITY_TYPE, EntityType.class);
        //User id can be validated through user service call
        reactionService.updateReaction(entityId, reactionRequest);
        return ResponseEntity.ok().build();
    }
}
