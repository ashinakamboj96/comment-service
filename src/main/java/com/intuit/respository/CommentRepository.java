package com.intuit.respository;

import com.intuit.models.entity.dataModel.CommentDataModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends MongoRepository<CommentDataModel, String> {

    Optional<CommentDataModel> findById(String id);

    Page<CommentDataModel> findAllByPostIdAndTypeAndParentIdOrderByUpdatedAtDesc(String postId, String type, String parentId, Pageable pageable);
}
