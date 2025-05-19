package org.rj.application_job.document.application.dto;

import lombok.Builder;
import org.rj.application_job.document.domain.model.DocumentApplication;

@Builder
public record DocumentApplicationResponse(Long documentApplicationId,
                                          String documentName,
                                          byte[] content) {

    public static DocumentApplicationResponse toModel(DocumentApplication documentApplication){
        return DocumentApplicationResponse.builder()
                .content(documentApplication.getContent())
                .documentName(documentApplication.getDocumentName())
                .documentApplicationId(documentApplication.getDocumentApplicationId().id())
                .build();
    }
}
