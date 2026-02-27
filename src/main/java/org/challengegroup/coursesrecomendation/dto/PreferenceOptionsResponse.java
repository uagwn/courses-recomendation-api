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
public class PreferenceOptionsResponse {
    private List<String> technologies;
    private List<String> platforms;
    private List<String> languages;
    private List<String> levels;
}
