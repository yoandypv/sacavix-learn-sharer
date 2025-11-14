package com.sacavix.learn.sharerer.service;

import java.util.HashMap;
import java.util.Map;

public enum ShareableType {

    CERTIFICATE("quiz"),
    ACHIEVEMENT("achievement"),
    CAREER_PATH("career_path"),
    DEFAULT("default");

    String text;

    private static final Map<String, ShareableType> TEXT_TO_ENUM = new HashMap<>();

    ShareableType(String text) {
        this.text = text;
    }

    static {
        for (ShareableType type : ShareableType.values()) {
            TEXT_TO_ENUM.put(type.text, type);
        }
    }

    public static ShareableType fromText(String text) {
        return TEXT_TO_ENUM.getOrDefault(text, DEFAULT);
    }

}
