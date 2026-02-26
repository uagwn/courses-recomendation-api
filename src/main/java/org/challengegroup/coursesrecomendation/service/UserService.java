package org.challengegroup.coursesrecomendation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.challengegroup.coursesrecomendation.dto.UserMeResponse;
import org.challengegroup.coursesrecomendation.dto.UserPreferenceRequest;
import org.challengegroup.coursesrecomendation.dto.UserPreferenceResponse;
import org.challengegroup.coursesrecomendation.entity.User;
import org.challengegroup.coursesrecomendation.entity.UserPreference;
import org.challengegroup.coursesrecomendation.exception.ResourceNotFoundException;
import org.challengegroup.coursesrecomendation.repository.UserPreferenceRepository;
import org.challengegroup.coursesrecomendation.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserPreferenceRepository preferenceRepository;
    private final ObjectMapper objectMapper;

    // ─── /users/me ────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public UserMeResponse getMe(UserDetails userDetails) {
        User user = findActiveUserByEmail(userDetails.getUsername());

        boolean hasPreferences = preferenceRepository.existsByUserId(user.getId());

        return UserMeResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .lastAccess(user.getLastAccess())
                .hasPreferences(hasPreferences)
                .build();
    }

    // ─── GET /users/preferences ────────────────────────────────────
    @Transactional(readOnly = true)
    public UserPreferenceResponse getPreferences(UserDetails userDetails) {
        User user = findActiveUserByEmail(userDetails.getUsername());

        UserPreference preference = preferenceRepository
                .findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Preferences not found for the user: " + user.getId()
                ));

        return toResponse(preference);
    }

    // ─── POST /users/preferences ───────────────────────────────────
    @Transactional
    public UserPreferenceResponse createPreferences(
            UserDetails userDetails,
            UserPreferenceRequest request) {

        User user = findActiveUserByEmail(userDetails.getUsername());

        if (preferenceRepository.existsByUserId(user.getId())) {
            throw new IllegalArgumentException(
                    "Preferences already exists Use PUT to update."
            );
        }

        UserPreference preference = UserPreference.builder()
                .user(user)
                .languages(toJson(request.getLanguages()))
                .technologies(toJson(request.getTechnologies()))
                .coursePlatformms(toJson(request.getPlatforms()))
                .level(request.getLevel())
                .minimumRating(
                        request.getMinimumRating() != null
                                ? request.getMinimumRating()
                                : 4.0
                )
                .interestConcepts(toJson(request.getInterestConcepts()))
                .build();

        preference = preferenceRepository.save(preference);
        log.info("Preferences created for the user: {}", user.getId());

        return toResponse(preference);
    }

    // ─── PUT /users/preferences ────────────────────────────────────
    @Transactional
    public UserPreferenceResponse updatePreferences(
            UserDetails userDetails,
            UserPreferenceRequest request) {

        User user = findActiveUserByEmail(userDetails.getUsername());

        UserPreference preference = preferenceRepository
                .findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Preferences not found. Use POST to create."
                ));

        if (request.getLanguages() != null) {
            preference.setLanguages(toJson(request.getLanguages()));
        }
        if (request.getTechnologies() != null) {
            preference.setTechnologies(toJson(request.getTechnologies()));
        }
        if (request.getPlatforms() != null) {
            preference.setCoursePlatformms(toJson(request.getPlatforms()));
        }
        if (request.getLevel() != null) {
            preference.setLevel(request.getLevel());
        }
        if (request.getMinimumRating() != null) {
            preference.setMinimumRating(request.getMinimumRating());
        }
        if (request.getInterestConcepts() != null) {
            preference.setInterestConcepts(toJson(request.getInterestConcepts()));
        }

        preference = preferenceRepository.save(preference);
        log.info("Preferences succesfully updated for the user: {}", user.getId());

        return toResponse(preference);
    }

    // ─── Helpers ──────────────────────────────────────────────────
    private User findActiveUserByEmail(String email) {
        return userRepository
                .findByEmailAndIsActive(email, true)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found: " + email
                ));
    }

    private String toJson(List<String> list) {
        if (list == null || list.isEmpty()) return null;
        try {
            return objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            log.error("Error to serialize the list: {}", e.getMessage());
            return null;
        }
    }

    private List<String> fromJson(String json) {
        if (json == null || json.isBlank()) return Collections.emptyList();
        try {
            String[] array = objectMapper.readValue(json, String[].class);
            return List.of(array);
        } catch (Exception e) {
            log.error("Error to serialize JSON: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    private UserPreferenceResponse toResponse(UserPreference preference) {
        return UserPreferenceResponse.builder()
                .id(preference.getId())
                .userId(preference.getUser().getId())
                .languages(fromJson(preference.getLanguages()))
                .technologies(fromJson(preference.getTechnologies()))
                .platforms(fromJson(preference.getCoursePlatformms()))
                .level(preference.getLevel())
                .minimumRating(preference.getMinimumRating())
                .interestConcepts(fromJson(preference.getInterestConcepts()))
                .hasEmbedding(preference.getPreferenceEmbedding() != null)
                .build();
    }
}
