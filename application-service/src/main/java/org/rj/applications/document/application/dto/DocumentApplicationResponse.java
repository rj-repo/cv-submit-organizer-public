package org.rj.applications.document.application.dto;

import lombok.Builder;
import org.rj.applications.document.domain.model.DocumentApplication;

import java.util.Arrays;
import java.util.Objects;

@Builder
public record DocumentApplicationResponse(Long documentApplicationId,
                                          String documentName,
                                          byte[] content) {

    public static DocumentApplicationResponse toModel(DocumentApplication documentApplication){
        return DocumentApplicationResponse.builder()
                .content(documentApplication.getContent())
                .documentName(documentApplication.getDocumentName())
                .documentApplicationId(documentApplication.getDocumentApplicationId().id())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DocumentApplicationResponse that = (DocumentApplicationResponse) o;
        return Objects.deepEquals(content, that.content) && Objects.equals(documentName, that.documentName) && Objects.equals(documentApplicationId, that.documentApplicationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentApplicationId, documentName, Arrays.hashCode(content));
    }
}
