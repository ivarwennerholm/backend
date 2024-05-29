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
    private String email;

    public CustomerDto(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}
