package org.rj.user_service.profile.domain.history.model;

import lombok.Builder;
import lombok.Getter;
import org.rj.user_service.profile.domain.model.UserProfileId;

@Getter
@Builder
public class UserProfileHistory {
    private UserProfileHistoryId id;
    private String previousEmail;
    private UserProfileId userId;

    public void checkUserCompliance(UserProfileId userId, UserProfileHistoryId userProfileHistoryId) {
        if (userProfileHistoryId.id().equals(userId.id())) {
            throw new IllegalArgumentException("User profile history id does not match");
        }
    }
}
