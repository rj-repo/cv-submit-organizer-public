package org.rj.auth.user.domain.user.command;

public record LoginUserCommand(String email,
                               String password) {
}
