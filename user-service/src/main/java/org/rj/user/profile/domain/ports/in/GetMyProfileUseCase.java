package org.rj.user.profile.domain.ports.in;

import org.rj.user.profile.domain.model.GetMyProfileCommand;
import org.rj.user.profile.domain.model.UserProfile;

public interface GetMyProfileUseCase {

    UserProfile getMyProfile(GetMyProfileCommand getMyProfileCommand);
}
