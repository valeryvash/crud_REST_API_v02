package org.valeryvash.dto;

import lombok.*;
import org.valeryvash.model.Event;
import org.valeryvash.model.File;
import org.valeryvash.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserPostRequestDto implements Serializable {
    private String name;
    private List<FilePostRequestDto> files;

    @Override
    public String toString() {
        return "\nUserPostRequestDto{" +
               "name='" + name + '\'' +
               ", files=" + files +
               '}';
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class FilePostRequestDto {
        private String fileName;
        private String filePath;

        @Override
        public String toString() {
            return "\nFilePostRequestDto{" +
                   "fileName='" + fileName + '\'' +
                   ", filePath='" + filePath + '\'' +
                   '}';
        }
    }

    public User getUser() {
        User user = new User();
        user.setName(name);
        if (this.files != null && !this.files.isEmpty()) {
            for (FilePostRequestDto requestDto : files) {
                File file = new File();
                file.setFileName(requestDto.fileName);
                file.setFilePath(requestDto.filePath);
                file.addEvent();
                user.addFile(file);
            }
        } else {
            user.setEvents(new ArrayList<>());
        }
        return user;
    }

}
