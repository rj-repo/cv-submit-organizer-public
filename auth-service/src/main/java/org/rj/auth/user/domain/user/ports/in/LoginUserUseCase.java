package org.rj.auth.user.domain.user.ports.in;

import org.rj.auth.user.application.dto.LoginResponse;
import org.rj.auth.user.domain.user.command.LoginUserCommand;

public interface LoginUserUseCase {
    LoginResponse login(LoginUserCommand loginUserCommand);
}
