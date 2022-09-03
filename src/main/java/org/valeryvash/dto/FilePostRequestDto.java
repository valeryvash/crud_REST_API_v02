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
public class FilePostRequestDto implements Serializable {
    private String name;
    private String filePath;
    private long userId;

    @Override
    public String toString() {
        return "\nFilePostRequestDto{" +
               "name='" + name + '\'' +
               ", filePath='" + filePath + '\'' +
               ", userId=" + userId +
               '}';
    }

    public File getFile() {
        File file = new File();
        file.setFileName(this.name);
        file.setFilePath(this.filePath);
        return file;
    }
}
