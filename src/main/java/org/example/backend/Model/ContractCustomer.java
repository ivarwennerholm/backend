package org.example.backend.Model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ContractCustomer {

    @Id
    @GeneratedValue
    private Long id;

    @JacksonXmlProperty(localName = "id")
    @Column(name = "customer_id")
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


    public Long getId() {
        return id;
    }

    @JacksonXmlProperty(localName = "id")
    public int getCustomerId() {
        return customerId;
    }

}
