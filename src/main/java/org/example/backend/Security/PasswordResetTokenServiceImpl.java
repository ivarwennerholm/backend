package org.example.backend.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenServiceImpl {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public String getTokenByUsername(User user){
        return passwordResetTokenRepository.findByUserId(user.getId()).getToken();
    };
}
