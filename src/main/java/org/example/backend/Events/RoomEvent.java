package org.example.backend.Events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
//@RequiredArgsConstructor
public abstract class RoomEvent {

    @Id
    @GeneratedValue
    Long id;

    @JsonProperty(value = "RoomNo")
    int roomno;

    @JsonProperty(value = "TimeStamp")
    LocalDateTime timestamp;

    public RoomEvent(Long id, int roomno, LocalDateTime timestamp) {
        this.id = id;
        this.roomno = roomno;
        this.timestamp = timestamp;
    }

    public RoomEvent(int roomno, LocalDateTime timestamp) {
        this.roomno = roomno;
        this.timestamp = timestamp;
    }
    @Override
    public String toString() {
        return "RoomEvent{" +
                "id=" + id +
                ", roomno='" + roomno + '\'' +
                ", dateTime='" + timestamp + '\'' +
                '}';
    }

    public String getCleaner(){return null;};
}
