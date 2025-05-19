package org.rj.auth.application.usecase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rj.auth.user.application.usecase.ValidateUserTokenService;
import org.rj.auth.user.domain.user.model.AuthUser;
import org.rj.auth.user.domain.user.model.AuthUserId;
import org.rj.auth.user.domain.user.model.UserDetails;
import org.rj.auth.user.infrastructure.user.persistence.JpaAuthUserAdapterPort;
import org.rj.auth.verification.domain.ports.out.AuthTokenProviderPort;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidateUserTokenServiceTest {

    @Mock
    private AuthTokenProviderPort authTokenProviderPort;

    @Mock
    JpaAuthUserAdapterPort jpaAuthUser;

    @InjectMocks
    private ValidateUserTokenService authTokenValidator;

    @Test
    void shouldValidateTokenAfterRemovingBearerPrefix() {
        // Given
        String tokenWithBearer = "Bearer abcdefghijklmnopqrstuvwxyz";
        UserDetails userDetails = new UserDetails("email@email.com", "1");
        Optional<AuthUser> authUser = Optional.of(new AuthUser(new AuthUserId(1L), userDetails.email(), "123", true));

        when(authTokenProviderPort.validate(anyString())).thenReturn(userDetails);
        when(jpaAuthUser.findByEmail(anyString())).thenReturn(authUser);
        // When
        authTokenValidator.validate(tokenWithBearer);

        // Then
        verify(authTokenProviderPort, times(1)).validate("abcdefghijklmnopqrstuvwxyz");
    }

    @Test
    void shouldValidateTokenWithoutBearerPrefixDirectly() {
        // Given
        String tokenWithoutBearer = "abcdefghijklmnopqrstuvwxyz";
        UserDetails userDetails = new UserDetails("email@email.com", "1");
        Optional<AuthUser> authUser = Optional.of(new AuthUser(new AuthUserId(1L), userDetails.email(), "123", true));

        when(authTokenProviderPort.validate(anyString())).thenReturn(userDetails);
        when(jpaAuthUser.findByEmail(anyString())).thenReturn(authUser);
        // When
        authTokenValidator.validate(tokenWithoutBearer);

        // Then
        verify(authTokenProviderPort, times(1)).validate(tokenWithoutBearer);
    }

}