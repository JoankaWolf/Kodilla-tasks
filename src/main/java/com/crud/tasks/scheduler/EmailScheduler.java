package com.crud.tasks.scheduler;

import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.trello.config.AdminConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailScheduler {
    private final SimpleEmailService simpleEmailService;
    private final TaskRepository taskRepository;
    private final AdminConfig adminConfig;
    private static final String SUBJECT = "Tasks: Once a day email";

//    @Scheduled(fixedDelay = 10000)
    @Scheduled(cron = "0 0 10 * * *")
    public void sendInformationEmail() {
        long size = taskRepository.count();
        if(taskRepository.count() == 1) {
            simpleEmailService.sendDaily(
                    new Mail(
                            adminConfig.getAdminMail(),
                            SUBJECT,
                            "Currently in database you got: " + size + " task",
                            null
                    )
            );
        } else {
            simpleEmailService.sendDaily(
                    new Mail(
                            adminConfig.getAdminMail(),
                            SUBJECT,
                            "Currently in database you got: " + size + " tasks",
                            null
                    )
            );
        }

    }
}
