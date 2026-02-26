package org.challengegroup.coursesrecomendation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "user_preferences", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id")
})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(columnDefinition = "TEXT")
    private String languages;

    @Column(columnDefinition = "TEXT")
    private String technologies;

    @Column(name = "platforms", columnDefinition = "TEXT")
    private String coursePlatformms;

    @Column(length = 50)
    private String level;

    @Column(name = "minimum_rating")
    @Builder.Default
    private Double minimumRating = 4.0;

    @Column(name = "interest_concepts", columnDefinition = "TEXT")
    private String interestConcepts;

    @Column(name = "preference_embedding", columnDefinition = "TEXT")
    private String preferenceEmbedding;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
