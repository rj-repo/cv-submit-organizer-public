package org.rj.user.profile.domain.ports.in;

import org.rj.user.profile.domain.model.SaveNewUserCommand;

public interface SaveUserProfileUseCase {

    void save(SaveNewUserCommand userProfile);
}
