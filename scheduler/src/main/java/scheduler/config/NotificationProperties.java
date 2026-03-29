package scheduler.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "notifications")
public class NotificationProperties {

    private String titleMixed;
    private String titleFinished;
    private String titleUnfinished;

    private String finishedHeader;
    private String unfinishedHeader;
    private String bullet;
}