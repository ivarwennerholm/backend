package org.example.backend1.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class CustomerDto {
    private Long id;
    private String name;
    private String phone;
}
