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
    private String languages;       // ✅ String simples
    private String technologies;    // ✅ String simples
    private String platforms;       // ✅ String simples
    private String level;
    private Double minimumRating;

    private List<CourseResponse> courses; // ← só courses é List
}
