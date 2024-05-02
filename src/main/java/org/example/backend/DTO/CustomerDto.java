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

    @NotEmpty(message = "Name is mandatory")
    @Size(min = 2, message = "Type a name that is at least 2 characters")
    @Pattern(regexp = "^[A-Öa-ö]*$", message = "Only letters for name")
    private String name;

    @NotEmpty(message = "Telephone number is mandatory")
    @Size(min = 5, max = 16, message = "You must type a telephone number of at least 5 but not more than 16 digits")
    @Pattern(regexp = "^\\d{1,5}(?:-\\d{3,11})?$", message = "Only digits for telephone number")
    private String phone;

    public CustomerDto(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
