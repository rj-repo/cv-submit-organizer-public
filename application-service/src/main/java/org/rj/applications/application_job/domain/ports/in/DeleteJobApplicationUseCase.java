package org.rj.applications.application_job.domain.ports.in;

public interface DeleteJobApplicationUseCase {

    void delete(Long jobId, Long userId);
}
