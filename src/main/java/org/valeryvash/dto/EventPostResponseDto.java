package org.valeryvash.dto;

import org.valeryvash.model.Event;
import org.valeryvash.model.EventType;
import org.valeryvash.model.File;

import java.io.Serializable;
import java.util.Date;

public class EventPostResponseDto implements Serializable {
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
        id = event.getId();
        timeStamp = event.getTimeStamp();
        eventType = event.getEventType();
        File file = event.getFile();
        fileId = file.getId();
        fileName = file.getFileName();
        filePath = file.getFilePath();
    }

    public Event getEvent() {
        File file = new File();
        file.setId(fileId);
        file.setFileName(fileName);
        file.setFilePath(filePath);
        file.addEvent();
        Event event = file.getEvent();
        event.setId(id);
        event.setTimeStamp(timeStamp);
        event.setEventType(eventType);
        return event;
    }
}
