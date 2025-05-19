package org.rj.auth.user.domain.user.ports.out;

import org.rj.auth.user.domain.user.model.AuthUser;

import java.util.Optional;

public interface UserAuthRepoPort {

    Optional<AuthUser> findByEmail(String email);

    AuthUser save(AuthUser authUser);

    Optional<AuthUser> findById(Long id);

}
