package com.wookjae.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SchedulerAdvancedApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulerAdvancedApplication.class, args);
    }

}
