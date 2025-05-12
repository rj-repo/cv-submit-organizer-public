package org.rj.user_service.profile.application.usecase;

import lombok.RequiredArgsConstructor;
import org.rj.user_service.profile.application.rest.dto.UserProfileIdResponse;
import org.rj.user_service.profile.domain.model.RegisterAuthCommand;
import org.rj.user_service.profile.domain.model.SaveNewUserCommand;
import org.rj.user_service.profile.infrastructure.AuthServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendInfoToAuthService {

    private final AuthServiceClient authServiceClient;
    @Value("${auth.microservice.secret-key}")
    private String internalCallSecret;


    public UserProfileIdResponse send(SaveNewUserCommand saveNewUserCommand) {
        RegisterAuthCommand request = new RegisterAuthCommand(saveNewUserCommand.email(), saveNewUserCommand.password());
        return authServiceClient.registerAuthUser(internalCallSecret,request).getBody();
    }
}
