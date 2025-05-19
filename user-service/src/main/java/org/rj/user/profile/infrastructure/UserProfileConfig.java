package org.rj.user.profile.infrastructure;

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
