package org.rj.application_service.domain.ports.out;

import org.rj.application_service.domain.model.ApplicationJob;
import org.rj.application_service.domain.model.command.GetAllUsersJobsCommand;
import org.rj.application_service.domain.model.response.UserJobApplications;

import java.util.Optional;

public interface ApplicationJobRepoPort {

    void saveJob(ApplicationJob applicationJob);
    void deleteJob(Long jobId);
    Optional<ApplicationJob> getJobById(Long jobId);
    UserJobApplications getAllJobs(GetAllUsersJobsCommand command);
}
