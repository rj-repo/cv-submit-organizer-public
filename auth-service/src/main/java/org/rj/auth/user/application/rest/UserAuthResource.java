package org.rj.auth.user.application.rest;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rj.auth.user.application.dto.LoginResponse;
import org.rj.auth.user.application.dto.LoginUserRequest;
import org.rj.auth.user.application.dto.UserIdResponse;
import org.rj.auth.user.application.dto.UserModifyRequest;
import org.rj.auth.user.domain.user.command.RegisterUserCommand;
import org.rj.auth.user.domain.user.model.UserDetails;
import org.rj.auth.user.domain.user.ports.in.*;
import org.rj.auth.verification.application.dto.VerificationTokenRequest;
import org.rj.auth.verification.domain.ports.out.ResendVerificationTokenUseCase;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class UserAuthResource {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;
    private final EnableUserUseCase enableUserUseCase;
    private final ResendVerificationTokenUseCase resendVerificationTokenUseCase;
    private final ValidateUserTokenUseCase validateUserTokenUseCase;
    private final ModifyUserUserCase modifyUserUserCase;


    @PostMapping("/registration")
    public ResponseEntity<UserIdResponse> register(@RequestBody @Valid RegisterUserCommand registerUserDto) {
        UserIdResponse signup = registerUserUseCase.signup(registerUserDto);
        return ResponseEntity.ok(signup);
    }

    @PostMapping("/verification")
    public ResponseEntity<Void> verify(@RequestParam VerificationTokenRequest token) {
        enableUserUseCase.enableUser(token.token());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verification/resend")
    public ResponseEntity<Void> resendVerificationToken(@RequestParam VerificationTokenRequest token) {
        resendVerificationTokenUseCase.resendVerificationToken(token.token());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginUserRequest tokenRequestDto) {
        LoginResponse authenticatedUser = loginUserUseCase.login(tokenRequestDto.toCommand());
        return ResponseEntity.ok(authenticatedUser);
    }


    @PostMapping("/validation")
    public ResponseEntity<Void> validation(@RequestHeader("Authorization") String token) {
        UserDetails validate = validateUserTokenUseCase.validate(token);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Email", validate.email());
        headers.add("X-User-Id", validate.userId());

        return ResponseEntity.ok().headers(headers).build();
    }

    @PostMapping("/user/{id}")
    public ResponseEntity<Void> modify(@PathVariable("id") String userId,
                                       @RequestHeader("X-Email") String email,
                                       @RequestBody @Valid UserModifyRequest userModifyRequest) {
        modifyUserUserCase.modify(email, userId, userModifyRequest.toCommand());
        return ResponseEntity.ok().build();
    }


}
