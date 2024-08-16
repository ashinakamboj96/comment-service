package com.intuit.respository;

import com.intuit.models.entity.dataModel.PostDataModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends MongoRepository<PostDataModel, String> {

    Optional<PostDataModel> findById(String id);

}
