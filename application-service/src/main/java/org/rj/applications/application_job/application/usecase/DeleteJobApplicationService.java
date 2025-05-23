package org.rj.applications.application_job.application.usecase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rj.applications.application_job.application.rest.ApplicationJobNotFound;
import org.rj.applications.application_job.domain.ports.in.DeleteJobApplicationUseCase;
import org.rj.applications.application_job.domain.ports.out.ApplicationJobRepoPort;
import org.rj.cvsubmitorganizer.common.UseCaseService;

@RequiredArgsConstructor
@UseCaseService
public class DeleteJobApplicationService implements DeleteJobApplicationUseCase {
    private final ApplicationJobRepoPort jobRepoPort;

    @Override
    @Transactional
    public void delete(Long jobId, Long userId) {
        jobRepoPort.getJobById(jobId)
                .orElseThrow(() -> new ApplicationJobNotFound("Not found job application"));
        jobRepoPort.deleteJob(jobId);
    }
}
