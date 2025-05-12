package org.rj.user_service.profile.infrastructure;

import lombok.RequiredArgsConstructor;
import org.rj.user_service.profile.domain.model.ModifyUserProfileCommand;
import org.rj.user_service.profile.domain.model.RegisterAuthCommand;
import org.rj.user_service.profile.domain.ports.out.AuthServiceClientPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthServiceClientAdapter implements AuthServiceClientPort {

    private final AuthServiceClient authServiceClient;
    @Value("${auth.microservice.secret-key}")
    private String internalCallSecret;

    @Override
    public void modifyAuthUser(Long userId, String newEmail, ModifyUserProfileCommand modifyUserProfileCommand) {
        authServiceClient.modifyAuthUser(internalCallSecret,String.valueOf(userId), newEmail, modifyUserProfileCommand);
    }

    @Override
    public void registerAuthUser(RegisterAuthCommand registeredUser) {
        authServiceClient.registerAuthUser(internalCallSecret,registeredUser);
    }
}


