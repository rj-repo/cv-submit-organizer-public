package org.rj.user_service.profile.it;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

 class TestContainersInitConfiguration {

    @Container
     static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14.4")
            .withDatabaseName("organizer")
            .withUsername("username")
            .withPassword("kon")
            .withExposedPorts(5432);

    @DynamicPropertySource
    static void initEnv(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

}

