package scheduler.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic emailTasksTopic() {
        return TopicBuilder.name("EMAIL_SENDING_TASKS")
                .partitions(1)
                .replicas(1)
                .build();
    }
}