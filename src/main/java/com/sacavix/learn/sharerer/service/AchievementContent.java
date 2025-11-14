package com.sacavix.learn.sharerer.service;

import com.sacavix.learn.sharerer.persistence.UserAchievementProjection;
import com.sacavix.learn.sharerer.persistence.UserAchievementRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AchievementContent implements Shareable {

    private final UserAchievementRepository userAchievementRepository;

    @Value("${cdn.url.static.achievement}")
    private String staticUrl;

    @Value("${cdn.url.static.default-image}")
    private String staticUrlDefaultImage;

    public AchievementContent(UserAchievementRepository userAchievementRepository) {
        this.userAchievementRepository = userAchievementRepository;
    }

    @Override
    public ShareContent getContent(String id) {
        Optional<UserAchievementProjection> userAchievementProjection = this.userAchievementRepository
                .getUserAchievementData(id);

        if (userAchievementProjection.isPresent()) {
            UserAchievementProjection ua = userAchievementProjection.get();
            return new ShareContent(ua.getTitle(), ua.getSocialDescription(), staticUrl);
        }

        return new ShareContent("He alcanzado un nuevo logro",
                "He alcanzado un nuevo logro en SACAViX Learn y quiero compartirlo, únete y aprendamos programación juntos", staticUrlDefaultImage);

    }

    @Override
    public ShareableType getType() {
        return ShareableType.ACHIEVEMENT;
    }
}
