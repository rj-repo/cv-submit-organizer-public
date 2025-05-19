package org.rj.auth.verification.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.rj.auth.verification.domain.model.VerificationToken;
import org.rj.auth.verification.domain.ports.out.VerificationTokenRepoPort;
import org.rj.auth.verification.infrastructure.persistence.entity.VerificationTokenEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JpaVerificationTokenAdapterPort implements VerificationTokenRepoPort {

    private final JpaVerificationToken verificationTokenRepository;


    @Override
    public void save(VerificationToken verificationToken) {
        verificationTokenRepository.save(VerificationTokenEntity.toEntity(verificationToken));
    }

    @Override
    public Optional<VerificationToken> findByVerificationToken(String token) {
        return verificationTokenRepository.findByVerificationToken(token).map(VerificationTokenEntity::toAggregate);
    }
}
