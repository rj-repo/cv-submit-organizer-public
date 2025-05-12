package org.rj.user_service.profile.domain.ports.in;

import org.rj.user_service.profile.domain.model.SaveNewUserCommand;

public interface SaveUserProfileUseCase {

    void save(SaveNewUserCommand userProfile);
}
