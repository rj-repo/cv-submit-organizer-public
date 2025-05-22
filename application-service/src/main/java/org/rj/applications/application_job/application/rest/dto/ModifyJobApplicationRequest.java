package org.rj.applications.application_job.application.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.rj.applications.application_job.domain.model.command.ModifyJobApplicationCommand;
import org.rj.applications.document.domain.model.command.ModifyDocumentCommand;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Slf4j
public record ModifyJobApplicationRequest(
        MultipartFile file,
        String companyName,
        String statusApplication,
        String jobOfferName,
        @NotNull(message = "Job id cannot be null")
        Long jobId
) {

    public ModifyJobApplicationCommand toCommand(Long userId){
        ModifyJobApplicationCommand.ModifyJobApplicationCommandBuilder builder = ModifyJobApplicationCommand.builder()
                .companyName(companyName)
                .jobOfferName(jobOfferName)
                .statusApplication(statusApplication)
                .jobId(jobId)
                .userId(userId);
        if(Objects.nonNull(file)){
            builder.modifyDocumentCommand(toDocumentCommand());
        }
        return builder.build();
    }

    private ModifyDocumentCommand toDocumentCommand() {
        try {
            return ModifyDocumentCommand.builder()
                    .fileContent(file.getBytes())
                    .filename(file.getOriginalFilename())
                    .build();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new FileContentException("Cannot parse file");
        }
    }
}
