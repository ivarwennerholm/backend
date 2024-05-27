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
public class EmailProperties {
    private String templateFilepath;
    private String senderHost;
    private int senderPort;
}
