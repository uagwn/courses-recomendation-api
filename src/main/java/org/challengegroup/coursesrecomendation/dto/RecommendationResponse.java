package org.challengegroup.coursesrecomendation.dto;

import lombok.Data;
import java.util.List;

@Data
public class RecommendationResponse {
    private Integer user_id;
    private Integer total;
    private String algorithm;
    private List<CourseResponse> courses;
}
