package org.example.backend.Events;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class RoomCleanStarted extends RoomEvent {

    @JsonProperty(value = "CleaningByUser")
    private String cleaner;

    public RoomCleanStarted(Long id, int roomno, LocalDateTime timestamp, String cleaner) {
        super(id,roomno, timestamp);
        this.cleaner = cleaner;
    }

    @Override
    public String toString() {
        return "RoomEvent{" +
                "id=" + this.getId() +
                ", roomno='" + this.getRoomno() + '\'' +
                ", dateTime='" + this.getTimestamp() + '\'' +
                ", cleaner='" + this.getCleaner() +
                '\'' +
                '}';
    }

    @Override
    public String getCleaner() {
        return cleaner;
    }
}
