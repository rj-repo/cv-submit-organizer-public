package org.rj.application_service.domain.model.command;

import lombok.Builder;
import org.rj.application_service.domain.documents.model.command.ModifyDocumentCommand;

@Builder
public record ModifyJobApplicationCommand(
        ModifyDocumentCommand modifyDocumentCommand,
        String companyName,
        String jobOfferName,
        String statusApplication,
        Long userId,
        Long jobId
) {
}
