package org.rj.user.history.domain.model;

import lombok.Builder;
import lombok.Getter;
import org.rj.user.profile.domain.model.UserProfileDomainException;
import org.rj.user.profile.domain.model.UserProfileId;

@Getter
@Builder
public class UserProfileHistory {
    private UserProfileHistoryId id;
    private String previousEmail;
    private UserProfileId userId;

    public void checkUserCompliance(UserProfileId userId, UserProfileHistoryId userProfileHistoryId) {
        if (userProfileHistoryId.id().equals(userId.id())) {
            throw new UserProfileDomainException("User profile history id does not match");
        }
    }
}
