package org.rj.user_service.profile.domain.ports.in;

import org.rj.user_service.profile.domain.model.ModifyUserProfileCommand;

public interface ModifyProfileUseCase {

    void modifyProfile(Long id, ModifyUserProfileCommand modifyUserProfileCommand);
}
