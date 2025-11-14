package com.sacavix.learn.sharerer.persistence;

import java.time.LocalDateTime;

public interface UserAchievementProjection {
     String getTitle();
     String getSocialDescription();
     String getIcon();
     LocalDateTime getEarnedAt();
}
