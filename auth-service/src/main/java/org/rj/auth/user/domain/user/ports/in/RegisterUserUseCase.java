package org.rj.auth.user.domain.user.ports.in;

import org.rj.auth.user.application.dto.UserIdResponse;
import org.rj.auth.user.domain.user.command.RegisterUserCommand;

public interface RegisterUserUseCase {

    UserIdResponse signup(RegisterUserCommand input);
}
