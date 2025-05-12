package org.rj.application_service.application.rest;

import org.rj.cvsubmitorganizer.common.ResponseException;
import org.springframework.http.HttpStatus;

public class ApplicationJobNotFound extends ResponseException {

    public ApplicationJobNotFound(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
