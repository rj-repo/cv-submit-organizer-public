package org.rj.application_job.application.rest;

import org.rj.cvsubmitorganizer.common.ResponseException;
import org.springframework.http.HttpStatus;

public class UserNotOwnerApplicationJobExcpetion extends ResponseException {

    public UserNotOwnerApplicationJobExcpetion(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
