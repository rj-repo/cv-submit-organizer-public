package org.rj.application_service.domain.ports.in;

public interface DeleteJobApplicationUseCase {

    void delete(Long jobId, Long userId);
}
