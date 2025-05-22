package org.rj.applications.application_job.domain.model.command;

import lombok.Builder;
import org.rj.applications.document.domain.model.command.ModifyDocumentCommand;

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
