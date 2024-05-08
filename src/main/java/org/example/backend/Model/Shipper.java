package org.example.backend.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.example.backend.DTO.ShipperDto;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Shipper {
    @Id
    @GeneratedValue
    Long id;

    Long shipper_id;
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

    public Shipper(ShipperDto shipDto){
        this.shipper_id = shipDto.getId();
        this.email = shipDto.getEmail();
        this.contactName = shipDto.getContactName();
        this.companyName = shipDto.getCompanyName();
        this.contactTitle = shipDto.getContactTitle();
        this.streetAddress = shipDto.getStreetAddress();
        this.city = shipDto.getCity();
        this.postalCode = shipDto.getPostalCode();
        this.country = shipDto.getCountry();
        this.phone = shipDto.getPhone();
        this.fax = shipDto.getFax();
    }
}
