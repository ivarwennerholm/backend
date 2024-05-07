package org.example.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractCustomerDto {
    private Long id;
    public String companyName;
    public String contactName;
    public String country;
}
