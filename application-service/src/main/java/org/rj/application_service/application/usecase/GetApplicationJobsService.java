package org.rj.application_service.application.usecase;

import lombok.RequiredArgsConstructor;
import org.rj.application_service.domain.model.command.GetAllUsersJobsCommand;
import org.rj.application_service.domain.model.response.UserJobApplications;
import org.rj.application_service.domain.ports.in.GetApplicationJobsUseCase;
import org.rj.application_service.domain.ports.out.ApplicationJobRepoPort;
import org.rj.cvsubmitorganizer.common.UseCaseService;

@UseCaseService
@RequiredArgsConstructor
public class GetApplicationJobsService implements GetApplicationJobsUseCase {

    private final ApplicationJobRepoPort applicationJobRepoPort;

    @Override
    public UserJobApplications getJobs(GetAllUsersJobsCommand getAllUsersJobsCommand) {
        return applicationJobRepoPort.getAllJobs(getAllUsersJobsCommand);

    }
}
