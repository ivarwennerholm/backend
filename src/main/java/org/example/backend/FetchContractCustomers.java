package org.example.backend;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Setter;
import org.example.backend.Model.ContractCustomer;
import org.example.backend.Repository.ContractCustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.List;

@ComponentScan
@Component
public class FetchContractCustomers implements CommandLineRunner {

    @Autowired
    ContractCustomerRepository repo;

//    @Autowired
//    ContractCustomerService service;

    @Setter
    private String filePath;

    private static final Logger logger = LoggerFactory.getLogger(FetchContractCustomers.class);

    public FetchContractCustomers() {
        this.filePath = "https://javaintegration.systementor.se/customers";
    }

    @Override
    public void run(String... args) {
        logger.info("Running FetchContractCustomers");
        try {
            JacksonXmlModule module = new JacksonXmlModule();
            module.setDefaultUseWrapper(false);
            XmlMapper xmlMapper = new XmlMapper(module);

            List<ContractCustomer> contractCustomers;
            if (filePath.startsWith("http")) {
                contractCustomers = xmlMapper.readValue(
                        new URL(filePath),
                        xmlMapper.getTypeFactory().constructCollectionType(List.class, ContractCustomer.class)
                );
            } else {
                File file = new File(filePath);
                contractCustomers = xmlMapper.readValue(
                        file,
                        xmlMapper.getTypeFactory().constructCollectionType(List.class, ContractCustomer.class)
                );
            }

            /*
            List<ContractCustomer> contractCustomers = xmlMapper.readValue(
                    new URL(filePath),
                    xmlMapper.getTypeFactory().constructCollectionType(List.class, ContractCustomer.class)
            );
            */

            logger.info("Fetched {} contract customers", contractCustomers.size());

            repo.deleteAll();
            repo.saveAll(contractCustomers);

            logger.info("Successfully saved all contract customers");
        } catch (Exception e) {
            logger.error("FetchContractCustomer: An error occurred during execution", e);
        }
    }

}
