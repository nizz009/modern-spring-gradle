package org.nizz.modernspringgradle;


import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;

import java.util.Arrays;

// defines the class as a source of bean definition
// tells Spring that the context has to be made based on the contents in this class
// ensures that @Bean (marks a method as bean producer) method creates a Singleton bean unless the scope is changed explicitly
@Configuration
// the package name where Spring needs to scan for components
// we can mention multiple packages with comma separated list - example: @ComponentScan("org.nizz.package1", "org.nizz.package2")
@ComponentScan("org.nizz.modernspringgradle")
public class Main {
    public static void main(String[] args) {
        // A simple Java application
        HelloWorldMessage helloWorldMessage = new HelloWorldMessage();
        helloWorldMessage.greet();

        // Gets the application context for Spring (space where all the objects reside)
        // @param: The main class from which it needs to get the objects to keep in context
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        System.out.println("\n-------- APPLICATION CONTEXT IS READY --------");
        Greeter greeter = context.getBean(Greeter.class);
        greeter.greetDefaultMessge();
        greeter.greet();

        // Bean Registry
        System.out.println("\n---------- BEAN REGISTRY ----------");
        String[] beanNames = context.getBeanDefinitionNames();
        System.out.println("Registered Bean Names: ");
        Arrays.stream(beanNames).forEach(System.out::println);

        BeanDefinition hwmDefinitions = context.getBeanDefinition("helloWorldMessage");
        System.out.println("\n HelloWorldMessage Bean Definition: " + hwmDefinitions);

        System.out.println("\n All bean definitions: ");
        Arrays.stream(beanNames).forEach(beanName -> System.out.println(beanName + ": " + context.getBeanDefinition(beanName)));

        // Java Config Bean Example
        System.out.println("\n---------- JAVA CONFIG BEAN EXAMPLE ----------");
        System.out.println("Before line 45 : to demonstrate Lazy initialization of hungryGreeterExample bean -----");
        HungryGreeter hungryGreeter = (HungryGreeter) context.getBean("hungryGreeterExample");
        hungryGreeter.greet();
        // Uses the bean of "hungryGreeter" declared below
        // If there was only one bean instance of HungryGreeter class, then we would not need to overwrite the
        // default bean name, since Spring would search for the bean with the specific type first
        // Since 2 bean instances of the same type are found, it searches for the bean with the same name that we
        // mention in the Greeter class
        greeter.greetHungryGreeter();

        // The bean below was marked as Lazy in the class - hence the print statement is called before the constructor
        // of the class
        System.out.println("Before line 57 : to demonstrate Lazy initialization of stringGreeter bean -----");
        StringGreeter stringGreeter = (StringGreeter) context.getBean("stringGreeter");
        stringGreeter.greet();

        StringGreeter getStringGreeter = (StringGreeter) context.getBean("getStringGreeter");
        getStringGreeter.greet();

        // Main Scopes (Singleton and Prototype)
        System.out.println("\n---------- SCOPES : SINGLETON AND PROTOTYPE ----------");
        SingletonGreeter singletonGreeter1 = context.getBean(SingletonGreeter.class);
        singletonGreeter1.setMessage("This message is for singleGreeter1");
        SingletonGreeter singletonGreeter2 = context.getBean(SingletonGreeter.class);
        singletonGreeter2.setMessage("This message is for singleGreeter2");
        // Since SingletonGreeter has a singleton scope, the same instance is returned for both the objects above
        // Hence, singletonGreeter1 and singletonGreeter2 point to same instance and their values are updated together

        System.out.println("Message from singletonGreeter1: " + singletonGreeter1.getMessage());
        System.out.println("Message from singletonGreeter2: " + singletonGreeter2.getMessage());

        PrototypeGreeter prototypeGreeter1 = context.getBean(PrototypeGreeter.class);
        prototypeGreeter1.setMessage("This message is for prototypeGreeter1");
        PrototypeGreeter prototypeGreeter2 = context.getBean(PrototypeGreeter.class);
        prototypeGreeter2.setMessage("This message is for prototypeGreeter2");
        // Since prototypeGreeter has a prototype scope, different instances are made for both the objects above
        // Hence, prototypeGreeter1 and prototypeGreeter2 point to different instance and their values do not affect
        // the other's values

        System.out.println("Message from prototypeGreeter1: " + prototypeGreeter1.getMessage());
        System.out.println("Message from prototypeGreeter2: " + prototypeGreeter2.getMessage());

        // Updating Order of Beans
        System.out.println("\n---------- AUTOWIRING EXAMPLES ----------");
        NotificationService notificationService = context.getBean(NotificationService.class);
        notificationService.printMessage();

        // Updating Order of Beans
        System.out.println("\n---------- ORDERING OF SPRING BEANS ----------");
        OrderTest orderTest = context.getBean(OrderTest.class);
        System.out.println("Without @Order (Single instances) ----------");
        orderTest.taskA.run();
        orderTest.taskB.run();
        System.out.println("With @Order (Collection of bean instances) ----------");
        OrderTestCollection orderTestCollection = context.getBean(OrderTestCollection.class);
        orderTestCollection.tasks.forEach(Task::run);

        // Life Cycle of Beans
        System.out.println("\n---------- LIFE CYCLE OF SPRING BEANS ----------");
        // registers a shutdown hook which runs and closes the context once the program ends : specialized feature
        // provided by the Application Context which is not present in the Bean Factory
        // This is essential to make Spring run the @PreDestroy methods
        context.registerShutdownHook();
        LifeCycleGreeter lifeCycleGreeter = context.getBean(LifeCycleGreeter.class);
        lifeCycleGreeter.greet();
    }

    // Example of Java Config Bean
    // The name of the method is the same as the bean name
    @Bean
    // This bean is not instantiated until the code on line 45 is executed (to get the bean from context)
    @Lazy
    public HungryGreeter hungryGreeterExample() {
        System.out.println("[hungryGreeterExample] Method called -----");
        return new HungryGreeter();
    }

    // We can override the default name of the bean (and store it as something else other than the method name)
    @Bean(name="hungryGreeter")
    public HungryGreeter hungryGreeterExample2() {
        return new HungryGreeter();
    }

    // @Bean creates a bean of any return type
    // This bean is used in StringGreeter class - the method/bean name does not need to be the same as "message"
    // because it is the only bean with the String type
    @Bean
    public String getMessage() {
        return "hello from getMessage";
    }

    // We can provide the String bean from above as a parameter to the new bean of type getStringGreeter
    // If there are multiple beans of type String, then the name of the parameter should match the name of the bean
    // that we want to use
    @Bean
    public StringGreeter getStringGreeter(String getMessage) {
        return new StringGreeter(getMessage);
    }
}