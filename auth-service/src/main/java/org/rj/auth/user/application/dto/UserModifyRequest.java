package org.rj.auth.user.application.dto;

import jakarta.validation.constraints.NotBlank;
import org.rj.auth.user.domain.user.command.UserModifyCommand;

public record UserModifyRequest(@NotBlank(message = "Email cannot be blank") String email) {

    public UserModifyCommand toCommand(){
        return new UserModifyCommand(email);
    }
}
