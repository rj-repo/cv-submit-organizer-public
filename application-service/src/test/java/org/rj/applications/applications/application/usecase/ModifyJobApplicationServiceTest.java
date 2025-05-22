package org.rj.applications.applications.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rj.applications.application_job.application.rest.ApplicationJobNotFound;
import org.rj.applications.application_job.application.usecase.ModifyJobApplicationService;
import org.rj.applications.application_job.domain.model.ApplicationJob;
import org.rj.applications.application_job.domain.model.ApplicationJobDomainException;
import org.rj.applications.application_job.domain.model.StatusApplication;
import org.rj.applications.application_job.domain.model.command.ModifyJobApplicationCommand;
import org.rj.applications.application_job.domain.ports.out.ApplicationJobRepoPort;
import org.rj.applications.document.domain.model.DocumentApplication;
import org.rj.applications.document.domain.model.TypeFile;
import org.rj.applications.document.domain.model.command.ModifyDocumentCommand;
import org.rj.applications.it.TestContainersInitConfiguration;
import org.rj.cvsubmitorganizer.common.UserProfileId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
 class ModifyJobApplicationServiceTest extends TestContainersInitConfiguration {

    @Autowired
    ModifyJobApplicationService modifyJobApplicationService;

    @Autowired
    ApplicationJobRepoPort jpaApplicationJob;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
     void clearDatabase() {
       jdbcTemplate.execute("DELETE FROM applications.applications");
       jdbcTemplate.execute("DELETE FROM applications.documents");
       jdbcTemplate.execute("ALTER SEQUENCE applications.applications_id_seq RESTART WITH 1");
       jdbcTemplate.execute("ALTER SEQUENCE applications.documents_id_seq RESTART WITH 1");
    }

    @Test
    void modify_expectAlterRecord() {
        //given
        ModifyDocumentCommand documentCommand =
                new ModifyDocumentCommand("just byte text".getBytes(), "filename");
        ModifyJobApplicationCommand command = ModifyJobApplicationCommand.builder()
                .jobId(1L)
                .modifyDocumentCommand(documentCommand)
                .userId(1L)
                .companyName("companyName")
                .jobOfferName("jobOfferName")
                .statusApplication(StatusApplication.APPLIED.name())
                .build();

        DocumentApplication documentApplication = DocumentApplication.builder()
                .documentName("docName")
                .content("old byte text".getBytes())
                .typeFile(TypeFile.PDF)
                .build();

        ApplicationJob applicationJob = ApplicationJob.builder()
                .documentApplication(documentApplication)
                .jobOfferName("jobOfferName")
                .companyName("companyName")
                .statusApplication(StatusApplication.APPLIED)
                .userProfileId(new UserProfileId(1L))
                .build();

        jpaApplicationJob.saveJob(applicationJob);

        //when
        modifyJobApplicationService.modify(command);

        Optional<ApplicationJob> jobById = jpaApplicationJob.getJobById(1L);

        //then
        assertTrue(jobById.isPresent());
        assertNotEquals(jobById.get(), applicationJob);
        DocumentApplication modifiedDoc = jobById.get().getDocumentApplication();
        assertEquals("filename", modifiedDoc.getDocumentName());
        assertEquals(Arrays.toString("just byte text".getBytes()), Arrays.toString(modifiedDoc.getContent()));
    }

    @Test
    void modify_notFoundRecord() {
        //given
        ModifyDocumentCommand documentCommand =
                new ModifyDocumentCommand("just byte text".getBytes(), "filename");
        ModifyJobApplicationCommand command = ModifyJobApplicationCommand.builder()
                .jobId(123L)
                .modifyDocumentCommand(documentCommand)
                .userId(1L)
                .companyName("companyName")
                .jobOfferName("jobOfferName")
                .statusApplication(StatusApplication.APPLIED.name())
                .build();


        //when
        assertThrows(ApplicationJobNotFound.class, () -> {
            modifyJobApplicationService.modify(command);
        });

    }

    @Test
    void modify_usernotownerRecord() {
        //given
        ModifyDocumentCommand documentCommand =
                new ModifyDocumentCommand("just byte text".getBytes(), "filename");
        ModifyJobApplicationCommand command = ModifyJobApplicationCommand.builder()
                .jobId(1L)
                .modifyDocumentCommand(documentCommand)
                .userId(1L)
                .companyName("companyName")
                .jobOfferName("jobOfferName")
                .statusApplication(StatusApplication.APPLIED.name())
                .build();

        DocumentApplication documentApplication = DocumentApplication.builder()
                .documentName("docName")
                .content("old byte text".getBytes())
                .typeFile(TypeFile.PDF)
                .build();

        ApplicationJob applicationJob = ApplicationJob.builder()
                .documentApplication(documentApplication)
                .jobOfferName("jobOfferName")
                .companyName("companyName")
                .statusApplication(StatusApplication.APPLIED)
                .userProfileId(new UserProfileId(13L))
                .build();

        jpaApplicationJob.saveJob(applicationJob);

        //when
        assertThrows(ApplicationJobDomainException.class,()-> {
            modifyJobApplicationService.modify(command);
        });
    }
}