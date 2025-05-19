package org.rj.user.profile.domain.ports.in;

import org.rj.user.profile.domain.model.ModifyUserProfileCommand;

public interface ModifyProfileUseCase {

    void modifyProfile(Long id, ModifyUserProfileCommand modifyUserProfileCommand);
}
