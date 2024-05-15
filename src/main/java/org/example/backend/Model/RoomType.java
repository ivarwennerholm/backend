package org.example.backend.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomType {

    @Id
    private Long id;
    private String type;
    private int maxExtraBed;
    private int maxPerson;
    private double pricePerNight;

    public RoomType(String type, int maxExtraBed, int maxPerson, double pricePerNight) {
        this.type = type;
        this.maxExtraBed = maxExtraBed;
        this.maxPerson = maxPerson;
        this.pricePerNight = pricePerNight;
    }
}
