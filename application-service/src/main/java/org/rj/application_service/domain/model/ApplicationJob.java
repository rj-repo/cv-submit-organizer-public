package org.rj.application_service.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.rj.application_service.domain.documents.model.DocumentApplication;
import org.rj.application_service.domain.documents.model.command.ModifyDocumentCommand;
import org.rj.application_service.domain.model.command.ModifyJobApplicationCommand;
import org.rj.cvsubmitorganizer.common.UserProfileId;

import java.util.Objects;


@Getter
@Builder
@Setter
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
