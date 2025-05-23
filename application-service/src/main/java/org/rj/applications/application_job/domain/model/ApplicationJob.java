package org.rj.applications.application_job.domain.model;

import lombok.Builder;
import lombok.Getter;
import org.rj.applications.application_job.domain.model.command.ModifyJobApplicationCommand;
import org.rj.applications.document.domain.model.DocumentApplication;
import org.rj.applications.document.domain.model.command.ModifyDocumentCommand;
import org.rj.cvsubmitorganizer.common.UserProfileId;

import java.util.Objects;


@Getter
@Builder
public class ApplicationJob {
    private ApplicationJobId applicationJobId;
    private UserProfileId userProfileId;
    private DocumentApplication documentApplication;
    private String companyName;
    private StatusApplication statusApplication;
    private String jobOfferName;

    public void modify(ModifyJobApplicationCommand command) {
        this.companyName = command.companyName() == null ? companyName : command.companyName();
        this.jobOfferName = command.jobOfferName() == null ? jobOfferName : command.jobOfferName();
        this.statusApplication = command.statusApplication() == null ?
                statusApplication :
                StatusApplication.getStatusApplication(command.statusApplication());

        modifyDocument(command.modifyDocumentCommand());
    }

    public void isModifyByOwner(ModifyJobApplicationCommand command) {
        if (!userProfileId.id().equals(command.userId())) {
            throw new ApplicationJobDomainException("User is not owner application job");
        }
    }

    private void modifyDocument(ModifyDocumentCommand modifyDocumentCommand) {
        if(Objects.isNull(modifyDocumentCommand)){
            return;
        }
        documentApplication.modify(modifyDocumentCommand);
    }
}
