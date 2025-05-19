package org.rj.auth.user.domain.user.ports.in;

import org.rj.auth.user.domain.user.command.LoginUserCommand;

public interface AuthManagerPort {

    void authenticate(LoginUserCommand loginUserCommand);
}
