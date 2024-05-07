package org.example.backend.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class customers {
    @JacksonXmlProperty(localName = "id")
    public int customerId;
    @JacksonXmlProperty(localName = "companyName")
    public String companyName;
    @JacksonXmlProperty(localName = "contactName")
    public String contactName;
    @JacksonXmlProperty(localName = "contactTitle")
    public String contactTitle;
    @JacksonXmlProperty(localName = "streetAddress")
    public String streetAddress;
    @JacksonXmlProperty(localName = "city")
    public String city;
    @JacksonXmlProperty(localName = "postalCode")
    public int postalCode;
    @JacksonXmlProperty(localName = "country")
    public String country;
    @JacksonXmlProperty(localName = "phone")
    public String phone;
    @JacksonXmlProperty(localName = "fax")
    public String fax;

    @JsonCreator
    public customers(@JsonProperty("id") int customerId,
                     @JsonProperty("companyName") String companyName,
                     @JsonProperty("contactName") String contactName,
                     @JsonProperty("contactTitle") String contactTitle,
                     @JsonProperty("streetAddress") String streetAddress,
                     @JsonProperty("city") String city,
                     @JsonProperty("postalCode") int postalCode,
                     @JsonProperty("country") String country,
                     @JsonProperty("phone") String phone,
                     @JsonProperty("fax") String fax) {
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
