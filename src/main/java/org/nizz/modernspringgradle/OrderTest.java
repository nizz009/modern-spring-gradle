package org.nizz.modernspringgradle;

import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

// NOTE: The components are marked as Lazy so that the logs will be printed only when
// we require in the Main method - to make it easy to demonstrate the ordering of the logs

@Component
@Lazy
public class OrderTest {
    Task taskA;
    Task taskB;

    public OrderTest(Task taskA, Task taskB) {
        this.taskA = taskA;
        this.taskB = taskB;
    }
}

@Component
@Lazy
class OrderTestCollection {
    List<Task> tasks;

    public OrderTestCollection(List<Task> tasks) {
        this.tasks = tasks;
    }
}

interface Task {
    void run();
}

@Component
@Lazy
@DependsOn("taskB")
@Order(2)
class TaskA implements Task {
    public TaskA() {
        System.out.println("Inside Task A constructor");
    }

    @Override
    public void run() {
        System.out.println("Running Task A");
    }
}

@Component
@Lazy
@Order(1)
class TaskB implements Task {
    public TaskB() {
        System.out.println("Inside Task B constructor");
    }

    @Override
    public void run() {
        System.out.println("Running Task B");
    }
}
