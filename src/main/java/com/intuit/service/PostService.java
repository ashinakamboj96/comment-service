package com.intuit.service;

import com.intuit.exceptions.PostNotFoundException;
import com.intuit.models.entity.dataModel.PostDataModel;
import com.intuit.respository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    /**
     * The output of this method will be cached based on the key i.e. postId
     *
     * @param postId postId associated with the post
     * @return PostDataModel if found, throws PostNotFoundException otherwise
     */
    @Cacheable(value = "PostCache", key = "#postId")
    public PostDataModel getPostById(String postId) {
        Optional<PostDataModel> post = postRepository.findById(postId);

        if (post.isEmpty()) {
            log.error("Could not find post with id: {}", postId);
            throw new PostNotFoundException("Could not find post with id: " + postId);
        }
        return post.get();
    }

}
