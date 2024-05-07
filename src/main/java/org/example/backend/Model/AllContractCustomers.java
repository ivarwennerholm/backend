package org.example.backend.Model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "allcustomers")
public class AllContractCustomers {
     @JacksonXmlProperty(localName = "customers")
     public List<customers> list;
}
