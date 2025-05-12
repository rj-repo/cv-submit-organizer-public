package org.rj.auth_service.domain.user.ports.in;

import org.rj.auth_service.domain.user.dto.RegisterUserRequest;
import org.rj.auth_service.domain.user.dto.UserIdResponse;

public interface RegisterUserUseCase {

    UserIdResponse signup(RegisterUserRequest input);
}
