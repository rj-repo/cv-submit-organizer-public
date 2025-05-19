package org.rj.auth.verification.infrastructure.persistence;

import org.rj.auth.verification.infrastructure.persistence.entity.VerificationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface JpaVerificationToken extends JpaRepository<VerificationTokenEntity, Long> {

    Optional<VerificationTokenEntity> findByVerificationToken(String verificationToken);
}
