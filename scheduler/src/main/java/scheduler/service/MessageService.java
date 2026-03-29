package scheduler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scheduler.config.NotificationProperties;
import scheduler.config.SchedulerProperties;
import scheduler.dto.EmailTask;
import scheduler.dto.UserTaskSummaryDto;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final SchedulerProperties schedulerProperties;
    private final NotificationProperties notificationProperties;

    public EmailTask createEmailTask(UserTaskSummaryDto task) {
        EmailTask emailTask = new EmailTask();
        emailTask.setRecipient(task.getEmail());
        emailTask.setTitle(selectTitle(task));
        emailTask.setBody(buildBody(task));

        return emailTask;
    }

    private String selectTitle(UserTaskSummaryDto task) {
        if (task.getFinishedCount() > 0 && task.getUnfinishedCount() > 0) {
            return notificationProperties.getTitleMixed();
        } else if (task.getFinishedCount() > 0) {
            return notificationProperties.getTitleFinished();
        } else if (task.getUnfinishedCount() > 0) {
            return notificationProperties.getTitleUnfinished();
        }

        return notificationProperties.getTitleMixed();
    }

    private String buildBody(UserTaskSummaryDto task) {
        StringBuilder body = new StringBuilder();
        int maxTitles = schedulerProperties.getMaxTitlesInEmail();

        if (task.getFinishedCount() > 0) {
            body.append(notificationProperties.getFinishedHeader())
                    .append(" ")
                    .append(task.getFinishedCount())
                    .append("\n");

            task.getFinishedTitles().stream()
                    .limit(maxTitles)
                    .forEach(title -> body.append(notificationProperties.getBullet())
                            .append(title)
                            .append("\n"));

            body.append("\n");
        }

        if (task.getUnfinishedCount() > 0) {
            body.append(notificationProperties.getUnfinishedHeader())
                    .append(" ")
                    .append(task.getUnfinishedCount())
                    .append("\n");

            task.getUnfinishedTitles().stream()
                    .limit(maxTitles)
                    .forEach(title -> body.append(notificationProperties.getBullet())
                            .append(title)
                            .append("\n"));
        }
        return body.toString();
    }
}