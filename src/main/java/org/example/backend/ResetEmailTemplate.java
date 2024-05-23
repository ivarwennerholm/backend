package org.example.backend;

import org.example.backend.Model.EmailTemplate;
import org.example.backend.Repository.EmailTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.io.*;

import static org.example.backend.BackendApplication.*;

@ComponentScan
public class ResetEmailTemplate implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ResetEmailTemplate.class);

    String filePath, markup;
    File file;
    FileReader fileReader;

    @Autowired
    EmailTemplateRepository emailTemplateRepository;

    @Override
    public void run(String... args) {
        logger.info(ANSI_YELLOW + "Running ResetEmailTemplate" + ANSI_RESET);
        try {
            filePath = "src/main/resources/templates/emailTemplate.html";
            file = new File(filePath);
            fileReader = new FileReader(file);
        } catch (FileNotFoundException ex) {
            logger.error(ANSI_RED + "ResetEmailTemplate: File not found (" + filePath + ")" + ANSI_RESET, ex);
            return; // Exit the method if file not found
        }

        try (LineNumberReader lnr = new LineNumberReader(fileReader)) {
            String line;
            StringBuilder markupBuilder = new StringBuilder(); // Use StringBuilder for efficient string concatenation
            while ((line = lnr.readLine()) != null) {
                markupBuilder.append(line).append("\n");
            }
            String markup = markupBuilder.toString().trim(); // Trim leading and trailing whitespace
            if (!markup.isEmpty()) {
                EmailTemplate emailTemplate = new EmailTemplate(markup);
                emailTemplateRepository.save(emailTemplate);
                logger.info(ANSI_GREEN + "Successfully read email template from file and wrote to database" + ANSI_RESET);
            } else {
                logger.warn(ANSI_YELLOW + "ResetEmailTemplate: Email template is empty" + ANSI_RESET);
            }
        } catch (IOException ex) {
            logger.error(ANSI_RED + "ResetEmailTemplate: An error occurred during execution" + ANSI_RESET, ex);
        }
    }

}
