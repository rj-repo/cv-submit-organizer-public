package org.rj.auth.verification.domain.ports.out;

import org.rj.auth.verification.domain.model.VerificationToken;

import java.util.Optional;

public interface VerificationTokenRepoPort {

    void save(VerificationToken verificationToken);

    Optional<VerificationToken> findByVerificationToken(String token);
}
