package emailSender.service;

import emailSender.dto.EmailTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmailConsumerService {

    private final EmailSenderService emailSenderService;

    @KafkaListener(topics = "${kafka.consumer.topic}", groupId = "${kafka.consumer.group-id}")
    public void processEmail(ConsumerRecord<String, EmailTask> record) {
        EmailTask task = record.value();
        log.debug("Received task from Kafka: {}", task);
        emailSenderService.sendTask(task);
        log.info("Finished processEmail");
    }
}