package org.nizz.modernspringgradle;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("sms")
public class SmsService implements MessageService {
    @Override
    public void sendMessage() {
        System.out.println("Message from SmsService");
    }
}
