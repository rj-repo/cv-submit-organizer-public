package org.rj.user_service.profile.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserProfileConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignClientErrorHandler();
    }
}
