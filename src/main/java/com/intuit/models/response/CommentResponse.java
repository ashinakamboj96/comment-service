package com.intuit.models.response;

import com.intuit.models.entity.dataModel.CommentDataModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private List<CommentDataModel> comments;
    private int currPage;
    private int pageSize;
    private int count;
    private int totalPages;
}
