package org.rj.application_job.domain.ports.in;

import org.rj.application_job.domain.model.command.AddJobApplicationCommand;

public interface AddJobApplicationUseCase {

    void addApplicationJob(AddJobApplicationCommand command);
}
