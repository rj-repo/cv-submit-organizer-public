package org.rj.auth.user.domain.user;

import org.rj.auth.user.domain.user.command.RegisterUserCommand;
import org.rj.auth.user.domain.user.model.AuthUser;

public class AuthDomainService {

    public AuthUser createUser(RegisterUserCommand registerUserCommand) {
        AuthUser aggregate = registerUserCommand.toAggregate();
        aggregate.validatePassword();
        aggregate.validateEmail();
        return aggregate;
    }
}
