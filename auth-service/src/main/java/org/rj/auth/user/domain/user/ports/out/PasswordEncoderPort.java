package org.rj.auth.user.domain.user.ports.out;

public interface PasswordEncoderPort {

    String encode(String password);
}
