package com.intuit.enums;

import lombok.Getter;

@Getter
public enum CommentType {
    COMMENT("comment"),
    REPLY("reply");

    public final String value;

    CommentType(String value) {
        this.value = value;
    }

    public static CommentType fromString(String type) {
        try {
            return CommentType.valueOf(type.toUpperCase());
        } catch (Exception ex) {
            return null;
        }
    }

}
