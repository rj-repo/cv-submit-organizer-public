package org.rj.user_service.profile.application.history.usecase;

import lombok.RequiredArgsConstructor;
import org.rj.cvsubmitorganizer.common.UseCaseService;
import org.rj.user_service.profile.domain.history.model.UserProfileHistory;
import org.rj.user_service.profile.domain.history.ports.in.SaveProfileHistoryUseCase;
import org.rj.user_service.profile.domain.history.ports.out.UserProfileHistoryRepoPort;
import org.rj.user_service.profile.domain.model.ModifyUserProfileCommand;
import org.rj.user_service.profile.domain.model.UserProfile;

import java.util.List;

@UseCaseService
@RequiredArgsConstructor
public class SaveProfileHistoryService implements SaveProfileHistoryUseCase {

    private final UserProfileHistoryRepoPort userProfileHistoryRepo;

    @Override
    public void saveProfileHistory(UserProfile userProfile, ModifyUserProfileCommand modifyUserProfileCommand) {
        List<UserProfileHistory> byEmail = userProfileHistoryRepo.findByEmail(modifyUserProfileCommand.email());
        for(UserProfileHistory userProfileHistory : byEmail) {
            userProfileHistory.checkUserCompliance(userProfile.getId(), userProfileHistory.getId());
        }
        userProfileHistoryRepo.saveHistory(modifyUserProfileCommand.toAggregate(userProfile));
    }
}
