package org.rj.user.profile.domain.ports.out;

import org.rj.user.profile.domain.model.ModifyUserProfileCommand;
import org.rj.user.profile.domain.model.RegisterAuthCommand;

public interface AuthServiceClientPort {

    void modifyAuthUser(Long userId, String newEmail, ModifyUserProfileCommand modifyUserProfileCommand);
    void registerAuthUser(RegisterAuthCommand registeredUser);
}
