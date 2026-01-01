package com.sacavix.learn.sharerer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class ShareHandler implements InitializingBean {

    private final List<Shareable> shareables;
    private Map<ShareableType, Shareable> shareableMap;

    public ShareHandler(List<Shareable> shareables) {
        this.shareables = shareables;
    }

    @Override
    public void afterPropertiesSet() {
        shareableMap = new HashMap<>();
        shareables.forEach(shareable -> shareableMap.put(shareable.getType(), shareable));
    }

    public ShareContent getContent(String type, String id) {
       ShareableType shareableType = Objects.nonNull(type) ? ShareableType.fromText(type): ShareableType.DEFAULT;
       return shareableMap.get(shareableType).getContent(id);
    }

}
