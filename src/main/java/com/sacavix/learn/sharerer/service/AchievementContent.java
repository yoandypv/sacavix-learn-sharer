package com.sacavix.learn.sharerer.service;

import org.springframework.stereotype.Service;

@Service
public class AchievementContent implements Shareable{

    @Override
    public ShareContent getContent(String id) {

        return new ShareContent("Primer quiz", "He alcanzado mi primero logro y quiero compartirlo", "https://substackcdn.com/image/fetch/$s_!bOY3!,w_1456,c_limit,f_webp,q_auto:good,fl_progressive:steep/https%3A%2F%2Fsubstack-post-media.s3.amazonaws.com%2Fpublic%2Fimages%2F90b287bc-2766-40a1-b6ea-d826c45b503b_973x667.png");
    }

    @Override
    public ShareableType getType() {
        return ShareableType.ACHIEVEMENT;
    }
}
