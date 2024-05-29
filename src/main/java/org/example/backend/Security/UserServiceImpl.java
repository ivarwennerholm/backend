package org.example.backend.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordResetTokenServiceImpl tokenService;

    private final PasswordResetTokenRepository tokenRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);

        if (user == null) {
            System.out.println("throw exception");
            throw new UsernameNotFoundException("Could not find user");
        }
//        if (!inputUser.getPassword().equals(user.getPassword())){
//            System.out.println("throw bad exception");
//            throw new BadCredentialsException("Invalid password");
//        }
        System.out.println("loaduserByusername: " + user.getUsername() + " " + user.getPassword());
        return new ConcreteUserDetails(user);
    }

    public String sendEmail(User user) throws Exception {
        try{
            String resetLink = generateResetToken(user);
            System.out.println(resetLink);
            return "";
        }catch (NullPointerException e){
            throw new Exception("No valid email");
        }


//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.ethereal.email");
//        mailSender.setPort(587);
//        mailSender.setUsername("nicolas.grady@ethereal.email");
//        mailSender.setPassword("xXA9BeE2bZ95SJn77N");
//
//        String text = "Please check on this link to reset your password: " + resetLink + ". \n\n";
//
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setFrom("noreply@hotel.se");
//        msg.setTo(mailSender.getUsername());
//        msg.setSubject("Reset Password Link");
//        msg.setText(text);
//
//        mailSender.send(msg);
//        return "msg sent";
    }

    public String generateResetToken(User user){
        PasswordResetToken p = tokenRepository.findByUserId(user.getId());
        UUID uuid = UUID.randomUUID();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime expiryDateTime = currentDateTime.plusDays(1);
        PasswordResetToken resetToken = new PasswordResetToken();
        if (p == null){
            resetToken.setUser(user);
            resetToken.setToken(uuid.toString());
            resetToken.setExpiryDateTime(expiryDateTime);
            tokenRepository.save(resetToken);

            return "im generating reset token here: " + tokenService.getTokenByUsername(user);
        } else if (isTokenExpired(p)){
            p.setToken(uuid.toString());
            p.setExpiryDateTime(expiryDateTime);
            tokenRepository.save(p);
            return "im generating NEW reset token here: " + tokenService.getTokenByUsername(user);
        } else {
            return "Token already created";
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
            if (inputToken == null){
                throw new Exception("token not existing in database");
            }
            if (isTokenExpired(inputToken)){
                throw new Exception("token expired.");
            }
            User user = userRepository.getUserByUsername(passwordReset.getMail());
            if (user == null){
                throw new Exception("no such user in database");
            }
            PasswordResetToken passwordResetToken = tokenRepository.findByUserId(user.getId());
            if (passwordResetToken == null){
                throw new Exception("no token found for such user");
            }
            String hash = bCryptPasswordEncoder.encode(passwordReset.getNewPassword());
            user.setPassword(hash);
            userRepository.save(user);
            tokenRepository.deleteById(inputToken.getId());
        } catch (NullPointerException e){
            throw new Exception("unexpected error occured");
        }
    }
}
