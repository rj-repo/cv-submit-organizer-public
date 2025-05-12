package org.rj.application_service.domain.model.command;

import org.rj.cvsubmitorganizer.common.UserProfileId;

public record GetAllUsersJobsCommand(
        Long page,
        Long pageSize,
        UserProfileId userProfileId
) {
}
