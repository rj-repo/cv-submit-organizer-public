package org.rj.auth.user.infrastructure.user.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.rj.auth.user.domain.user.model.AuthUser;
import org.rj.auth.user.domain.user.model.AuthUserId;

@Builder
@AllArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name = "users", schema = "users")
@NoArgsConstructor
public class AuthUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private boolean enabled;

    public static AuthUserEntity toEntity(AuthUser authUser) {
        return AuthUserEntity.builder()
                .id(authUser.getId() != null ? authUser.getId().id() : null)
                .email(authUser.getEmail())
                .password(authUser.getPassword())
                .enabled(authUser.isEnabled())
                .build();

    }

    public AuthUser toAggregate() {
        return AuthUser.builder()
                .id(new AuthUserId(id))
                .email(email)
                .password(password)
                .enabled(enabled)
                .build();

    }


}
