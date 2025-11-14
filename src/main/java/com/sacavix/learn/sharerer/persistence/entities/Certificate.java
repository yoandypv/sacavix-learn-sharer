package com.sacavix.learn.sharerer.persistence.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "certificates")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quiz_id", nullable = false)
    private String quizId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "user_full_name", nullable = false)
    private String name;

    @Column(name = "user_email")
    private String email;

    @Column(name = "quiz_title", nullable = false)
    private String quizTitle;

    @Column(name = "generated_at", nullable = false)
    private LocalDateTime generatedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "certificate_hash", nullable = false, unique = true, length = 64)
    private String certificateHash;

    @Column(name = "was_generated", nullable = false)
    private boolean isGenerated;
}
