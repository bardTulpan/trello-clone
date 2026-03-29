package scheduler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import scheduler.config.SchedulerProperties;
import scheduler.dto.EmailTask;
import scheduler.dto.UserTaskSummaryDto;
import scheduler.repository.MainRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class SchedulerService {

    private final MainRepository mainRepository;
    private final MessageService messageService;
    private final KafkaTemplate<String, EmailTask> kafkaTemplate;
    private final SchedulerProperties schedulerProperties;

    @Scheduled(cron = "${scheduler.cron-expression}")
    public void processDailyTasks() {

        List<UserTaskSummaryDto> tasks = mainRepository.getTasks();

        for (UserTaskSummaryDto task : tasks) {
            EmailTask emailTask = messageService.createEmailTask(task);
            log.info("Prepared email task: {}", emailTask);

            kafkaTemplate.send(schedulerProperties.getKafkaTopic(), emailTask)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Kafka send FAILED for {}: {}", emailTask.getRecipient(), ex.getMessage(), ex);
                        } else {
                            log.info("Kafka send OK for {} to partition: {}",
                                    emailTask.getRecipient(),
                                    result.getRecordMetadata().partition());
                        }
                    });
        }

        log.info("Completed daily task processing.");
    }
}