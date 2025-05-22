package org.rj.applications.application_job.domain.ports.in;

import org.rj.applications.application_job.domain.model.command.GetUserApplicationsJobCommand;
import org.rj.applications.application_job.domain.model.response.UserJobApplications;

public interface GetApplicationJobsUseCase {

    UserJobApplications getJobs(GetUserApplicationsJobCommand getUserApplicationsJobCommand);
}
