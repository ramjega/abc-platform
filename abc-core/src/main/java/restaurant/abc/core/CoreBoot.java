package restaurant.abc.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoreBoot {

    public static void main(String[] args) {
        SpringApplication.run(CoreBoot.class, args);
        System.out.println("ABC core has started successfully!");
    }
}
