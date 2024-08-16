package com.intuit.models.entity.common;

import com.intuit.enums.ContentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Content {
    //Only supporting TEXT for now
    private String type;
    private String data;
}
