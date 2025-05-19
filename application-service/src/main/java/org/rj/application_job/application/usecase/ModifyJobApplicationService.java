package org.rj.application_job.application.usecase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rj.application_job.application.rest.ApplicationJobNotFound;
import org.rj.application_job.domain.ApplicationJobDomainService;
import org.rj.application_job.domain.model.ApplicationJob;
import org.rj.application_job.domain.model.command.ModifyJobApplicationCommand;
import org.rj.application_job.domain.ports.in.ModifyJobApplicationUseCase;
import org.rj.application_job.domain.ports.out.ApplicationJobRepoPort;
import org.rj.cvsubmitorganizer.common.UseCaseService;

@UseCaseService
@RequiredArgsConstructor
public class ModifyJobApplicationService implements ModifyJobApplicationUseCase {

    private final ApplicationJobRepoPort applicationJobRepoPort;
    private final ApplicationJobDomainService applicationJobDomainService;

    @Override
    @Transactional
    public void modify(ModifyJobApplicationCommand modifyJobApplicationCommand) {
        ApplicationJob applicationJob = applicationJobRepoPort.getJobById(modifyJobApplicationCommand.jobId())
                .orElseThrow(() -> new ApplicationJobNotFound("Not found job with id " + modifyJobApplicationCommand.jobId()));
        ApplicationJob modified = applicationJobDomainService.modify(applicationJob, modifyJobApplicationCommand);
        applicationJobRepoPort.saveJob(modified);
    }
}
