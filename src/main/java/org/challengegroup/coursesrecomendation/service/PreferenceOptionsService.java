package org.challengegroup.coursesrecomendation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.challengegroup.coursesrecomendation.dto.PreferenceOptionsResponse;
import org.challengegroup.coursesrecomendation.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PreferenceOptionsService {

    private final CourseRepository courseRepository;

    @Transactional(readOnly = true)
    public PreferenceOptionsResponse getOptions() {
        log.info("Fetching preference options from database");

        return PreferenceOptionsResponse.builder()
                .technologies(courseRepository.findDistinctTechnologies())
                .platforms(courseRepository.findDistinctPlatforms())
                .languages(courseRepository.findDistinctLanguages())
                .levels(List.of("iniciante", "intermediario", "avancado"))
                .build();
    }
}
