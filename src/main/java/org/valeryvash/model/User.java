package org.valeryvash.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(
            name = "name",
            nullable = false,
            length = 25
    )
    private String name;
    @OneToMany(
            targetEntity = Event.class,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.LAZY,
            mappedBy = "user",
            orphanRemoval = true
    )
    private List<Event> events;

    @Override
    public String toString() {
        return "\n\nUser{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", events=" + events +
               '}';
    }

    public void addEvents(List<Event> events) {
        this.events = events;
        this.events.forEach(event -> event.setUser(this));
    }

    public void addEvent(Event event) {
        this.events.add(event);
        event.setUser(this);
    }

    public void removeEvent(long id) {
        Event eventToBeRemoved = null;
        for (Event event : this.events) {
            if (event.getId() == id) {
                eventToBeRemoved = event;
                event.setUser(null);
                event.getFile().setEvent(null);
                event.setFile(null);
            }
        }
        if (eventToBeRemoved != null) {
            this.events.remove(eventToBeRemoved);
        }
    }

    public void addFiles(List<File> files) {
        List<Event> eventsToBeAdded =
                files.stream()
                        .map(File::getEvent)
                        .toList();
        eventsToBeAdded.forEach(event -> event.setUser(this));
        if (this.events == null) {
            events = new ArrayList<>();
        }
        this.events.addAll(eventsToBeAdded);
    }

    public void addFile(File file) {
        Event event = file.getEvent();
        if (this.events == null) {
            this.events = new ArrayList<>();
        }
        event.setUser(this);
        this.events.add(event);
    }

    public void removeFile(long id) {
        Event toBeRemoved = null;
        for (Event event1 : this.events) {
            File file = event1.getFile();
            if (file.getId() == id) {
                toBeRemoved = file.getEvent();
                event1.setUser(null);
                event1.setFile(null);
                file.setEvent(null);
            }
        }
        if (toBeRemoved != null) {
            this.events.remove(toBeRemoved);
        }
    }
}
