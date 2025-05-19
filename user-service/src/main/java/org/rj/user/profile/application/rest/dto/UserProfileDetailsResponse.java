package org.rj.user.profile.application.rest.dto;

import lombok.Builder;
import org.rj.user.profile.domain.model.UserProfile;

@Builder
public record UserProfileDetailsResponse(
        Long id,
        String email,
        String firstname,
        String surname
) {
    public static UserProfileDetailsResponse of(UserProfile userProfile) {
        return UserProfileDetailsResponse.builder()
                .email(userProfile.getEmail())
                .id(userProfile.getId().id())
                .firstname(userProfile.getFirstname())
                .surname(userProfile.getSurname())
                .build();
    }
}
