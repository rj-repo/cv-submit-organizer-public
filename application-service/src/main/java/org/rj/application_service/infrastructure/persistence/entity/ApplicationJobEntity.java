package org.rj.application_service.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rj.application_service.domain.model.ApplicationJob;
import org.rj.application_service.domain.model.ApplicationJobId;
import org.rj.application_service.domain.model.StatusApplication;
import org.rj.application_service.infrastructure.documents.persistence.entity.DocumentEntity;
import org.rj.cvsubmitorganizer.common.UserProfileId;

@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(schema = "applications", name = "applications")
public class ApplicationJobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    @Enumerated(EnumType.STRING)
    private StatusApplication statusApplication;
    private String jobOfferName;
    @Setter
    @OneToOne(orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "document_id")
    private DocumentEntity document;
    private Long userId;


    public ApplicationJob toModel() {
        return ApplicationJob.builder()
                .applicationJobId(new ApplicationJobId(id))
                .companyName(companyName)
                .statusApplication(statusApplication)
                .jobOfferName(jobOfferName)
                .userProfileId(new UserProfileId(userId))
                .documentApplication(document.toModel())
                .build();
    }

    public static ApplicationJobEntity of(ApplicationJob applicationJob) {
        return ApplicationJobEntity.builder()
                .id(applicationJob.getApplicationJobId() != null ?
                        applicationJob.getApplicationJobId().id() : null)
                .companyName(applicationJob.getCompanyName())
                .statusApplication(applicationJob.getStatusApplication())
                .jobOfferName(applicationJob.getJobOfferName())
                .userId(applicationJob.getUserProfileId().id())
                .document(DocumentEntity.of(applicationJob.getDocumentApplication()))
                .build();
    }
}
