package org.rj.application_service.infrastructure.persistence;

import org.rj.application_service.infrastructure.persistence.entity.ApplicationJobEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaApplicationJob extends JpaRepository<ApplicationJobEntity,Long> {
    Page<ApplicationJobEntity> findAllByUserId(Long userId, Pageable pageable);
}
