package org.example.backend.Events;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
//@DiscriminatorValue("RoomClosed")
public class RoomClosed extends RoomEvent {
    @Id
    @GeneratedValue
    Long id;

}
