package org.rj.user_service.profile.application.usecase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rj.cvsubmitorganizer.common.UseCaseService;
import org.rj.user_service.profile.application.exception.UserProfielAlreadyExists;
import org.rj.user_service.profile.application.rest.dto.UserProfileIdResponse;
import org.rj.user_service.profile.domain.model.SaveNewUserCommand;
import org.rj.user_service.profile.domain.model.UserProfile;
import org.rj.user_service.profile.domain.ports.in.SaveUserProfileUseCase;
import org.rj.user_service.profile.domain.ports.out.UserProfileRepoPort;

import java.util.Optional;

@RequiredArgsConstructor
@UseCaseService
public class SaveUserProfileService implements SaveUserProfileUseCase {

    private final UserProfileRepoPort profileRepoPort;
    private final SendInfoToAuthService sendInfoToAuthService;

    @Override
    @Transactional
    public void save(SaveNewUserCommand userProfile) {
        Optional<UserProfile> profile = profileRepoPort.findByEmail(userProfile.email());
        if (profile.isPresent()) {
            throw new UserProfielAlreadyExists("User " + userProfile.email() + " already exists");
        }
        UserProfileIdResponse send = sendInfoToAuthService.send(userProfile);
        profileRepoPort.save(userProfile.toAggregate(send.id()));
    }
}
