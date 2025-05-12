package org.rj.auth_service.application.user.exception;

import org.rj.cvsubmitorganizer.common.ResponseException;
import org.springframework.http.HttpStatus;


public class AuthUserEmailModifyException extends ResponseException {

    private final String message;

    public AuthUserEmailModifyException(String message) {
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    public String getMessage() {
        return message;
    }

   
}
