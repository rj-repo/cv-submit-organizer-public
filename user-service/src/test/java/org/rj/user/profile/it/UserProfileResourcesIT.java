package org.rj.user.profile.it;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rj.cvsubmitorganizer.common.ApiResponseException;
import org.rj.user.history.infrastructure.persistence.jpa.JpaUserProfileHistoryRepo;
import org.rj.user.profile.application.rest.dto.UserProfileDetailsResponse;
import org.rj.user.profile.application.rest.dto.UserProfileIdResponse;
import org.rj.user.profile.application.usecase.SendInfoToAuthService;
import org.rj.user.profile.domain.model.ModifyUserProfileCommand;
import org.rj.user.profile.domain.model.SaveNewUserCommand;
import org.rj.user.profile.domain.model.UserProfile;
import org.rj.user.profile.domain.ports.out.AuthServiceClientPort;
import org.rj.user.profile.infrastructure.ExternalErrorExcpetion;
import org.rj.user.profile.infrastructure.persistence.JpaUserProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class UserProfileResourcesIT extends TestContainersInitConfiguration {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JpaUserProfileRepo jpaUserProfileRepo;

    @Autowired
    private JpaUserProfileHistoryRepo jpaUserProfileHistoryRepo;

    @MockitoBean
    private SendInfoToAuthService sendInfoToAuthService;

    @MockitoBean
    AuthServiceClientPort authServiceClientPort;

    @AfterEach
    void cleanDb() {
        jpaUserProfileHistoryRepo.deleteAll();
        jpaUserProfileRepo.deleteAll();
    }

    @Test
    void getMyProfile_success() {
        UserProfile enabledUser = createEnabledUser();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-User-Id", "1");
        headers.add("X-Email", "username@com.pl");

        ResponseEntity<UserProfileDetailsResponse> voidResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/profile/my-profile",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                UserProfileDetailsResponse.class);


        UserProfileDetailsResponse userProfileDetailsResponse = new UserProfileDetailsResponse(
                1L,
                enabledUser.getEmail(),
                enabledUser.getFirstname(),
                enabledUser.getSurname()

        );
        assertThat(voidResponseEntity.getStatusCode().value()).isEqualTo(200);
        assertEquals(userProfileDetailsResponse, voidResponseEntity.getBody());
    }

    @Test
    void getMyProfile_notexisteduser_success() {
        createEnabledUserVoid();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-User-Id", "3");
        headers.add("X-Email", "username@com.pl");

        ResponseEntity<UserProfileDetailsResponse> voidResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/profile/my-profile",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                UserProfileDetailsResponse.class);


        assertThat(voidResponseEntity.getStatusCode().value()).isEqualTo(404);
    }


    @Test
    void modifymyprofile_success() {
        createEnabledUserVoid();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-User-Id", "1");
        ModifyUserProfileCommand modifyUserProfileCommand = new ModifyUserProfileCommand("newUsernameEmail@com.pl");
        doNothing().when(authServiceClientPort).modifyAuthUser(anyLong(), anyString(), any(ModifyUserProfileCommand.class));

        ResponseEntity<Void> voidResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/profile/my-profile",
                HttpMethod.PATCH,
                new HttpEntity<>(modifyUserProfileCommand, headers),
                Void.class);

        assertThat(voidResponseEntity.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void modifymyprofile_alreadyValueSet() {
        createEnabledUserVoid();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-User-Id", "1");
        ModifyUserProfileCommand modifyUserProfileCommand = new ModifyUserProfileCommand("username@com.pl");
        doNothing().when(authServiceClientPort).modifyAuthUser(anyLong(), anyString(), any(ModifyUserProfileCommand.class));

        ResponseEntity<ApiResponseException> voidResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/profile/my-profile",
                HttpMethod.PATCH,
                new HttpEntity<>(modifyUserProfileCommand, headers),
                ApiResponseException.class);

        assertThat(voidResponseEntity.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    void registration_success() {

        SaveNewUserCommand saveNewUserCommand = SaveNewUserCommand
                .builder()
                .email("username@com.pl")
                .password("123Password@")
                .firstname("firstname")
                .surname("surname")
                .build();

        when(sendInfoToAuthService.send(any(SaveNewUserCommand.class))).thenReturn(new UserProfileIdResponse(1L));

        ResponseEntity<Void> voidResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/profile/registration",
                saveNewUserCommand,
                Void.class);

        assertThat(voidResponseEntity.getStatusCode().value()).isEqualTo(200);
    }


    @Test
    void registration_badRequest() {

        SaveNewUserCommand saveNewUserCommand = SaveNewUserCommand
                .builder()
                .email("username@com.pl")
                .surname("surname")
                .build();

        when(sendInfoToAuthService.send(any(SaveNewUserCommand.class))).thenReturn(new UserProfileIdResponse(1L));

        ResponseEntity<ApiResponseException> voidResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/profile/registration",
                saveNewUserCommand,
                ApiResponseException.class);

        assertThat(voidResponseEntity.getStatusCode().value()).isEqualTo(400);
        assertTrue(voidResponseEntity.getBody().messages().size() == 2);
    }

    @Test
    void registration_throwExcpetionFromExternalService() {

        SaveNewUserCommand saveNewUserCommand = SaveNewUserCommand
                .builder()
                .email("username@com.pl")
                .password("123Password@")
                .firstname("firstname")
                .surname("surname")
                .build();


        doThrow(new ExternalErrorExcpetion("Houston - we have a problem", HttpStatus.BAD_REQUEST)).when(sendInfoToAuthService).send(any(SaveNewUserCommand.class));

        ResponseEntity<ApiResponseException> voidResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/profile/registration",
                saveNewUserCommand,
                ApiResponseException.class);

        assertThat(voidResponseEntity.getStatusCode().value()).isEqualTo(400);
        assertTrue(voidResponseEntity.hasBody());
        assertThat(voidResponseEntity.getBody().message()).isEqualTo("Houston - we have a problem");
    }


    private UserProfile createEnabledUser() {
        SaveNewUserCommand saveNewUserCommand = SaveNewUserCommand
                .builder()
                .email("username@com.pl")
                .password("123Password@")
                .firstname("firstname")
                .surname("surname")
                .build();

        when(sendInfoToAuthService.send(any(SaveNewUserCommand.class))).thenReturn(new UserProfileIdResponse(1L));

        restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/profile/registration",
                saveNewUserCommand,
                UserProfileIdResponse.class);

        return saveNewUserCommand.toAggregate(1L);
    }

    void createEnabledUserVoid() {
        createEnabledUser();
    }
}
