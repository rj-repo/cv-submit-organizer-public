package org.rj.applications.application_job.application.rest.dto;

import lombok.Builder;
import org.rj.applications.application_job.domain.model.ApplicationJob;
import org.rj.applications.document.application.dto.DocumentApplicationResponse;

@Builder
public record ApplicationJobResponse(Long applicationJobId,
                                     DocumentApplicationResponse document,
                                     String companyName,
                                     String statusApplication,
                                     String jobOfferName) {

    public static ApplicationJobResponse toResponse(ApplicationJob applicationJob){
        return ApplicationJobResponse.builder()
                .applicationJobId(applicationJob.getApplicationJobId().id())
                .document(DocumentApplicationResponse.toModel(applicationJob.getDocumentApplication()))
                .companyName(applicationJob.getCompanyName())
                .statusApplication(applicationJob.getStatusApplication().name())
                .jobOfferName(applicationJob.getJobOfferName())
                .build();
    }
}
