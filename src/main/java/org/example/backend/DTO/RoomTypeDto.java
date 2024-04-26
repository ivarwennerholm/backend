package org.example.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomTypeDto {
    private Long id;
    private String type;
    private int maxExtraBed;
    private int maxPerson;

}
