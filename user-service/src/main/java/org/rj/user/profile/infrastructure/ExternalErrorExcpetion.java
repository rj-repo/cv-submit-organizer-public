package org.rj.user.profile.infrastructure;

import org.rj.cvsubmitorganizer.common.ResponseException;
import org.springframework.http.HttpStatus;

public class ExternalErrorExcpetion extends ResponseException {

    private final HttpStatus status;

    public ExternalErrorExcpetion(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return status;
    }
}
