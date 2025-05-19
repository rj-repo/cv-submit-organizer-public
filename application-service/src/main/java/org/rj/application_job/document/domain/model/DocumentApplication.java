package org.rj.application_job.document.domain.model;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.rj.application_job.document.domain.model.command.ModifyDocumentCommand;

import java.util.Objects;

@Getter
@Builder
public class DocumentApplication {
    private DocumentApplicationId documentApplicationId;
    private String documentName;
    private byte[] content;
    private TypeFile typeFile;

    public void modify(ModifyDocumentCommand modifyDocumentCommand) {
        this.content = Objects.nonNull(modifyDocumentCommand.fileContent()) ?
                modifyDocumentCommand.fileContent() : content;
        this.documentName = StringUtils.isNoneBlank(modifyDocumentCommand.filename()) ?
                modifyDocumentCommand.filename() : documentName;
    }
}
