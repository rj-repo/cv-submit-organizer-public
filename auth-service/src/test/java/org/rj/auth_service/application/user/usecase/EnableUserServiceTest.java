package org.rj.auth_service.application.user.usecase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rj.auth_service.application.user.exception.AuthUserAlreadyVerifiedException;
import org.rj.auth_service.application.user.exception.AuthUserNotFoundException;
import org.rj.auth_service.application.verification.exception.VerificationTokenNotFoundException;
import org.rj.auth_service.domain.user.model.AuthUser;
import org.rj.auth_service.domain.user.model.AuthUserId;
import org.rj.auth_service.domain.user.ports.out.UserAuthRepoPort;
import org.rj.auth_service.domain.verification.model.VerificationToken;
import org.rj.auth_service.domain.verification.model.VerificationTokenId;
import org.rj.auth_service.domain.verification.ports.out.VerificationTokenRepoPort;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class EnableUserServiceTest {

    @Mock
    private UserAuthRepoPort userRepository;

    @Mock
    private VerificationTokenRepoPort verificationTokenRepoPort;


    @InjectMocks
    private EnableUserService enableUserService;

    @Test
     void enableUserSuccessfully() {
        // given
        String token = "sample-token";
        AuthUserId userId = new AuthUserId(1L);
        AuthUser authUser = AuthUser.builder()
                .id(userId)
                .enabled(false)
                .build();
        VerificationToken verificationToken = VerificationToken.builder()
                .userId(userId)
                .token(token)
                .expirationDate(LocalDateTime.now().plusMinutes(10))
                .build();


        when(userRepository.findById(userId.id())).thenReturn(Optional.of(authUser));
        when(verificationTokenRepoPort.findByVerificationToken(any())).thenReturn(Optional.of(verificationToken));
        // when
        enableUserService.enableUser(token);

        // then
        verify(userRepository).save(authUser);
    }

    @Test
     void throwExceptionIfUserNotFound() {
        // given
        String token = "invalid-token";
        AuthUserId userId = new AuthUserId(1L);
        VerificationToken verificationToken = VerificationToken.builder()
                .id(new VerificationTokenId(1L))
                .userId(userId)
                .token(token)
                .expirationDate(LocalDateTime.now().plusMinutes(10))
                .build();

        when(verificationTokenRepoPort.findByVerificationToken(any())).thenReturn(Optional.of(verificationToken));
        when(userRepository.findById(userId.id())).thenReturn(Optional.empty());

        // when-then
        assertThrows(AuthUserNotFoundException.class, () -> enableUserService.enableUser(token));

        verify(userRepository, never()).save(any());
    }

    @Test
     void throwExceptionIfUserAlreadyVerified() {
        // given
        String token = "invalid-token";
        AuthUserId userId = new AuthUserId(1L);
        AuthUser authUser = AuthUser.builder()
                .id(userId)
                .enabled(true)
                .build();
        VerificationToken verificationToken = VerificationToken.builder()
                .id(new VerificationTokenId(1L))
                .userId(userId)
                .token(token)
                .expirationDate(LocalDateTime.now().plusMinutes(10))
                .build();

        when(verificationTokenRepoPort.findByVerificationToken(any())).thenReturn(Optional.of(verificationToken));
        when(userRepository.findById(userId.id())).thenReturn(Optional.of(authUser));

        // when-then
        assertThrows(AuthUserAlreadyVerifiedException.class, () -> enableUserService.enableUser(token));

        verify(userRepository, never()).save(any());
    }

    @Test
     void throwExceptionIfTokenNotFound() {
        // given
        String token = "token-not-found";

        when(verificationTokenRepoPort.findByVerificationToken(any())).thenReturn(Optional.empty());

        // when-then
        assertThrows(VerificationTokenNotFoundException.class, () -> enableUserService.enableUser(token));


        verify(userRepository, never()).save(any());
        verify(userRepository, never()).findById(any());
    }
}