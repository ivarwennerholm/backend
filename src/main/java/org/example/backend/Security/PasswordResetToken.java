package org.example.backend.Security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {
    @Id
    @GeneratedValue
    Long id;

    private String token;
    private LocalDateTime expiryDateTime;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id",nullable = false)
    private User user;
}
