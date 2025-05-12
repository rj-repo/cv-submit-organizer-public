package org.rj.user_service.profile.domain.ports.out;

import org.rj.user_service.profile.domain.model.ModifyUserProfileCommand;
import org.rj.user_service.profile.domain.model.RegisterAuthCommand;

public interface AuthServiceClientPort {

    void modifyAuthUser(Long userId, String newEmail, ModifyUserProfileCommand modifyUserProfileCommand);
    void registerAuthUser(RegisterAuthCommand registeredUser);
}
