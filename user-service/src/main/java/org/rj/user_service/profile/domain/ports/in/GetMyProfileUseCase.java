package org.rj.user_service.profile.domain.ports.in;

import org.rj.user_service.profile.domain.model.GetMyProfileCommand;
import org.rj.user_service.profile.domain.model.UserProfile;

public interface GetMyProfileUseCase {

    UserProfile getMyProfile(GetMyProfileCommand getMyProfileCommand);
}
