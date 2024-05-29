package org.example.backend.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.example.backend.Configurations.IntegrationsProperties;
import org.example.backend.Model.EmailTemplate;
import org.example.backend.Repository.EmailTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

import static org.example.backend.BackendApplication.*;

@Service
@RequiredArgsConstructor
public class EmailTemplateService {

    @Autowired
    IntegrationsProperties integrations;

    private final EmailTemplateRepository emailTemplateRepository;
    private final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    private final Logger logger = LoggerFactory.getLogger(EmailTemplateService.class);

    public void sendMail(int roomNumber, String checkin, String checkout, String guests, String extrabeds,
                         String name, String phone, String email, String fullprice, String discount, String discountedPrice) {
        mailSender.setHost(integrations.getEmail().getSenderHost());
        mailSender.setPort(integrations.getEmail().getSenderPort());
        mailSender.setUsername(integrations.getEmail().getUsername());
        mailSender.setPassword(integrations.getEmail().getPassword());
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setFrom("nicolas.grady@ethereal.email");
            helper.setSubject("Your booking confirmation");
            String markup = emailTemplateRepository.getLatestEmailTemplate();
            markup = markup.replace("!!!name!!!", name).
                    replace("!!!phone!!!", phone).
                    replace("!!!roomnumber!!!", String.valueOf(roomNumber)).
                    replace("!!!checkin!!!", checkin).
                    replace("!!!checkout!!!", checkout).
                    replace("!!!guests!!!", guests).
                    replace("!!!extrabeds!!!", extrabeds).
                    replace("!!!fullprice!!!", fullprice).
                    replace("!!!discount!!!", discount).
                    replace("!!!discountedprice!!!", discountedPrice);
            helper.setText(markup, true);
            mailSender.send(message);
            logger.info(ANSI_GREEN + "Confirmation email sent" + ANSI_RESET);
        } catch (MessagingException e) {
            logger.error(ANSI_RED + "EmailService: Error sending email" + ANSI_RESET, e);
        }

    }

    public String getLatestEmailTemplate() {
        return emailTemplateRepository.getLatestEmailTemplate();
    }

    public void saveTemplatetoDatabase(String markup) {
        emailTemplateRepository.save(new EmailTemplate(markup));
    }

}
