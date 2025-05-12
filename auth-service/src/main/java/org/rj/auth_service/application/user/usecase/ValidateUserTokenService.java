package org.rj.auth_service.application.user.usecase;

import lombok.RequiredArgsConstructor;
import org.rj.auth_service.application.user.exception.AuthUserNotFoundException;
import org.rj.auth_service.domain.user.model.AuthUser;
import org.rj.auth_service.domain.user.model.UserDetails;
import org.rj.auth_service.domain.user.ports.in.ValidateUserTokenUseCase;
import org.rj.auth_service.domain.user.ports.out.UserAuthRepoPort;
import org.rj.auth_service.domain.verification.ports.out.AuthTokenProviderPort;
import org.rj.cvsubmitorganizer.common.UseCaseService;

@UseCaseService
@RequiredArgsConstructor
public class ValidateUserTokenService implements ValidateUserTokenUseCase {

    private final AuthTokenProviderPort authTokenProviderPort;
    private final UserAuthRepoPort userRepository;

    @Override
    public UserDetails validate(String token) {
        if (token.contains("Bearer ")) {
            token = token.substring(7);
        }
        UserDetails userDetails = authTokenProviderPort.validate(token);
        AuthUser authUser = userRepository.findByEmail(userDetails.email())
                .orElseThrow(() -> new AuthUserNotFoundException("User " + userDetails.email() + " not found"));
        authUser.checkIfUserEnabled();

        return userDetails;
    }
}
