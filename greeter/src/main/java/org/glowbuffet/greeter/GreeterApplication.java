package org.glowbuffet.greeter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class GreeterApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreeterApplication.class, args);
    }

}
