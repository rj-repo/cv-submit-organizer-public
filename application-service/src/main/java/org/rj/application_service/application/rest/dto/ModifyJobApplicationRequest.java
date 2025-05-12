package org.rj.application_service.application.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.rj.application_service.domain.documents.model.command.ModifyDocumentCommand;
import org.rj.application_service.domain.model.command.ModifyJobApplicationCommand;
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

    public ModifyDocumentCommand toDocumentCommand() {
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
