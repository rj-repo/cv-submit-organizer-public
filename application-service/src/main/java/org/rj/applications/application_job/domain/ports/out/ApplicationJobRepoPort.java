package org.rj.applications.application_job.domain.ports.out;

import org.rj.applications.application_job.domain.model.ApplicationJob;
import org.rj.applications.application_job.domain.model.command.GetUserApplicationsJobCommand;
import org.rj.applications.application_job.domain.model.response.UserJobApplications;

import java.util.Optional;

public interface ApplicationJobRepoPort {

    void saveJob(ApplicationJob applicationJob);
    void deleteJob(Long jobId);
    Optional<ApplicationJob> getJobById(Long jobId);
    UserJobApplications getAllJobs(GetUserApplicationsJobCommand command);
}
