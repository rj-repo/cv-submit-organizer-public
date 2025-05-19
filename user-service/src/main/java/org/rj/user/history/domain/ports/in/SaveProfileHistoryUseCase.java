package org.rj.user.history.domain.ports.in;

import org.rj.user.profile.domain.model.ModifyUserProfileCommand;
import org.rj.user.profile.domain.model.UserProfile;

public interface SaveProfileHistoryUseCase {

    void saveProfileHistory(UserProfile userProfile, ModifyUserProfileCommand modifyUserProfileCommand);
}
