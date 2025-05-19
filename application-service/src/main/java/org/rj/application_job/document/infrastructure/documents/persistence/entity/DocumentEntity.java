package org.rj.application_job.document.infrastructure.documents.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.rj.application_job.document.domain.model.DocumentApplication;
import org.rj.application_job.document.domain.model.DocumentApplicationId;
import org.rj.application_job.document.domain.model.TypeFile;

@Entity
@Table(schema = "applications", name = "documents")
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private byte[] content;
    private String documentName;
    @Enumerated(EnumType.STRING)
    private TypeFile typeFile;


    public DocumentApplication toModel() {
        return DocumentApplication.builder()
                .documentApplicationId(new DocumentApplicationId(id))
                .documentName(documentName)
                .typeFile(typeFile)
                .content(content)
                .build();
    }

    public static DocumentEntity of(DocumentApplication documentApplication) {
        return DocumentEntity.builder()
                .id(documentApplication.getDocumentApplicationId() != null ?
                        documentApplication.getDocumentApplicationId().id() : null)
                .content(documentApplication.getContent())
                .documentName(documentApplication.getDocumentName())
                .typeFile(documentApplication.getTypeFile())
                .build();
    }
}
