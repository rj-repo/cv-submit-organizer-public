package org.rj.application_service.domain.ports.in;

import org.rj.application_service.domain.model.command.ModifyJobApplicationCommand;

public interface ModifyJobApplicationUseCase {

    void modify(ModifyJobApplicationCommand modifyJobApplicationCommand);
}
