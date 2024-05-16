package org.example.backend.Events;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class RoomCleanStarted extends RoomEvent {
    @Id
    @GeneratedValue
    Long id;

    @JsonProperty(value = "CleaningByUser")
    String cleaner;

    @Override
    public String toString() {
        return "RoomStatus{" +
                "room=" + roomno +
                ", dateTime='" + timestamp +
                ", cleaner='" + cleaner +
                '\'' +
                '}';
    }

    @Override
    public String getCleaner() {
        return cleaner;
    }
}
