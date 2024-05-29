package org.example.backend.Security;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long> {
    PasswordResetToken findByUserId(UUID userid);
    PasswordResetToken findByToken(String token);
}
