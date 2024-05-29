package org.example.backend.Configurations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersProperties {

    private String adminUsername;
    private String adminPassword;
    private String receptionistUsername;
    private String receptionistPassword;

}
