package org.rj.application_service.infrastructure;

import org.rj.application_service.domain.ApplicationJobDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationJobConfig {

    @Bean
    public ApplicationJobDomainService applicationJobDomainService() {
        return new ApplicationJobDomainService();
    }
}
