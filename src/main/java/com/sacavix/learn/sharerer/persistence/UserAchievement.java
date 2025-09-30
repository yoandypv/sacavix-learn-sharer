package com.sacavix.learn.sharerer.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "user_achievement")
@NoArgsConstructor
@AllArgsConstructor
public class UserAchievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}