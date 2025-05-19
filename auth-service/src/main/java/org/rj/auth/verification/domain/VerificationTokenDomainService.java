package org.rj.auth.verification.domain;

import org.rj.auth.user.domain.user.model.AuthUserId;
import org.rj.auth.verification.domain.model.VerificationToken;

public class VerificationTokenDomainService {

    public VerificationToken createVerificationToken(AuthUserId userId) {
        VerificationToken verificationToken = VerificationToken.builder()
                .userId(userId)
                .build();
        verificationToken.setExpirationDate(10);
        verificationToken.generateToken();
        return verificationToken;
    }

    public void generateNewToken(VerificationToken oldToken) {
        oldToken.generateToken();
        oldToken.setExpirationDate(10);
    }
}
