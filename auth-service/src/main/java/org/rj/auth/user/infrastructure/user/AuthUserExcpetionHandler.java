package org.rj.auth.user.infrastructure.user;


import lombok.extern.slf4j.Slf4j;
import org.rj.auth.user.domain.user.model.AuthUserDomainException;
import org.rj.cvsubmitorganizer.common.ApiResponseException;
import org.rj.cvsubmitorganizer.common.ResponseException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientResponseException;

@RestControllerAdvice
@Slf4j
public class AuthUserExcpetionHandler {

    @ExceptionHandler({AuthUserDomainException.class,RestClientResponseException.class})
    public ResponseEntity<ApiResponseException> handleResponseException(RuntimeException ex) {
        ApiResponseException body = getBody(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler({ResponseException.class})
    public ResponseEntity<ApiResponseException> handleResponseException(ResponseException ex) {
        ApiResponseException body = getBody(ex.getMessage(), ex.getHttpStatus());
        return ResponseEntity.status(ex.getHttpStatus()).body(body);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiResponseException> handleDefault(MethodArgumentNotValidException ex) {
        ApiResponseException responseException = ApiResponseException.builder()
                .messages(ex.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseException);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ApiResponseException> handleResponseException(AuthenticationException ex) {
        ApiResponseException body = getBody(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(body);
    }


    private ApiResponseException getBody(String bodyMessage, HttpStatus httpStatus) {
        return ApiResponseException.builder()
                .message(bodyMessage)
                .statusCode(httpStatus.value())
                .build();
    }
}
