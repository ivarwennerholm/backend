package org.example.backend.Events;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
public class RoomOpened extends RoomEvent {

    public RoomOpened(long id, int roomno, LocalDateTime timestamp) {
        super(id,roomno,timestamp);
    }
}
