package org.example.backend1.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
enum Type {
    SINGLE, DOUBLE, LARGE_DOUBLE
}
*/

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
}
