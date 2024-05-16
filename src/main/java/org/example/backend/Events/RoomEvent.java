package org.example.backend.Events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RoomOpened.class, name = "RoomOpened"),
        @JsonSubTypes.Type(value = RoomClosed.class, name = "RoomClosed"),
        @JsonSubTypes.Type(value = RoomCleanStarted.class, name = "RoomCleaningStarted"),
        @JsonSubTypes.Type(value = RoomCleanDone.class, name = "RoomCleaningFinished")
})
@Data
@RequiredArgsConstructor
public abstract class RoomEvent {

    @Id
    @GeneratedValue
    Long id;

    @JsonProperty(value = "RoomNo")
    int roomno;

    @JsonProperty(value = "TimeStamp")
    LocalDateTime timestamp;

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", roomno='" + roomno + '\'' +
                ", dateTime='" + timestamp + '\'' +
                '}';
    }

    public String getCleaner(){return null;};
}