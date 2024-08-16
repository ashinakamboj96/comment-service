package com.intuit.models.request;

import com.intuit.models.entity.common.Content;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    private String postId;
    private String parentId;
    private Content content;
    private String userId;
    private String type;
}
