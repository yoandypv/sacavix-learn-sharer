package com.sacavix.learn.sharerer.persistence;

import java.time.LocalDateTime;

public interface UserAchievementProjection {
     String getTitle();
     String getDescription();
     String getIcon();
     LocalDateTime getEarnedAt();
}
