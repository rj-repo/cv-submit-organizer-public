package org.rj.auth.user.infrastructure.user.adapters;

import lombok.RequiredArgsConstructor;
import org.rj.auth.user.domain.user.command.LoginUserCommand;
import org.rj.auth.user.domain.user.ports.in.AuthManagerPort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthLoginManagerAdapter implements AuthManagerPort {
    private final AuthenticationManager authenticationManager;

    @Override
    public void authenticate(LoginUserCommand loginUserCommand) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginUserCommand.email(), loginUserCommand.password()
            ));
        }catch (AuthenticationException e){
            throw new BadCredentialsException("Bad credentials");
        }
    }
}
