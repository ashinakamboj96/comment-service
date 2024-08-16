package com.intuit.controller;

import com.intuit.constants.FieldNames;
import com.intuit.models.entity.dataModel.PostDataModel;
import com.intuit.service.PostService;
import com.intuit.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    /**
     * This API will get the post by post id
     */
    @GetMapping("/{postId}")
    public ResponseEntity<Object> getPostById(@PathVariable final String postId) {
        ValidationUtil.validateUuid(postId, FieldNames.POST_ID);
        PostDataModel post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }
}
