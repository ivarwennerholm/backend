package org.example.backend.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
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

    public ContractCustomer(@JacksonXmlProperty(localName = "id") int customerId,
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
    }

}










/*
package org.example.backend.Model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;

//@Data
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@JacksonXmlRootElement(localName = "customers")
public class customers {
//    @Id
//    @GeneratedValue
//    private Long id;
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

    public customers(int customerId, String companyName, String contactName, String contactTitle, String streetAddress, String city, int postalCode, String country, String phone, String fax) {
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
    }
}*/
