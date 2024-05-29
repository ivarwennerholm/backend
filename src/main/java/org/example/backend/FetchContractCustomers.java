package org.example.backend;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Setter;
import org.example.backend.Configurations.IntegrationsProperties;
import org.example.backend.Model.ContractCustomer;
import org.example.backend.Repository.ContractCustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.net.URL;
import java.util.List;

import static org.example.backend.BackendApplication.*;

@ComponentScan
public class FetchContractCustomers implements CommandLineRunner {

    private final ContractCustomerRepository repo;
    private final IntegrationsProperties integrations;
    private static final Logger logger = LoggerFactory.getLogger(FetchContractCustomers.class);

    @Setter
    private String filePath;

    @Autowired
    protected FetchContractCustomers(ContractCustomerRepository repo, IntegrationsProperties integrations) {
        this.repo = repo;
        this.integrations = integrations;
        this.filePath = integrations.getContractCustomersUrl();
    }

    @Override
    public void run(String... args) {
        logger.info(ANSI_YELLOW + "Running FetchContractCustomers" + ANSI_RESET);
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
            logger.info(ANSI_GREEN + "Fetched " + contractCustomers.size() + " contract customers" + ANSI_RESET);
            repo.deleteAll();
            repo.saveAll(contractCustomers);
            logger.info(ANSI_GREEN + "Successfully saved all contract customers" + ANSI_RESET);
        } catch (Exception e) {
            logger.error(ANSI_RED + "FetchContractCustomer: An error occurred during execution" + ANSI_RESET, e);
        }
    }

}
