package org.rj.user.profile.application.usecase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rj.user.profile.application.exception.UserProfielAlreadyExists;
import org.rj.user.profile.application.rest.dto.UserProfileIdResponse;
import org.rj.user.profile.domain.model.SaveNewUserCommand;
import org.rj.user.profile.domain.model.UserProfile;
import org.rj.user.profile.domain.model.UserProfileId;
import org.rj.user.profile.domain.ports.out.UserProfileRepoPort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveUserProfileServiceTest {

    @InjectMocks
    SaveUserProfileService saveUserProfileUseCase;

    @Mock
    UserProfileRepoPort userProfileRepoPort;

    @Mock
    SendInfoToAuthService sendInfoToAuthService;

    @Test
    void save_userExistsException() {
        //given
        SaveNewUserCommand saveNewUserCommand = SaveNewUserCommand.builder()
                .email("email@com.pl")
                .surname("surname")
                .firstname("firstname")
                .password("Password123@")
                .build();

        UserProfile profile = UserProfile.builder()
                .id(new UserProfileId(1L))
                .surname("surname")
                .firstname("firstname")
                .email("email@com.pl")
                .build();

        when(userProfileRepoPort.findByEmail(anyString())).thenReturn(Optional.of(profile));

        assertThrows(UserProfielAlreadyExists.class, () -> saveUserProfileUseCase.save(saveNewUserCommand));

    }

    @Test
    void save_success() {
        //given
        SaveNewUserCommand saveNewUserCommand = SaveNewUserCommand.builder()
                .email("email@com.pl")
                .surname("surname")
                .firstname("firstname")
                .password("Password123@")
                .build();


        when(sendInfoToAuthService.send(any(SaveNewUserCommand.class))).thenReturn(new UserProfileIdResponse(1L));

        //when
        saveUserProfileUseCase.save(saveNewUserCommand);

        //then-verify
        verify(userProfileRepoPort, times(1)).save(any(UserProfile.class));

    }
}