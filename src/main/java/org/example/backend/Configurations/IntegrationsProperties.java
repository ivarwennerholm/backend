package org.example.backend.Configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;


@ConfigurationPropertiesScan
@Configuration
@ConfigurationProperties(prefix = "integrations")
@Getter
@Setter
public class IntegrationsProperties {

    private String contractCustomersUrl;
    private String shippersUrl;
    private String blacklistUrl;
    private EmailProperties email;
    private EventsProperties events;
    private UsersProperties users;

}
