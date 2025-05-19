package org.rj.auth.verification.domain.ports.out;

import org.rj.auth.user.domain.user.model.AuthUserId;

public interface SendVerificationTokenPort {

    void sendToken(AuthUserId userId, String userEmail);

}
