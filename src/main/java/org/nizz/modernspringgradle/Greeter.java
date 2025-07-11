package org.nizz.modernspringgradle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Greeter {
    HelloWorldMessage helloWorldMessage;
    HungryGreeter hungryGreeter;

    Greeter() {
        // This constructor is called after the context is ready
        System.out.println("Inside Greeter class's constructor");
    }

    // CONSTRUCTOR INJECTION
    // Spring automatically injects the helloWorldMessage bean from its container
    // This works if all the parameters (here, HelloWorldMessage) are also marked as a component since Spring needs the
    // beans to be present in the registry for them to be injected
    // If there exists only a single constructor, we do not need to add @Autowired
    // However, since there are two constructors present here, we need to mark it as @Autowired
    // NOTE: We cannot add @Autowired to multiple constructors in the same class since that creates ambiguity as well
    @Autowired
    Greeter(HelloWorldMessage helloWorldMessage) {
        // This constructor is called before the context is ready (for bean initialization)
        System.out.println("Inside Greeter class's constructor with parameter");
        this.helloWorldMessage = helloWorldMessage;
    }

    public HelloWorldMessage getHelloWorldMessage() {
        return helloWorldMessage;
    }

    // SETTER INJECTION
    @Autowired
    public void setHelloWorldMessage(HelloWorldMessage helloWorldMessage) {
        this.helloWorldMessage = helloWorldMessage;
    }

    public HungryGreeter getHungryGreeter() {
        return hungryGreeter;
    }

    @Autowired
    public void setHungryGreeter(HungryGreeter hungryGreeter) {
        this.hungryGreeter = hungryGreeter;
    }

    public void greet() {
        helloWorldMessage.greet();
    }

    public void greetDefaultMessge() {
        System.out.println("Hello from Greeter class");
    }

    public void greetHungryGreeter() {
        System.out.println("From Greeter class -----");
        hungryGreeter.greet();
    }
}
