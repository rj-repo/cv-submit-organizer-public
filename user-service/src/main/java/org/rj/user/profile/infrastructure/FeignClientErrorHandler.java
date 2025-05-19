package org.rj.user.profile.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rj.cvsubmitorganizer.common.ApiResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class FeignClientErrorHandler implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Exception decode(String s, Response response) {
        if(HttpStatusCode.valueOf(response.status()).is4xxClientError()){
            try {
                if(response.body() == null){
                    ApiResponseException apiResponseException = ApiResponseException.builder().statusCode(response.status()).build();
                    throw new ExternalErrorExcpetion(String.valueOf(apiResponseException.statusCode()),HttpStatus.valueOf(apiResponseException.statusCode()));
                }
                ApiResponseException apiResponseException = objectMapper.readValue(response.body().asInputStream(), ApiResponseException.class);
                throw new ExternalErrorExcpetion(apiResponseException.message(),HttpStatus.valueOf(apiResponseException.statusCode()));
            } catch (IOException e) {
                String errorId = UUID.randomUUID().toString();
                log.error("Error occurred while decoding API response with id: {}",errorId, e);
                throw new ExternalErrorExcpetion("Unexpected error - error id: "
                + errorId, HttpStatus.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            }
        }
        return errorDecoder.decode(s, response);
    }
}
