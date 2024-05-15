package org.example.backend.Events;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@DiscriminatorValue("RoomClosed")
public class RoomClosed extends Log {
    @Id
    @GeneratedValue
    Long id;

}
