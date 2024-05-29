package org.example.backend.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordResetTokenServiceImpl tokenService;

    private final PasswordResetTokenRepository tokenRepository;

    private final JavaMailSenderImpl mailSender;

    @Autowired
    public UserServiceImpl (UserRepository userRepository, PasswordResetTokenServiceImpl tokenService, PasswordResetTokenRepository tokenRepository, JavaMailSenderImpl mailSender) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.tokenRepository = tokenRepository;
        this.mailSender = mailSender;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);

        if (user == null) {
            System.out.println("throw exception");
            throw new UsernameNotFoundException("Could not find user");
        }
        System.out.println("loaduserByusername: " + user.getUsername() + " " + user.getPassword());
        return new ConcreteUserDetails(user);
    }

    public void sendEmail(String username){
        User user = userRepository.getUserByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("invalid username");
        }
        String resetLink = generateResetToken(user);
        LocalDateTime resetTokenExpiryDateTime = tokenRepository.findByToken(resetLink).getExpiryDateTime();

        mailSender.setHost("smtp.ethereal.email");
        mailSender.setPort(587);
        mailSender.setUsername("nicolas.grady@ethereal.email");
        mailSender.setPassword("xXA9BeE2bZ95SJn77N");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        String text = "Please check on this link to reset your password: \n\n" +
                "http://localhost:8080/passwordreset/" + resetLink + "\n\n" +
                "Reset link expires: " + resetTokenExpiryDateTime + "\n\n";

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("noreply@hotel.com");
        msg.setTo(mailSender.getUsername());
        msg.setSubject("Reset Password Link");
        msg.setText(text);

        mailSender.send(msg);
    }

    public String generateResetToken(User user){
        UUID uuid = UUID.randomUUID();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime expiryDateTime = currentDateTime.plusDays(1);
        PasswordResetToken resetToken = new PasswordResetToken();

        //check if user exists in token database
        PasswordResetToken p = tokenRepository.findByUserId(user.getId());

        if (p == null){
            //create new reset token if user hasnt had valid token
            resetToken.setUser(user);
            resetToken.setToken(uuid.toString());
            resetToken.setExpiryDateTime(expiryDateTime);
            tokenRepository.save(resetToken);

            return tokenService.getTokenByUsername(user);
        } else if (isTokenExpired(p)){
            //if user has requested token before, check if that token has expired
            p.setToken(uuid.toString());
            p.setExpiryDateTime(expiryDateTime);
            tokenRepository.save(p);
            return tokenService.getTokenByUsername(user);
        } else {
            //if user has valid token still, return existing valid token
            return p.getToken();
        }

    }

    public boolean isTokenExpired(PasswordResetToken previousToken){
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(previousToken.getExpiryDateTime());
    }

    public void updatePassword(PasswordResetDto passwordReset) throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        try{
            PasswordResetToken inputToken = tokenRepository.findByToken(passwordReset.getToken());
            User tokenOwnner = inputToken.getUser();

            if (inputToken == null){
                throw new Exception("token not existing in database");
            }
            if (isTokenExpired(inputToken)){
                throw new Exception("token expired.");
            }
            if (!tokenOwnner.getUsername().equals(passwordReset.getMail())){
                throw new Exception("invalid user");
            }
            String hash = bCryptPasswordEncoder.encode(passwordReset.getNewPassword());
            tokenOwnner.setPassword(hash);
            userRepository.save(tokenOwnner);
            tokenRepository.deleteById(inputToken.getId());
        } catch (NullPointerException e){
            throw new Exception("unexpected error occured");
        }
    }
}
