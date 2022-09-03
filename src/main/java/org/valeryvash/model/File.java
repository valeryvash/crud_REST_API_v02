package org.valeryvash.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(
            name = "name",
            nullable = false
    )
    private String fileName;
    @Column(
            name = "filePath",
            nullable = false
    )
    private String filePath;
    @OneToOne(
            targetEntity = Event.class,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            fetch = FetchType.LAZY,
            mappedBy = "file",
            optional = false,
            orphanRemoval = true
    )
    private Event event;

    @Override
    public String toString() {
        return "\nFile{" +
               "id=" + id +
               ", fileName='" + fileName + '\'' +
               ", filePath='" + filePath + '\'' +
               '}';
    }

    /**
     *  Helper methods
     *  call this method while new file is created.
     *  It will create new event and link it with this file
     */
    public void addEvent() {
        this.event = new Event();
        this.event.setFile(this);
    }

    public void changeFileEventType(EventType eventType) {
        this.event.setEventType(eventType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        File file = (File) o;

        return id == file.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
