package org.rj.user.profile.application.usecase;

import lombok.RequiredArgsConstructor;
import org.rj.cvsubmitorganizer.common.UseCaseService;
import org.rj.user.profile.application.exception.UserProfielNotFoundException;
import org.rj.user.profile.domain.model.GetMyProfileCommand;
import org.rj.user.profile.domain.model.UserProfile;
import org.rj.user.profile.domain.ports.in.GetMyProfileUseCase;
import org.rj.user.profile.domain.ports.out.UserProfileRepoPort;

@RequiredArgsConstructor
@UseCaseService
public class GetMyProfileService implements GetMyProfileUseCase {

    private final UserProfileRepoPort userProfileRepoPort;

    @Override
    public UserProfile getMyProfile(GetMyProfileCommand getMyProfileCommand) {
        return userProfileRepoPort.findByIdAndEmail(getMyProfileCommand.userId(), getMyProfileCommand.userEmail())
                .orElseThrow(() -> new UserProfielNotFoundException("User profile not found"));
    }
}
