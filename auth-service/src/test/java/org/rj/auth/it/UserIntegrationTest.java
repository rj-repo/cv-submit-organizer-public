package org.rj.auth.it;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.rj.auth.TestContainersInitConfiguration;
import org.rj.auth.user.application.dto.LoginResponse;
import org.rj.auth.user.domain.user.command.LoginUserCommand;
import org.rj.auth.user.domain.user.command.RegisterUserCommand;
import org.rj.auth.user.domain.user.model.AuthUser;
import org.rj.auth.user.domain.user.model.UserDetails;
import org.rj.auth.user.infrastructure.user.persistence.JpaAuthUser;
import org.rj.auth.verification.domain.model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
 class UserIntegrationTest extends TestContainersInitConfiguration {


    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("username", "password"))
            .withPerMethodLifecycle(false);

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JpaAuthUser jpaAuthUser;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JpaVerificationTokenStub jpaVerificationTokenStub;


    @DynamicPropertySource
    static void initEnv(DynamicPropertyRegistry registry) {
        registry.add("application.security.jwt.secret-key", () -> 123);
        registry.add("application.security.jwt.expiration", () -> 10);
    }

    @BeforeEach
     void clearDatabase() {
        jpaAuthUser.deleteAll();
        jpaVerificationTokenStub.deleteAll();
        jdbcTemplate.execute("ALTER SEQUENCE users.users_id_seq RESTART WITH 1");
    }


    @Test
    void registerUserWithExistingUsername() {
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(
                "username@com.pl",
                "passworD123@"

        );

        ResponseEntity<Void> getResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/auth/registration",
                registerUserCommand,
                Void.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertFalse(getResponse.hasBody());

        ResponseEntity<Void> getResponse1 = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/auth/registration",
                registerUserCommand,
                Void.class);
        assertThat(getResponse1.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertFalse(getResponse1.hasBody());

    }

    @Test
    void registerValidNewUser() {
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(
                "username@com.pl",
                "passworD123@"

        );

        ResponseEntity<Void> getResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/auth/registration",
                registerUserCommand,
                Void.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertFalse(getResponse.hasBody());

    }


    @Test
    void loginUser_accountEnabled() {
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(
                "username@gmail.com",
                "passworD123@"
        );

        restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/auth/registration",
                registerUserCommand,
                Void.class);

        String tokenVer = getVerificationToken(registerUserCommand.email());

        restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/auth/verification?token=" + tokenVer,
                getVerificationToken(registerUserCommand.email()),
                Void.class);

        LoginUserCommand loginUserCommand = new LoginUserCommand(
                "username@gmail.com",
                "passworD123@"
        );

        ResponseEntity<LoginResponse> loginResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/auth/login",
                loginUserCommand,
                LoginResponse.class);

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(loginResponse.hasBody());
        assertNotNull(loginResponse.getBody().token());
        String token = loginResponse.getBody().token();
        assertThat(decodeToken(token).getSubject()).isEqualTo("1");

    }

    @Test
    void loginUser_accountDisabled() {
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(
                "username@gmail.com",
                "passworD123@"
        );

        restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/auth/registration",
                registerUserCommand,
                Void.class);

        LoginUserCommand loginUserCommand = new LoginUserCommand(
                "username@gmail.com",
                "passworD123@"
        );

        ResponseEntity<LoginResponse> loginResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/auth/login",
                loginUserCommand,
                LoginResponse.class);

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void loginUser_badToken() {
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(
                "username@com.pl",
                "passworD123@"

        );

        restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/auth/registration",
                registerUserCommand,
                Void.class);

        String tokenVer = getVerificationToken(registerUserCommand.email());
        restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/auth/verification?token=" + tokenVer,
                getVerificationToken(registerUserCommand.email()),
                Void.class);
        LoginUserCommand loginUserCommand = new LoginUserCommand(
                "username@com.pl",
                "passworD123@"
        );
        ResponseEntity<LoginResponse> loginResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/auth/login",
                loginUserCommand,
                LoginResponse.class);

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(loginResponse.hasBody());
        assertNotNull(loginResponse.getBody().token());
        String token = loginResponse.getBody().token();
        assertThat(decodeToken(token).getSubject()).isEqualTo("1");

    }

    @Test
    void enableUser_resendToken() {
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(
                "username@com.pl",
                "passworD123@"

        );

        restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/auth/registration",
                registerUserCommand,
                Void.class);

        String tokenVer = getVerificationToken(registerUserCommand.email());
        ResponseEntity<Void> voidResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/auth/verification/resend?token=" + tokenVer,
                getVerificationToken(registerUserCommand.email()),
                Void.class);

        assertThat(voidResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertFalse(voidResponseEntity.hasBody());

    }

    @Test
    void validateToken_validToken() {
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(
                "username@gmail.com",
                "passworD123@"
        );

        restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/auth/registration",
                registerUserCommand,
                Void.class);


        String tokenVer = getVerificationToken(registerUserCommand.email());

        restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/auth/verification?token=" + tokenVer,
                getVerificationToken(registerUserCommand.email()),
                Void.class);

        LoginUserCommand loginUserCommand = new LoginUserCommand(
                "username@gmail.com",
                "passworD123@"
        );

        ResponseEntity<LoginResponse> loginResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/auth/login",
                loginUserCommand,
                LoginResponse.class);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + loginResponse.getBody().token());
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<UserDetails> validatingTokenResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/auth/validation",
                requestEntity,
                UserDetails.class);

        assertThat(validatingTokenResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNull(validatingTokenResponse.getBody());
        assertThat(validatingTokenResponse.getHeaders().getFirst("X-User-Id")).isEqualTo("1");
        assertThat(validatingTokenResponse.getHeaders().getFirst("X-Email")).isEqualTo(registerUserCommand.email());
    }


    private DecodedJWT decodeToken(String token) {
        return JWT.decode(token);
    }

    private String getVerificationToken(String email) {
        AuthUser byEmail = jpaAuthUser.findByEmail(email).get().toAggregate();
        VerificationToken byAuthUserId = jpaVerificationTokenStub.findByAuthUser_Id(byEmail.getId().id()).get().toAggregate();
        return byAuthUserId.getToken();
    }


}
