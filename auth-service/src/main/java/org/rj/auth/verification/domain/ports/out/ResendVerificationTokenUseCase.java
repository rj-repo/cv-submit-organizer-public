package org.rj.auth.verification.domain.ports.out;

public interface ResendVerificationTokenUseCase {

    void resendVerificationToken(String token);
}
