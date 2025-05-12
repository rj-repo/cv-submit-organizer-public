package org.rj.application_service.domain.model.response;

import lombok.Builder;
import org.rj.application_service.domain.model.ApplicationJob;

import java.util.List;
@Builder
public record UserJobApplications(List<ApplicationJob> jobs,
                                  int pageSize,
                                  int currentPage,
                                  int remainElements,
                                  int totalPages,
                                  long totalResults
) {
}
