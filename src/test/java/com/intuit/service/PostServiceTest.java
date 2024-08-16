package com.intuit.service;

import com.intuit.exceptions.PostNotFoundException;
import com.intuit.models.entity.dataModel.PostDataModel;
import com.intuit.respository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private PostService postService;

    @Test
    public void getPostByIdValidTest() {
        String postId = "2f727867-910e-4490-afee-f8bcb144061f";
        PostDataModel postDataModel = new PostDataModel();
        when(postRepository.findById(postId)).thenReturn(Optional.of(postDataModel));
        assertThat(postService.getPostById(postId)).isEqualTo(postDataModel);
    }

    @Test
    public void getPostByIdNotFoundTest() {
        String postId = "2f727867-910e-4490-afee-f8bcb144061f";
        when(postRepository.findById(postId)).thenReturn(Optional.empty());
        assertThrows(PostNotFoundException.class, () -> postService.getPostById(postId));
    }

}