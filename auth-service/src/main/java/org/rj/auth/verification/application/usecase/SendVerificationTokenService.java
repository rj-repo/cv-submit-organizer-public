package org.rj.auth.verification.application.usecase;

import lombok.RequiredArgsConstructor;
import org.rj.auth.user.domain.user.model.AuthUserId;
import org.rj.auth.verification.domain.VerificationTokenDomainService;
import org.rj.auth.verification.domain.model.VerificationToken;
import org.rj.auth.verification.domain.ports.out.EmailSenderProviderPort;
import org.rj.auth.verification.domain.ports.out.SendVerificationTokenPort;
import org.rj.auth.verification.domain.ports.out.VerificationTokenRepoPort;
import org.rj.cvsubmitorganizer.common.UseCaseService;

@UseCaseService
@RequiredArgsConstructor
public class SendVerificationTokenService implements SendVerificationTokenPort {

    private final VerificationTokenRepoPort verificationTokenRepoPort;
    private final EmailSenderProviderPort javaMailProvider;
    private final VerificationTokenDomainService verificationTokenDomainService;

    @Override
    public void sendToken(AuthUserId userId, String userEmail) {
        VerificationToken tokeVer;
        do {
            tokeVer = verificationTokenDomainService.createVerificationToken(userId);
        } while (verificationTokenRepoPort.findByVerificationToken(tokeVer.getToken()).isPresent());
        verificationTokenRepoPort.save(tokeVer);
        javaMailProvider.sendMail(userEmail, tokeVer.getToken());
    }

}
