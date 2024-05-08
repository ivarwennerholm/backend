package org.example.backend.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShipperDto {
    Long id;
    String email;
    String contactName;
    String companyName;
    String contactTitle;
    String streetAddress;
    String city;
    String postalCode;
    String country;
    String phone;
    String fax;
}