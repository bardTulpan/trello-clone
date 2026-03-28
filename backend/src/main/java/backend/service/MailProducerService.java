package backend.service;

import backend.dto.EmailTask;
import backend.exception.MailSendingException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class MailProducerService {

    private final KafkaTemplate<String, EmailTask> kafkaTemplate;

    @Async
    public void sendWelcomeMail(String email) {
        try {
            kafkaTemplate.send("EMAIL_SENDING_TASKS", new EmailTask(email, "Welcome", "<h1>Welcome!!!</h1>"))
                            .whenComplete((emailTask, throwable) -> {
                                if (throwable != null) {
                                    log.error("Failed to send welcome email to Kafka for: {}", email, throwable);
                                } else {
                                    log.info("Welcome email task sent successfully for: {}", email);
                                }
                            });
            log.info("Welcome email task sent to Kafka for: {}", email);
        } catch (Exception e) {
            log.error("Failed to send message to Kafka for email: {}", email, e);
            throw new MailSendingException("Email service is temporarily unavailable. Please try again later.", e);

        }
    }
}
