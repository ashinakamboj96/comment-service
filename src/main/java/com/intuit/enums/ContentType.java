package com.intuit.enums;

import lombok.Getter;

@Getter
public enum ContentType {
    TEXT("text"),
    IMAGE("image"),
    GIF("gif");

    public final String value;

    ContentType(String value) {
        this.value = value;
    }

}
