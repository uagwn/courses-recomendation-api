package org.challengegroup.coursesrecomendation.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserPreferenceRequest {
    private String languages;
    private String technologies;
    private String platforms;
    private String level;
    private Double minimumRating;
}
