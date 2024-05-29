package org.example.backend.Events;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
public class RoomOpened extends RoomEvent {

    public RoomOpened(long id, int roomno, LocalDateTime timestamp) {
        super(id,roomno,timestamp);
    }

}
