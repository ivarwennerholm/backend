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
    private Long id;

    private Long shipper_id;
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

    public Shipper(ShipperDto shipDto){
        this.shipper_id = shipDto.getId();
        this.email = shipDto.getEmail();
        this.companyName = shipDto.getCompanyName();
        this.contactName = shipDto.getContactName();
        this.contactTitle = shipDto.getContactTitle();
        this.streetAddress = shipDto.getStreetAddress();
        this.city = shipDto.getCity();
        this.postalCode = shipDto.getPostalCode();
        this.country = shipDto.getCountry();
        this.phone = shipDto.getPhone();
        this.fax = shipDto.getFax();
    }

}
