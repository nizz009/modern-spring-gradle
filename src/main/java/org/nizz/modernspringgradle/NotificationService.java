package org.nizz.modernspringgradle;

import org.springframework.stereotype.Component;


@Component
public class NotificationService {
    MessageService messageService;

    // Spring will inject emailService bean until and unless we use @Qualifier since it is the primary bean for MessageService type now
    public NotificationService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void printMessage() {
        messageService.sendMessage();
    }
}
