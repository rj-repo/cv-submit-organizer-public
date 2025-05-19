package org.rj.auth.user.application.usecase;

import lombok.RequiredArgsConstructor;
import org.rj.auth.user.application.dto.LoginResponse;
import org.rj.auth.user.application.exception.AuthUserNotFoundException;
import org.rj.auth.user.domain.user.command.LoginUserCommand;
import org.rj.auth.user.domain.user.model.AuthUser;
import org.rj.auth.user.domain.user.model.Token;
import org.rj.auth.user.domain.user.ports.in.AuthManagerPort;
import org.rj.auth.user.domain.user.ports.in.LoginUserUseCase;
import org.rj.auth.user.domain.user.ports.out.UserAuthRepoPort;
import org.rj.auth.verification.domain.ports.out.AuthTokenProviderPort;
import org.rj.cvsubmitorganizer.common.UseCaseService;

@UseCaseService
@RequiredArgsConstructor
public class LoginUserService implements LoginUserUseCase {
    private final AuthManagerPort authManagerPort;
    private final AuthTokenProviderPort tokenService;
    private final UserAuthRepoPort userRepository;

    @Override
    public LoginResponse login(LoginUserCommand loginUserCommand) {
        AuthUser authUser = userRepository.findByEmail(loginUserCommand.email())
                .orElseThrow(() -> new AuthUserNotFoundException("User " + loginUserCommand.email() + " not found"));
        authUser.checkIfUserEnabled();
        authManagerPort.authenticate(loginUserCommand);
        Token token = tokenService.createToken(authUser.getId(),authUser.getEmail());

        return new LoginResponse(token.token());
    }

}
