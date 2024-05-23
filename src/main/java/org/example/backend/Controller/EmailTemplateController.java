package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.ResetEmailTemplate;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
            Files.writeString(filePath, markup);
            logger.info(ANSI_GREEN + "Successfully wrote changes to current email template" + ANSI_RESET);
        } catch (IOException e) {
            logger.error(ANSI_RED + "Error writing to email template filen" + ANSI_RESET, e);
        }
        return "currentEmailTemplate.html";
    }

}


