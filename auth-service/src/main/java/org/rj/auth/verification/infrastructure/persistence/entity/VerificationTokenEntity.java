package org.rj.auth.verification.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.rj.auth.user.domain.user.model.AuthUserId;
import org.rj.auth.user.infrastructure.user.persistence.entity.AuthUserEntity;
import org.rj.auth.verification.domain.model.VerificationToken;
import org.rj.auth.verification.domain.model.VerificationTokenId;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name = "verification_token",schema = "users")
@NoArgsConstructor
public class VerificationTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String verificationToken;
    private LocalDateTime expirationDate;
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AuthUserEntity authUser;


    public static VerificationTokenEntity toEntity(VerificationToken verificationToken) {
        AuthUserEntity authUser = new AuthUserEntity();
        authUser.setId(verificationToken.getUserId().id());

        return VerificationTokenEntity.builder()
                .id(verificationToken.getId() != null ? verificationToken.getId().id() : null)
                .verificationToken(verificationToken.getToken())
                .authUser(authUser)
                .expirationDate(verificationToken.getExpirationDate())
                .build();
    }

    public VerificationToken toAggregate() {

        return VerificationToken.builder()
                .id(new VerificationTokenId(id))
                .token(verificationToken)
                .userId(new AuthUserId(authUser.getId()))
                .expirationDate(expirationDate)
                .build();
    }
}
