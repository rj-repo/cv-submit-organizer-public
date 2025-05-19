package org.rj.user.profile.domain.model;

public record GetMyProfileCommand(Long userId, String userEmail) {
}
