package com.intuit.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReactionRequest {
    private String reactionType;
    private String userId;
    private String entityType;
}
