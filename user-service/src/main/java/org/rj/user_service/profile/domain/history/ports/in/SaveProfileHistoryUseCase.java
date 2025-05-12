package org.rj.user_service.profile.domain.history.ports.in;

import org.rj.user_service.profile.domain.model.ModifyUserProfileCommand;
import org.rj.user_service.profile.domain.model.UserProfile;

public interface SaveProfileHistoryUseCase {

    void saveProfileHistory(UserProfile userProfile, ModifyUserProfileCommand modifyUserProfileCommand);
}
