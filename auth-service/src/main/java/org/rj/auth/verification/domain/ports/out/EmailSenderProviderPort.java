package org.rj.auth.verification.domain.ports.out;

public interface EmailSenderProviderPort {

    void sendMail(String mail, String verToken);
}
