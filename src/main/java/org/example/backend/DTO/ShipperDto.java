package org.example.backend.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShipperDto {
    private Long id;
    private String email;
    private String companyName;
    private String contactName;
    private String contactTitle;
    private String streetAddress;
    private String city;
    private String postalCode;
    private String country;
    private String phone;
    private String fax;
}
