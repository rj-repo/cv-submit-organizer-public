package org.rj.applications.application_job.application.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.rj.applications.application_job.domain.model.command.AddJobApplicationCommand;
import org.rj.applications.document.domain.model.command.AddDocumentCommand;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
public record AddJobApplicationRequest(
        @NotNull(message = "Message cannot be null")
        MultipartFile documentApplication,
        @NotNull(message = "Company name cannot be null")
        String companyName,
        @NotNull(message = "Job offer name cannot be null")
        String jobOfferName
) {

    public AddJobApplicationCommand toCommand(Long userId) {
        return AddJobApplicationCommand.builder()
                .addDocumentCommand(toDocumentCommand())
                .companyName(companyName)
                .jobOfferName(jobOfferName)
                .userId(userId)
                .build();
    }

    private AddDocumentCommand toDocumentCommand() {
        try {
            return AddDocumentCommand.builder()
                    .fileContent(documentApplication.getBytes())
                    .filename(documentApplication.getOriginalFilename())
                    .build();
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            throw new FileContentException("Cannot parse file");
        }
    }
}
