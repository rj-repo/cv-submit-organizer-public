package org.rj.applications.application_job.domain.model.command;

import lombok.Builder;
import org.rj.applications.application_job.domain.model.ApplicationJob;
import org.rj.applications.application_job.domain.model.StatusApplication;
import org.rj.applications.document.domain.model.command.AddDocumentCommand;
import org.rj.cvsubmitorganizer.common.UserProfileId;


@Builder
public record AddJobApplicationCommand(
        AddDocumentCommand addDocumentCommand,
        String companyName,
        String jobOfferName,
        Long userId
) {

    public ApplicationJob toModel(){
        return ApplicationJob.builder()
                .companyName(companyName)
                .statusApplication(StatusApplication.APPLIED)
                .jobOfferName(jobOfferName)
                .userProfileId(new UserProfileId(userId))
                .documentApplication(addDocumentCommand.toModel())
                .build();
    }

}
