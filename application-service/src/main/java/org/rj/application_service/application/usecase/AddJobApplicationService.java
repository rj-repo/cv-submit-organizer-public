package org.rj.application_service.application.usecase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rj.application_service.domain.model.command.AddJobApplicationCommand;
import org.rj.application_service.domain.ports.in.AddJobApplicationUseCase;
import org.rj.application_service.domain.ports.out.ApplicationJobRepoPort;
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
