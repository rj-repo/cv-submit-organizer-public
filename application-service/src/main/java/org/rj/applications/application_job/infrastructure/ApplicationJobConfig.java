package org.rj.applications.application_job.infrastructure;

import org.rj.applications.application_job.domain.ApplicationJobDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationJobConfig {

    @Bean
    public ApplicationJobDomainService applicationJobDomainService() {
        return new ApplicationJobDomainService();
    }
}
