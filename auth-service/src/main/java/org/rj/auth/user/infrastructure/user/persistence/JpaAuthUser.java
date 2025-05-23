package org.rj.auth.user.infrastructure.user.persistence;

import org.rj.auth.user.infrastructure.user.persistence.entity.AuthUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface JpaAuthUser extends JpaRepository<AuthUserEntity, Long> {

    Optional<AuthUserEntity> findByEmail(String email);

}
