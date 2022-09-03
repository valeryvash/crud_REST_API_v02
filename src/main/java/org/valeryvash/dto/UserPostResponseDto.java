package org.valeryvash.dto;

import lombok.*;
import org.valeryvash.model.Event;
import org.valeryvash.model.EventType;
import org.valeryvash.model.File;
import org.valeryvash.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserPostResponseDto implements Serializable {
    private long id;
    private String name;
    private List<EventPostResponseDto> events;

    @Override
    public String toString() {
        return "\nUserPostResponseDto{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", events=" + events +
               '}';
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class EventPostResponseDto {
        private long id;
        private Date timeStamp;
        private EventType eventType;
        private long fileId;
        private String fileName;
        private String filePath;

        @Override
        public String toString() {
            return "\nEventPostResponseDto{" +
                   "id=" + id +
                   ", timeStamp=" + timeStamp +
                   ", eventType=" + eventType +
                   ", fileId=" + fileId +
                   ", fileName='" + fileName + '\'' +
                   ", filePath='" + filePath + '\'' +
                   '}';
        }

        public EventPostResponseDto(Event event) {
            this.id = event.getId();
            this.timeStamp = event.getTimeStamp();
            this.eventType = event.getEventType();
            this.fileId = event.getFile().getId();
            this.fileName = event.getFile().getFileName();
            this.filePath = event.getFile().getFilePath();
        }

        public Event getEvent() {
            Event event = new Event();
            event.setId(this.id);
            event.setTimeStamp(this.timeStamp);
            event.setEventType(this.eventType);

            File file = new File();
            file.setId(fileId);
            file.setFileName(fileName);
            file.setFilePath(filePath);
            event.addFile(file);
            return event;
        }
    }

    public UserPostResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.events = new ArrayList<>();

        for (Event event : user.getEvents()) {
            this.events.add(new EventPostResponseDto(event));
        }
    }

    public User getUser() {
        User user = new User();
        user.setId(this.id);
        user.setName(this.name);
        user.setEvents(new ArrayList<>());

        if (this.events != null && !this.events.isEmpty()) {
            for (EventPostResponseDto eventPostResponseDto : this.events) {
                Event event = eventPostResponseDto.getEvent();
                user.addEvent(event);
            }
        }
        return user;
    }
}
