package org.rj.application_service.domain.ports.in;

import org.rj.application_service.domain.model.command.GetAllUsersJobsCommand;
import org.rj.application_service.domain.model.response.UserJobApplications;

public interface GetApplicationJobsUseCase {

    UserJobApplications getJobs(GetAllUsersJobsCommand getAllUsersJobsCommand);
}
