package org.rj.applications.document.domain.model.command;

import lombok.Builder;
import org.apache.commons.lang3.StringUtils;
import org.rj.applications.document.domain.model.DocumentApplication;
import org.rj.applications.document.domain.model.TypeFile;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

@Builder
public record ModifyDocumentCommand(byte[] fileContent,
                                    String filename) {

    public DocumentApplication toModel(){
        return DocumentApplication.builder()
                .documentName(filename)
                .content(fileContent)
                .typeFile(TypeFile.valueOf(getFileFormat()))
                .build();
    }

    private String getFileFormat(){
        int index = StringUtils.lastIndexOf(filename, ".");
        return filename.substring(index+1).toUpperCase(Locale.ROOT);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ModifyDocumentCommand that = (ModifyDocumentCommand) o;
        return Objects.equals(filename, that.filename) && Objects.deepEquals(fileContent, that.fileContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(fileContent), filename);
    }

    @Override
    public String toString() {
        return "ModifyDocumentCommand{" +
                "fileContent=" + Arrays.toString(fileContent) +
                ", filename='" + filename + '\'' +
                '}';
    }
}
