package org.example.backend;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.backend.Model.AllContractCustomers;
import org.example.backend.Model.ContractCustomer;
import org.example.backend.Repository.ContractCustomerRepository;
import org.example.backend.Service.ContractCustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;

@ComponentScan
public class FetchContractCustomers implements CommandLineRunner {

    @Autowired
    ContractCustomerRepository repo;

    @Autowired
    ContractCustomerService service;

    private static final Logger logger = LoggerFactory.getLogger(FetchContractCustomers.class);

    @Override
    public void run(String... args) {
        logger.info("Running FetchContractCustomers");
        try {
            JacksonXmlModule module = new JacksonXmlModule();
            module.setDefaultUseWrapper(false);
            XmlMapper xmlMapper = new XmlMapper(module);
            AllContractCustomers allContractCustomers = xmlMapper.readValue(new URL("https://javaintegration.systementor.se/customers"),
                    AllContractCustomers.class
            );
            repo.deleteAll();
            for (ContractCustomer cc : allContractCustomers.list) {
                repo.save(cc);
            }
        } catch (Exception e) {
            logger.error("FetchContractCustomer: An error occurred during execution", e);
        }
    }

}
