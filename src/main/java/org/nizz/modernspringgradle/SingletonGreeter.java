package org.nizz.modernspringgradle;

import org.springframework.stereotype.Component;

@Component
public class SingletonGreeter {
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
