package org.example.backend.Security;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordResetDto {
    private String token;
    private String mail;
    private String newPassword;
}
