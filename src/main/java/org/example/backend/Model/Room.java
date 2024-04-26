package org.example.backend1.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Room {


    @Id
    @GeneratedValue
    private Long id;
    private int roomNumber;
    private int pricePerNight;
    @ManyToOne
    @JoinColumn
    private RoomType type;


    public Room(int roomNumber, int pricePerNight, RoomType type) {
        this.roomNumber = roomNumber;
        this.pricePerNight = pricePerNight;
        this.type = type;
    }

}
