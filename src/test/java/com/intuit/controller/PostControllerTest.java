package com.intuit.controller;

import com.intuit.exceptions.InvalidUuidException;
import com.intuit.exceptions.PostNotFoundException;
import com.intuit.models.entity.dataModel.PostDataModel;
import com.intuit.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @Test
    public void getPostByIdNullPostIdTest() {
        assertThrows(InvalidUuidException.class, () -> postController.getPostById(null));
        verifyNoInteractions(postService);
    }

    @Test
    public void getPostByIdInvalidPostIdTest() {
        assertThrows(InvalidUuidException.class, () -> postController.getPostById("123"));
        verifyNoInteractions(postService);
    }

    @Test
    public void getPostByIdNotFoundTest() {
        String postId = "2f727867-910e-4490-afee-f8bcb144061f";
        when(postService.getPostById(postId)).thenThrow(new PostNotFoundException("Could not find post with id: " + postId));
        assertThrows(PostNotFoundException.class, () -> postController.getPostById(postId));
        verify(postService, times(1)).getPostById(postId);
    }

    @Test
    public void getPostByIdValidTest() {
        String postId = "2f727867-910e-4490-afee-f8bcb144061f";
        PostDataModel postDataModel = new PostDataModel();
        when(postService.getPostById(postId)).thenReturn(postDataModel);
        assertThat(postController.getPostById(postId)).isEqualTo(ResponseEntity.ok().body(postDataModel));
        verify(postService, times(1)).getPostById(postId);
    }

}