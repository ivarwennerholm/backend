package org.example.backend;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.backend.Model.AllContractCustomers;
import org.example.backend.Model.ContractCustomer;
//import org.example.backend.Repository.ContractCustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;

@ComponentScan
public class FetchContractCustomers implements CommandLineRunner {

//    ContractCustomerRepository repo;

    // ANSI colors for readability
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    @Override
    public void run(String... args) throws Exception {
        System.out.println(ANSI_YELLOW + "Running FetchContractCustomers" + ANSI_RESET);
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);
        AllContractCustomers allContractCustomers = xmlMapper.readValue(new URL("https://javaintegration.systementor.se/customers"),
                AllContractCustomers.class
        );
//
        for (ContractCustomer cc : allContractCustomers.list){
            System.out.println(ANSI_GREEN + cc.companyName + ANSI_RESET); // FOR TESTING ONLY
            //repo.save(cc);
        }


    }

}
