package org.rj.applications.application_job.application;

import lombok.extern.slf4j.Slf4j;
import org.rj.applications.application_job.domain.model.ApplicationJobDomainException;
import org.rj.cvsubmitorganizer.common.ApiResponseException;
import org.rj.cvsubmitorganizer.common.ResponseException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApplicationServiceResponseHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiResponseException> handleDefault(MethodArgumentNotValidException ex) {
        ApiResponseException responseException = ApiResponseException.builder()
                .messages(ex.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseException);
    }

    @ExceptionHandler({ResponseException.class})
    public ResponseEntity<ApiResponseException> handleDefault(ResponseException ex) {
        ApiResponseException responseException = ApiResponseException.builder()
                .message(ex.getMessage())
                .statusCode(ex.getHttpStatus().value())
                .build();
        return ResponseEntity.status(ex.getHttpStatus().value()).body(responseException);
    }

    @ExceptionHandler({ApplicationJobDomainException.class})
    public ResponseEntity<ApiResponseException> handleDefault(ApplicationJobDomainException ex) {
        ApiResponseException responseException = ApiResponseException.builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(responseException);
    }

}
