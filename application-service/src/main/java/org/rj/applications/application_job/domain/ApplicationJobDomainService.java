package org.rj.applications.application_job.domain;

import org.rj.applications.application_job.domain.model.ApplicationJob;
import org.rj.applications.application_job.domain.model.command.ModifyJobApplicationCommand;

public class ApplicationJobDomainService {
    public ApplicationJob modify(ApplicationJob applicationJob,ModifyJobApplicationCommand modifyJobApplicationCommand){
        applicationJob.isModifyByOwner(modifyJobApplicationCommand);
        applicationJob.modify(modifyJobApplicationCommand);
        return applicationJob;
    }
}
