package org.nizz.modernspringgradle;

import org.springframework.beans.factory.BeanNameAware;

public class HungryGreeter implements BeanNameAware {
    String beanName;

    public void greet() {
        System.out.println("Hello, I am hungry : from bean name: " + beanName);
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
}
