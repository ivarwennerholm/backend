package org.example.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlacklistPersonDto {
    Long id;
    String email;
    String name;
    String group;
    String created;
    boolean ok;
}
