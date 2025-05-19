package org.rj.application_job.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.rj.application_job.application.rest.IllegalParameterRequest;
import org.rj.application_job.domain.model.ApplicationJob;
import org.rj.application_job.domain.model.command.GetUserApplicationsJobCommand;
import org.rj.application_job.domain.model.response.UserJobApplications;
import org.rj.application_job.domain.ports.out.ApplicationJobRepoPort;
import org.rj.application_job.infrastructure.persistence.entity.ApplicationJobEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ApplicationJobRepoAdapter implements ApplicationJobRepoPort {

    private final JpaApplicationJob jpaApplicationJob;

    @Override
    public void saveJob(ApplicationJob applicationJob) {
        jpaApplicationJob.save(ApplicationJobEntity.of(applicationJob));
    }

    @Override
    public void deleteJob(Long jobId) {
        jpaApplicationJob.deleteById(jobId);
    }

    @Override
    public Optional<ApplicationJob> getJobById(Long jobId) {
        return jpaApplicationJob.findById(jobId).map(ApplicationJobEntity::toModel);
    }

    @Override
    public UserJobApplications getAllJobs(GetUserApplicationsJobCommand request) {

        Pageable pageable = PageRequest.of(request.page().intValue(), request.pageSize().intValue());
        Page<ApplicationJobEntity> applicationJobEntityPage = jpaApplicationJob.findAllByUserId(request.userProfileId().id(), pageable);
        if (applicationJobEntityPage.getTotalPages() - 1 < request.page().intValue()) {
            throw new IllegalParameterRequest(String.format("Illegal request for pages - %d page requested and available is %d",
                    request.page().intValue(), applicationJobEntityPage.getTotalPages()));
        }
        return UserJobApplications.builder()
                .jobs(applicationJobEntityPage.get().map(ApplicationJobEntity::toModel).toList())
                .currentPage(request.page().intValue())
                .totalResults(applicationJobEntityPage.getTotalElements())
                .totalPages(applicationJobEntityPage.getTotalPages())
                .remainElements(calculateRemainElements(request.page().intValue(), request.pageSize().intValue(), applicationJobEntityPage.getTotalElements()))
                .build();
    }

    private int calculateRemainElements(int page, int size, long totalElements) {
        if (totalElements < size) {
            return size;
        }

        return (int) ((totalElements) - page * size);
    }
}
