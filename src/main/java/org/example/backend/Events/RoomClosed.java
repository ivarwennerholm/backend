package org.example.backend.Events;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class RoomClosed extends RoomEvent {
    public RoomClosed(long id, int roomno, LocalDateTime timestamp) {
        super(id,roomno,timestamp);
    }
}
