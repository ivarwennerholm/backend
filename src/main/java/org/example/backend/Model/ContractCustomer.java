package org.example.backend.Model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractCustomer {
    @Id
    @GeneratedValue
    public Long id;
    @JacksonXmlProperty(localName = "id")
    public int customerId;
    public String companyName;
    public String contactName;
    public String contactTitle;
    public String streetAddress;
    public String city;
    public int postalCode;
    public String country;
    public String phone;
    public String fax;

    /*public ContractCustomer(@JacksonXmlProperty(localName = "id") int customerId,
                            @JacksonXmlProperty(localName = "companyName") String companyName,
                            @JacksonXmlProperty(localName = "contactName") String contactName,
                            @JacksonXmlProperty(localName = "contactTitle") String contactTitle,
                            @JsonProperty("streetAddress") String streetAddress,
                            @JacksonXmlProperty(localName = "city") String city,
                            @JacksonXmlProperty(localName = "postalCode") int postalCode,
                            @JacksonXmlProperty(localName = "country") String country,
                            @JacksonXmlProperty(localName = "phone") String phone,
                            @JacksonXmlProperty(localName = "fax") String fax) {
        this.customerId = customerId;
        this.companyName = companyName;
        this.contactName = contactName;
        this.contactTitle = contactTitle;
        this.streetAddress = streetAddress;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.phone = phone;
        this.fax = fax;
    }*/

}
