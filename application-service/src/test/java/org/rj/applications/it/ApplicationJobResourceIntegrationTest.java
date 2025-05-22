package org.rj.applications.it;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.rj.applications.application_job.application.rest.dto.UserJobApplicationResponse;
import org.rj.applications.application_job.domain.model.ApplicationJob;
import org.rj.applications.application_job.domain.model.StatusApplication;
import org.rj.applications.application_job.infrastructure.persistence.JpaApplicationJob;
import org.rj.applications.application_job.infrastructure.persistence.entity.ApplicationJobEntity;
import org.rj.applications.document.domain.model.DocumentApplication;
import org.rj.applications.document.domain.model.TypeFile;
import org.rj.cvsubmitorganizer.common.ApiResponseException;
import org.rj.cvsubmitorganizer.common.UserProfileId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class ApplicationJobResourceIntegrationTest extends TestContainersInitConfiguration {


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JpaApplicationJob jpaAuthUser;


    @AfterEach
    void clearDatabase() {
        jpaAuthUser.deleteAll();
    }

    @Test
    void addJob_expect404() {
        Resource fileResource = new ByteArrayResource("Test CV content".getBytes()) {
            @Override
            public String getFilename() {
                return "cv.pdf";
            }
        };
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("documentApplication", fileResource);
        body.add("jobOfferName", "Java Developer");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("X-User-Id", "1");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);


        ResponseEntity<Void> voidResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/applications",
                HttpMethod.POST,
                requestEntity,
                Void.class);

        assertThat(voidResponseEntity.getStatusCode().value()).isEqualTo(400);

    }

    @Test
    void addJob_expectOK() {
        Resource fileResource = new ByteArrayResource("Test CV content".getBytes()) {
            @Override
            public String getFilename() {
                return "cv.pdf";
            }
        };
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("documentApplication", fileResource);
        body.add("companyName", "Acme Corp");
        body.add("jobOfferName", "Java Developer");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("X-User-Id", "1");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);


        ResponseEntity<Void> voidResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/applications",
                HttpMethod.POST,
                requestEntity,
                Void.class);

        assertThat(voidResponseEntity.getStatusCode().value()).isEqualTo(200);

    }

    @Test
    @Sql("/sql.sql")
    void delete_deleteOK() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-User-Id", "1");
        ResponseEntity<Void> voidResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/applications/1",
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                Void.class);

        assertThat(voidResponseEntity.getStatusCode().value()).isEqualTo(200);


    }


    @Test
    @Sql("/sql.sql")
    void modifyJob_oneField_expectOK() {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("jobOfferName", "Java Developer");
        body.add("jobId", "1");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("X-User-Id", "1");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);


        ResponseEntity<Void> voidResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/applications",
                HttpMethod.PATCH,
                requestEntity,
                Void.class);

        assertThat(voidResponseEntity.getStatusCode().value()).isEqualTo(200);

    }


    @Test
    @Sql("/sql.sql")
    void modifyJob_allFields_expectOK() {
        Resource fileResource = new ByteArrayResource("Test CV content".getBytes()) {
            @Override
            public String getFilename() {
                return "cv.pdf";
            }
        };
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileResource);
        body.add("companyName", "Acme Corp");
        body.add("jobOfferName", "Java Developer");
        body.add("statusApplication", StatusApplication.IN_PROGRESS.name());
        body.add("jobId", "1");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("X-User-Id", "1");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);


        ResponseEntity<Void> voidResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/applications",
                HttpMethod.PATCH,
                requestEntity,
                Void.class);

        assertThat(voidResponseEntity.getStatusCode().value()).isEqualTo(200);

    }


    @Test
    void getJobsForUser_badRequest() {
        createSomeApplications();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-User-Id", "1");


        ResponseEntity<ApiResponseException> voidResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/applications?page=10000",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                ApiResponseException.class);


        assertThat(voidResponseEntity.getStatusCode().value()).isEqualTo(400);
        assertTrue(voidResponseEntity.getBody().message().contains("Illegal request for pages - 10000 page requested and available is 50"));

    }


    @Test
    void getJobsForUser_expectOK() {
        createSomeApplications();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-User-Id", "1");


        ResponseEntity<UserJobApplicationResponse> voidResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/applications?page=10",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                UserJobApplicationResponse.class);


        assertThat(voidResponseEntity.getStatusCode().value()).isEqualTo(200);
        UserJobApplicationResponse body = voidResponseEntity.getBody();
        assertNotNull(body);
        assertNotNull(body.jobs());
        assertFalse(body.jobs().isEmpty());

        assertThat(body.jobs().size()).isEqualTo(10);
        assertThat(body.remainElements()).isEqualTo(400);
        assertThat(body.currentPage()).isEqualTo(10);
        assertThat(body.totalPages()).isEqualTo(50);
        assertThat(body.totalResults()).isEqualTo(500);

    }


    private void createSomeApplications() {
        List<ApplicationJob> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ApplicationJob applicationJob = ApplicationJob.builder()
                    .statusApplication(StatusApplication.IN_PROGRESS)
                    .jobOfferName("job" + i)
                    .companyName("companyName" + i)
                    .userProfileId(new UserProfileId(i % 2 == 0 ? 1L : 2L))
                    .documentApplication(createDocumentApplication(i))
                    .build();
            list.add(applicationJob);
        }
        jpaAuthUser.saveAll(list.stream().map(ApplicationJobEntity::of).toList());
    }

    private DocumentApplication createDocumentApplication(int i) {
        return DocumentApplication.builder()
                .documentName("documentName" + i)
                .content(("fdsfresgpor;lfsdg" + i).getBytes())
                .typeFile(TypeFile.PDF)
                .build();
    }


}
