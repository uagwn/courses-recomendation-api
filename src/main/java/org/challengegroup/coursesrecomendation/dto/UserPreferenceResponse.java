package org.challengegroup.coursesrecomendation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferenceResponse {
    private Long id;
    private Long userId;
    private List<String> languages;
    private List<String> technologies;
    private List<String> platforms;
    private String level;
    private Double minimumRating;
    private List<String> interestConcepts;
    private boolean hasEmbedding;
}
