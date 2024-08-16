package com.intuit.controller;

import com.intuit.enums.ContentType;
import com.intuit.exceptions.InvalidUuidException;
import com.intuit.exceptions.PostNotFoundException;
import com.intuit.models.entity.common.Content;
import com.intuit.models.entity.common.Reaction;
import com.intuit.models.entity.dataModel.PostDataModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PostControllerIntegrationTest {
    @Autowired
    private PostController postController;

    @Test
    public void getPostByIdNullPostIdTest() {
        assertThrows(InvalidUuidException.class, () -> postController.getPostById(null));
    }

    @Test
    public void getPostByIdInvalidPostIdTest() {
        assertThrows(InvalidUuidException.class, () -> postController.getPostById("123"));
    }

    @Test
    public void getPostByIdNotFoundTest() {
        String postId = "2f727867-910e-4490-afee-f8bcb144061f";
        assertThrows(PostNotFoundException.class, () -> postController.getPostById(postId));
    }

    @Test
    public void getPostByIdValidTest() {
        //TODO: Do a repository call to save the post first
        String postId = "2f727867-910e-4490-afee-f8bcb144060f";
        PostDataModel postDataModel = PostDataModel.builder()
                .id(postId)
                .userId("ashina.kamboj")
                .content(new Content(ContentType.TEXT.getValue(), "Today is a good day"))
                .likes(new Reaction(1, Set.of("neha.rana")))
                .dislikes(new Reaction(1, Set.of("rahul.khanna")))
                .updatedAt(LocalDateTime.parse("2024-02-04T15:33:46"))
                .createdAt(LocalDateTime.parse("2024-02-04T15:33:46"))
                .build();

        assertThat(postController.getPostById(postId)).isEqualTo(ResponseEntity.ok().body(postDataModel));
    }


}
