package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Service.Impl.EmailTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.example.backend.BackendApplication.*;

@Controller
@RequestMapping("emailtemplate")
@RequiredArgsConstructor
public class EmailTemplateController {

    private final EmailTemplateService service;

    private static final Logger logger = LoggerFactory.getLogger(EmailTemplateController.class);

    @GetMapping("/current")
    public String getCurrentEmailTemplate() {
        return "currentEmailTemplate.html";
    }

    @GetMapping("/default")
    public String getDefaultEmailTemplate() {
        return "defaultEmailTemplate.html";
    }

    @GetMapping("/edit")
    public String getEmailTemplateEditor(Model model) {
        String markup = service.getLatestEmailTemplate();
        model.addAttribute("markup", markup);
        return "emailTemplateEditor.html";
    }

    @PostMapping("/edit/done")
    public String getEditConfirmation(@RequestParam(name = "markup") String markup) {
        service.saveTemplatetoDatabase(markup);
        Path filePath = Paths.get("src/main/resources/templates/currentEmailTemplate.html");
        try {
            Files.write(filePath, markup.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            logger.info(ANSI_GREEN + "Successfully wrote changes to current email template" + ANSI_RESET);
        } catch (IOException e) {
            logger.error(ANSI_RED + "Error writing to email template file" + ANSI_RESET, e);
        }
        int maxRetries = 20;
        int retryCount = 0;
        int waitTime = 2000;
        while (retryCount < maxRetries && (!Files.exists(filePath) || !Files.isReadable(filePath))) {
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            retryCount++;
        }
        if (Files.exists(filePath) && Files.isReadable(filePath)) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                logger.error(ANSI_RED + "Thread was interrupted while waiting" + ANSI_RESET, ex);
            }

            logger.info(ANSI_GREEN + "File is successfully written and ready to be accessed" + ANSI_RESET);
            return "showCurrentEmailTemplate";
        } else {
            logger.error(ANSI_RED + "File does not exist or is not accessible after waiting" + ANSI_RESET);
            return "Failed writing new email template";
        }
    }

}


