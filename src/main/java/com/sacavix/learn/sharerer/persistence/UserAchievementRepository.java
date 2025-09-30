package com.sacavix.learn.sharerer.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {

    @Query(value = " SELECT a.title, " +
                   " a.description, " +
                   " a.icon, " +
                   " ua.earned_at " +
                   " FROM user_achievement ua INNER JOIN achievement a ON a.id=ua.achievement_text_id " +
                   " WHERE ua.share_token = :token", nativeQuery = true)
    public UserAchievementProjection getUserAchievementData(@Param("token") String token);

}
