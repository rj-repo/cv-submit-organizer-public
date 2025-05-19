package org.rj.auth.verification.domain.ports.out;

import org.rj.auth.user.domain.user.model.AuthUserId;
import org.rj.auth.user.domain.user.model.Token;
import org.rj.auth.user.domain.user.model.UserDetails;

public interface AuthTokenProviderPort {

    Token createToken(AuthUserId userId,String email);
    UserDetails validate(String token);

}
