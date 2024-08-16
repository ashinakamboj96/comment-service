package com.intuit.models.entity.dataModel;

import com.intuit.models.entity.common.Content;
import com.intuit.models.entity.common.Reaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Comment")
public class CommentDataModel {
    @Id
    private String id;
    private String parentId;
    private String postId;
    private String type;
    private String userId;
    private Content content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Reaction likes;
    private Reaction dislikes;
}
