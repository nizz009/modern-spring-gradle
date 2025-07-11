package org.nizz.modernspringgradle;

import org.springframework.stereotype.Component;

@Component
public class HelloWorldMessage {
    public void greet() {
        System.out.println("Hello, World!");
    }
}
