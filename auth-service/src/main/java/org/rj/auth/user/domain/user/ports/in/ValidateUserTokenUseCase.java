package org.rj.auth.user.domain.user.ports.in;

import org.rj.auth.user.domain.user.model.UserDetails;

public interface ValidateUserTokenUseCase {

    UserDetails validate(String token);
}
