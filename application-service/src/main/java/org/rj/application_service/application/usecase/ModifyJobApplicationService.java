package org.rj.application_service.application.usecase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rj.application_service.application.rest.ApplicationJobNotFound;
import org.rj.application_service.domain.ApplicationJobDomainService;
import org.rj.application_service.domain.model.ApplicationJob;
import org.rj.application_service.domain.model.command.ModifyJobApplicationCommand;
import org.rj.application_service.domain.ports.in.ModifyJobApplicationUseCase;
import org.rj.application_service.domain.ports.out.ApplicationJobRepoPort;
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
