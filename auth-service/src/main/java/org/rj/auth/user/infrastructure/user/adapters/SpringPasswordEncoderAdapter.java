package org.rj.auth.user.infrastructure.user.adapters;

import lombok.RequiredArgsConstructor;
import org.rj.auth.user.domain.user.ports.out.PasswordEncoderPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringPasswordEncoderAdapter implements PasswordEncoderPort {

    private final PasswordEncoder passwordEncoder;
    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
