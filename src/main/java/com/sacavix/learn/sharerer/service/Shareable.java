package com.sacavix.learn.sharerer.service;

public interface Shareable {
    ShareContent getContent(String id);
    ShareableType getType();
}
