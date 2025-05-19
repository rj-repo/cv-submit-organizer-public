package org.rj.auth.user.application.dto;

import jakarta.validation.constraints.NotBlank;
import org.rj.auth.user.domain.user.command.LoginUserCommand;

public record LoginUserRequest(@NotBlank(message = "Email cannot be blank") String email,
                               @NotBlank(message = "Password cannot be blank") String password) {

    public LoginUserCommand toCommand(){
        return new LoginUserCommand(email, password);
    }
}
