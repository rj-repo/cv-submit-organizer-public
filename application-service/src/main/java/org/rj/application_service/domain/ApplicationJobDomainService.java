package org.rj.application_service.domain;

import org.rj.application_service.domain.model.ApplicationJob;
import org.rj.application_service.domain.model.command.ModifyJobApplicationCommand;

public class ApplicationJobDomainService {
    public ApplicationJob modify(ApplicationJob applicationJob,ModifyJobApplicationCommand modifyJobApplicationCommand){
        applicationJob.isModifyByOwner(modifyJobApplicationCommand);
        applicationJob.modify(modifyJobApplicationCommand);
        return applicationJob;
    }
}
