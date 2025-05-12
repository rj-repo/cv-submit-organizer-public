package org.rj.cvsubmitorganizer.common;

import org.springframework.http.HttpStatus;

public abstract class ResponseException extends RuntimeException {

    protected  ResponseException() {
    }

    protected ResponseException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
