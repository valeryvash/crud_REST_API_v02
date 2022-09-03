package org.valeryvash.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Temporal(value = TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(
            name = "timestamp",
            nullable = false,
            updatable = false
    )
    private Date timeStamp;
    @Enumerated(EnumType.STRING)
    @Column(
            table = "events",
            name = "event_type",
            nullable = false
    )
    private EventType eventType = EventType.CREATED;
    @ManyToOne(
        targetEntity = User.class,
            cascade = {CascadeType.MERGE},
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            table = "events",
            name = "user_id",
            nullable = false
    )
    private User user;
    @OneToOne(
        targetEntity = File.class,
            cascade ={ CascadeType.PERSIST,CascadeType.MERGE},
            fetch = FetchType.LAZY,
            optional = false,
            orphanRemoval = true
    )
    @JoinColumn(
            table = "events",
            name = "file_id",
            nullable = false
    )
    private File file;

    @Override
    public String toString() {
        return "\nEvent{" +
               "id=" + id +
               ", timeStamp=" + timeStamp +
               ", eventType=" + eventType +
               ", file=" + file +
               '}';
    }

    /**
     * Helper method
     */
    public void addFile(File file) {
        this.file = file;
        file.setEvent(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        return id == event.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
