package org.rj.applications.application_job.application.usecase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rj.applications.application_job.domain.model.command.AddJobApplicationCommand;
import org.rj.applications.application_job.domain.ports.in.AddJobApplicationUseCase;
import org.rj.applications.application_job.domain.ports.out.ApplicationJobRepoPort;
import org.rj.cvsubmitorganizer.common.UseCaseService;

@UseCaseService
@RequiredArgsConstructor
public class AddJobApplicationService implements AddJobApplicationUseCase {

    private final ApplicationJobRepoPort applicationJobRepoPort;

    @Override
    @Transactional
    public void addApplicationJob(AddJobApplicationCommand command) {
        applicationJobRepoPort.saveJob(command.toModel());
    }
}
