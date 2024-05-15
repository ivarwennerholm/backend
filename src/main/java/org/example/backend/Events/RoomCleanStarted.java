package org.example.backend.Events;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@DiscriminatorValue("RoomCleaningStarted")
public class RoomCleanStarted extends Log {
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
