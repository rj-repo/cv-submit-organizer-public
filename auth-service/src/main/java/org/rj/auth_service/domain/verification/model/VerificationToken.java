package org.rj.auth_service.domain.verification.model;

import lombok.Builder;
import lombok.Getter;
import org.rj.auth_service.domain.user.model.AuthUserId;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Builder
public class VerificationToken {

    private VerificationTokenId id;
    private String token;
    private AuthUserId userId;
    private LocalDateTime expirationDate;

    public void setExpirationDate(int minutes){
        this.expirationDate = LocalDateTime.now().plusMinutes(minutes);
    }


    public void generateToken() {
        this.token = UUID.randomUUID().toString();
    }

    public void checkTokenExpiration(){
        if (LocalDateTime.now().isAfter(expirationDate)) {
            throw new VerificationTokenDomainException("Token expired");
        }
    }
}
