package org.rj.auth.user.application.usecase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rj.auth.user.application.exception.AuthUserAlreadyVerifiedException;
import org.rj.auth.user.application.exception.AuthUserNotFoundException;
import org.rj.auth.user.domain.user.model.AuthUser;
import org.rj.auth.user.domain.user.ports.in.EnableUserUseCase;
import org.rj.auth.user.domain.user.ports.out.UserAuthRepoPort;
import org.rj.auth.verification.application.exception.VerificationTokenNotFoundException;
import org.rj.auth.verification.domain.model.VerificationToken;
import org.rj.auth.verification.domain.ports.out.VerificationTokenRepoPort;
import org.rj.cvsubmitorganizer.common.UseCaseService;

@UseCaseService
@RequiredArgsConstructor
public class EnableUserService implements EnableUserUseCase {

    private final UserAuthRepoPort userRepository;
    private final VerificationTokenRepoPort verificationTokenRepoPort;

    @Override
    @Transactional
    public void enableUser(String verificationTokenRequest) {
        VerificationToken verificationToken = verificationTokenRepoPort.findByVerificationToken(verificationTokenRequest)
                .orElseThrow(() -> new VerificationTokenNotFoundException("Token not found"));
        AuthUser authUser = userRepository.findById(verificationToken.getUserId().id())
                .orElseThrow(() -> new AuthUserNotFoundException("User not found"));
        if (authUser.isAccountEnabled()) {
            throw new AuthUserAlreadyVerifiedException("User already verified");
        }
        verificationToken.checkTokenExpiration();
        authUser.enableAccount();
        userRepository.save(authUser);
    }
}