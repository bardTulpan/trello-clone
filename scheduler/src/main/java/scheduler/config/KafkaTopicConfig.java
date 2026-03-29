package scheduler.config;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@AllArgsConstructor
public class KafkaTopicConfig {

    private final SchedulerProperties scheduler;

    @Bean
    public NewTopic emailTasksTopic() {
        return TopicBuilder.name(scheduler.getKafkaTopic())
                .partitions(1)
                .replicas(1)
                .build();
    }
}