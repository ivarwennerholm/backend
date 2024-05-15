package org.example.backend.Events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
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
public abstract class Log {
    @Id
    @GeneratedValue
    Long id;

    @JsonProperty(value = "RoomNo")
    String roomno;
    @JsonProperty(value = "TimeStamp")
    String dateTime;

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", roomno='" + roomno + '\'' +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }
}
