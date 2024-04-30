package org.example.backend.Model;

import jakarta.persistence.*;
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
    private String name;
    private String phone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Booking> bookingList;

    public Customer(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Customer(long l, String venusP, String s) {
    }
}

