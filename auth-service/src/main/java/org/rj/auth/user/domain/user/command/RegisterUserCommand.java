package org.rj.auth.user.domain.user.command;

import org.rj.auth.user.domain.user.model.AuthUser;

public record RegisterUserCommand(String email,
                                  String password) {

    public AuthUser toAggregate() {
        return AuthUser.builder()
                .password(password)
                .email(email)
                .build();
    }

}
