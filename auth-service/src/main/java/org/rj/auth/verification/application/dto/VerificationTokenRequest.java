package org.rj.auth.verification.application.dto;

import jakarta.validation.constraints.NotBlank;

public record VerificationTokenRequest(@NotBlank(message = "Token is blank") String token) {
}
