package org.challengegroup.coursesrecomendation.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserPreferenceRequest {
    private List<String> languages;

    private List<String> technologies;

    private List<String> platforms;

    private String level;

    private Double minimumRating;

    private List<String> interestConcepts;
}
