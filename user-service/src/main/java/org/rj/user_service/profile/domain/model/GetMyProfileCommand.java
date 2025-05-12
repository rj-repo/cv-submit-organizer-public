package org.rj.user_service.profile.domain.model;

public record GetMyProfileCommand(Long userId, String userEmail) {
}
