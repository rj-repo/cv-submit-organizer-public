package org.rj.application_job.domain.model.command;

import org.rj.cvsubmitorganizer.common.UserProfileId;

public record GetUserApplicationsJobCommand(
        Long page,
        Long pageSize,
        UserProfileId userProfileId
) {
}
