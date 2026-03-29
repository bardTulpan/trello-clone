package emailSender.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "email.sender")
public class EmailProperties {
    private String address;
    private String name;
    private String htmlTemplate;
}