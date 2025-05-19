package org.rj.user.profile.domain.model;

import org.rj.user.history.domain.model.UserProfileHistory;

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
