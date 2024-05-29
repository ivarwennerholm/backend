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
    private Long id;
    private String email;
    private String name;
    private String group;
    private String created;
    private boolean ok;
}
