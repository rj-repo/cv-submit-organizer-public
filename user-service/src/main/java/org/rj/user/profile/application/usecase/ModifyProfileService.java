package org.rj.user.profile.application.usecase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rj.cvsubmitorganizer.common.UseCaseService;
import org.rj.user.history.domain.ports.in.SaveProfileHistoryUseCase;
import org.rj.user.profile.application.exception.UserProfielNotFoundException;
import org.rj.user.profile.domain.model.ModifyUserProfileCommand;
import org.rj.user.profile.domain.model.UserProfile;
import org.rj.user.profile.domain.ports.in.ModifyProfileUseCase;
import org.rj.user.profile.domain.ports.out.AuthServiceClientPort;
import org.rj.user.profile.domain.ports.out.UserProfileRepoPort;

@RequiredArgsConstructor
@UseCaseService
public class ModifyProfileService implements ModifyProfileUseCase {

    private final UserProfileRepoPort userProfileRepoPort;
    private final SaveProfileHistoryUseCase saveProfileHistoryUseCase;
    private final AuthServiceClientPort authServiceClient;

    @Override
    @Transactional
    public void modifyProfile(Long id, ModifyUserProfileCommand modifyUserProfileCommand) {
        UserProfile profile = userProfileRepoPort.findById(id)
                .orElseThrow(() -> new UserProfielNotFoundException("User profile not found"));
        profile.isValueAlreadySet(modifyUserProfileCommand);
        saveProfileHistoryUseCase.saveProfileHistory(profile, modifyUserProfileCommand);
        authServiceClient.modifyAuthUser(id, profile.getEmail(), modifyUserProfileCommand);
        profile.updateProfile(modifyUserProfileCommand);
        userProfileRepoPort.save(profile);
    }
}