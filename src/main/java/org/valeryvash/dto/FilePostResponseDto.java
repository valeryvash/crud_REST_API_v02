package org.valeryvash.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.valeryvash.model.File;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FilePostResponseDto implements Serializable {
    private long id;
    private String name;
    private String filePath;

    @Override
    public String toString() {
        return "\nFilePostResponseDto{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", filePath='" + filePath + '\'' +
               '}';
    }

    public FilePostResponseDto(File file) {
        this.id = file.getId();
        this.name = file.getFileName();
        this.filePath = file.getFilePath();
    }

    public File getFile() {
        File file = new File();
        file.setId(this.id);
        file.setFileName(this.getName());
        file.setFilePath(this.getFilePath());
        return file;
    }
}
