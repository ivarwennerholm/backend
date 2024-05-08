package org.example.backend.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty(message = "Name is mandatory")
    @Size(min = 2, message = "Type a name that is at least 2 characters")
    @Pattern(regexp = "^[A-Za-zÅÄÖåäö\\s]*$", message = "Only letters and whitespace for name")
    // @Pattern(regexp = "^[A-Öa-ö]*$", message = "Only letters for name")
    private String name;
    @NotEmpty(message = "Telephone number is mandatory")
    @Size(min = 5, max = 16, message = "You must type a telephone number of at least 5 but not more than 16 digits")
    @Pattern(regexp = "^[\\d+-]*$", message = "Telephone numbers can only contain digits and hyphens")
    private String phone;

    @NotEmpty(message = "Email is mandatory")
    @Pattern(regexp = "^(.+)@(\\S+)$", message = "Please provide a valid email")
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Booking> bookingList;

    public Customer(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Customer(Long id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Customer(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

}
