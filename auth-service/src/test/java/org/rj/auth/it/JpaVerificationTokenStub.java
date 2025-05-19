package org.rj.auth.it;

import org.rj.auth.verification.infrastructure.persistence.entity.VerificationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
 interface JpaVerificationTokenStub extends JpaRepository<VerificationTokenEntity, Long> {
    Optional<VerificationTokenEntity> findByAuthUser_Id(Long authUserId);
}
