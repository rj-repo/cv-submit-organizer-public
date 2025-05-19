package org.rj.application_job.application.rest.dto;

import lombok.Builder;
import org.rj.application_job.domain.model.response.UserJobApplications;

import java.util.List;

@Builder
public record UserJobApplicationResponse(List<ApplicationJobResponse> jobs,
                                         int pageSize,
                                         int currentPage,
                                         int remainElements,
                                         int totalPages,
                                         long totalResults) {

    public static UserJobApplicationResponse toResponse(UserJobApplications userJobApplications){
        return UserJobApplicationResponse.builder()
                .jobs(userJobApplications.jobs().stream().map(ApplicationJobResponse::toResponse).toList())
                .pageSize(userJobApplications.pageSize())
                .currentPage(userJobApplications.currentPage())
                .remainElements(userJobApplications.remainElements())
                .totalPages(userJobApplications.totalPages())
                .totalResults(userJobApplications.totalResults())
                .build();
    }
}
