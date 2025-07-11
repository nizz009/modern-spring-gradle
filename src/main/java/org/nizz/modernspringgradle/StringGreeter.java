package org.nizz.modernspringgradle;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy // The bean with the name "stringGreeter" is instantiated lazily
public class StringGreeter implements BeanNameAware {
    String message;
    String beanName;

    StringGreeter(String message) {
        System.out.println("StringGreeter constructor for bean");
        this.message = message;
    }

    public void greet() {
        System.out.println("Greeting from StringGreeter with message: " + message + " with bean name: " + beanName);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
