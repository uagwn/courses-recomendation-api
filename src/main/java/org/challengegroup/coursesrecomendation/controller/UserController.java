package org.challengegroup.coursesrecomendation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.challengegroup.coursesrecomendation.dto.UserMeResponse;
import org.challengegroup.coursesrecomendation.dto.UserPreferenceRequest;
import org.challengegroup.coursesrecomendation.dto.UserPreferenceResponse;
import org.challengegroup.coursesrecomendation.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // GET /users/me
    @GetMapping("/me")
    public ResponseEntity<UserMeResponse> getMe(
            @AuthenticationPrincipal UserDetails userDetails) {

        log.info("GET /users/me - {}", userDetails.getUsername());
        return ResponseEntity.ok(userService.getMe(userDetails));
    }

    // GET /users/preferences
    @GetMapping("/preferences")
    public ResponseEntity<UserPreferenceResponse> getPreferences(
            @AuthenticationPrincipal UserDetails userDetails) {

        log.info("GET /users/preferences - {}", userDetails.getUsername());
        return ResponseEntity.ok(userService.getPreferences(userDetails));
    }

    // POST /users/preferences
    @PostMapping("/preferences")
    public ResponseEntity<UserPreferenceResponse> createPreferences(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserPreferenceRequest request) {

        log.info("POST /users/preferences - {}", userDetails.getUsername());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createPreferences(userDetails, request));
    }

    // PUT /users/preferences
    @PutMapping("/preferences")
    public ResponseEntity<UserPreferenceResponse> updatePreferences(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserPreferenceRequest request) {

        log.info("PUT /users/preferences - {}", userDetails.getUsername());
        return ResponseEntity.ok(userService.updatePreferences(userDetails, request));
    }
}
