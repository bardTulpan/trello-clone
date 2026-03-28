package scheduler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scheduler.config.SchedulerProperties;
import scheduler.dto.EmailTask;
import scheduler.dto.UserTaskSummaryDto;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final SchedulerProperties schedulerProperties;

    public EmailTask createEmailTask(UserTaskSummaryDto task) {
        StringBuilder body = new StringBuilder();
        int maxTitles = schedulerProperties.getMaxTitlesInEmail();

        EmailTask emailTask = new EmailTask();
        emailTask.setRecipient(task.getEmail());
        emailTask.setTitle(defineTitle(task));

        if (task.getFinishedCount() > 0) {
            body.append("Tasks completed today: ")
                    .append(task.getFinishedCount())
                    .append("\n");

            task.getFinishedTitles().stream()
                    .limit(maxTitles)
                    .forEach(title -> body.append("- ").append(title).append("\n"));

            body.append("\n");
        }

        if (task.getUnfinishedCount() > 0) {
            body.append("Pending tasks remaining: ")
                    .append(task.getUnfinishedCount())
                    .append("\n");

            task.getUnfinishedTitles().stream()
                    .limit(maxTitles)
                    .forEach(title -> body.append("- ").append(title).append("\n"));
        }

        emailTask.setBody(body.toString());

        return emailTask;
    }

    private String defineTitle(UserTaskSummaryDto task) {
        if (task.getFinishedCount() > 0 && task.getUnfinishedCount() > 0) {
            return "Daily Task Summary";
        } else if (task.getFinishedCount() > 0) {
            return "Tasks Completed Today";
        } else if (task.getUnfinishedCount() > 0) {
            return "Pending Tasks Notification";
        } else {
            return "Daily Task Summary";
        }
    }
}