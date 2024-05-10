package org.example.backend.DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
