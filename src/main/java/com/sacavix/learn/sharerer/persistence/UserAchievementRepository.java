package com.sacavix.learn.sharerer.persistence;

import com.sacavix.learn.sharerer.persistence.entities.UserAchievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {

    @Query(value = " SELECT a.title, " +
                   " a.social_description, " +
                   " a.icon, " +
                   " ua.earned_at " +
                   " FROM user_achievement ua INNER JOIN achievement a ON a.id=ua.achievement_text_id " +
                   " WHERE ua.share_token = :token", nativeQuery = true)
    Optional<UserAchievementProjection> getUserAchievementData(@Param("token") String token);

}
