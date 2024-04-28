package org.example.backend.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    private Long id;
    private String name;
    private String phone;

    public CustomerDto(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
