package org.challengegroup.coursesrecomendation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "courses", indexes = {
        @Index(name = "idx_technology", columnList = "technology"),
        @Index(name = "idx_platform", columnList = "platform"),
        @Index(name = "idx_language", columnList = "language")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String technology;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(length = 100, nullable = false)
    private String platform;

    @Column(length = 200, nullable = false)
    private String instructor;

    private Double rating;

    @Column(name = "colbert_description", columnDefinition = "TEXT")
    private String colbertDescription;

    @Column(name = "ontology_syllabus", columnDefinition = "TEXT")
    private String ontologySyllabus;

    @Column(length = 50, nullable = false)
    private String language;

    @Column(length = 500, nullable = false)
    private String link;

    @Column(name = "embedding_vector", columnDefinition = "TEXT")
    private String embeddingVector;
}
