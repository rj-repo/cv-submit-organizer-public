package org.rj.user_service.profile.domain.model;

import org.rj.user_service.profile.domain.history.model.UserProfileHistory;

public record ModifyUserProfileCommand(
        String email
) {

    public UserProfileHistory toAggregate(UserProfile userProfile) {
        return UserProfileHistory.builder()
                .previousEmail(userProfile.getEmail())
                .userId(userProfile.getId())
                .build();
    }
}
