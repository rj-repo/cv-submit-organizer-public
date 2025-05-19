package org.rj.user.history.application.usecase;

import lombok.RequiredArgsConstructor;
import org.rj.cvsubmitorganizer.common.UseCaseService;
import org.rj.user.history.domain.model.UserProfileHistory;
import org.rj.user.history.domain.ports.in.SaveProfileHistoryUseCase;
import org.rj.user.history.domain.ports.out.UserProfileHistoryRepoPort;
import org.rj.user.profile.domain.model.ModifyUserProfileCommand;
import org.rj.user.profile.domain.model.UserProfile;

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
