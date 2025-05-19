package org.rj.application_job.domain.ports.in;

import org.rj.application_job.domain.model.command.ModifyJobApplicationCommand;

public interface ModifyJobApplicationUseCase {

    void modify(ModifyJobApplicationCommand modifyJobApplicationCommand);
}
