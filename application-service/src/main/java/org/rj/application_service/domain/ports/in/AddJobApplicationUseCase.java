package org.rj.application_service.domain.ports.in;

import org.rj.application_service.domain.model.command.AddJobApplicationCommand;

public interface AddJobApplicationUseCase {

    void addApplicationJob(AddJobApplicationCommand command);
}
