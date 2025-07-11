package org.nizz.modernspringgradle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
// Marked it lazy so that it will only be constructed only when we call it in the
// Main method so that we can demonstrate the order of the log printing appropriately
// under the Life Cycle Section
@Lazy
public class LifeCycleGreeter {
    public LifeCycleGreeter() {
        System.out.println("LifeCycleGreeter Constructor called -----");
    }

    @PostConstruct
    public void lifeCyclePostConstruct() {
        System.out.println("PostConstruct method executed for LifeCycleGreeter -----");
    }

    @PreDestroy
    public void lifeCyclePreDestroy() {
        System.out.println("PreDestroy method executed for LifeCycleGreeter -----");
    }

    public void greet() {
        System.out.println("Greeting from LifeCycleGreeter -----");
    }
}
