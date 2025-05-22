package org.rj.applications.application_job.application.usecase;

import lombok.RequiredArgsConstructor;
import org.rj.applications.application_job.domain.model.command.GetUserApplicationsJobCommand;
import org.rj.applications.application_job.domain.model.response.UserJobApplications;
import org.rj.applications.application_job.domain.ports.in.GetApplicationJobsUseCase;
import org.rj.applications.application_job.domain.ports.out.ApplicationJobRepoPort;
import org.rj.cvsubmitorganizer.common.UseCaseService;

@UseCaseService
@RequiredArgsConstructor
public class GetApplicationJobsService implements GetApplicationJobsUseCase {

    private final ApplicationJobRepoPort applicationJobRepoPort;

    @Override
    public UserJobApplications getJobs(GetUserApplicationsJobCommand getUserApplicationsJobCommand) {
        return applicationJobRepoPort.getAllJobs(getUserApplicationsJobCommand);

    }
}
