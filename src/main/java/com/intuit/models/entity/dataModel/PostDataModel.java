package com.intuit.models.entity.dataModel;

import com.intuit.models.entity.common.Content;
import com.intuit.models.entity.common.Reaction;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Post")
public class PostDataModel {
    @Id
    private String id;
    private Content content;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Reaction likes;
    private Reaction dislikes;
}
