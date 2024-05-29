package org.example.backend.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ContractCustomerDto {
    private Long id;
    private int customerId;
    private String companyName;
    private String contactName;
    private String contactTitle;
    private String streetAddress;
    private String city;
    private int postalCode;
    private String country;
    private String phone;
    private String fax;
}
