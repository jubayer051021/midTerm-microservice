package com.BjitMidTerm.Bookservice;

import com.BjitMidTerm.Bookservice.config.MigrationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookServiceApplication implements CommandLineRunner {
    @Autowired
    private MigrationConfiguration migrationConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(BookServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Migration Configuration : "+migrationConfiguration.getMigration());
    }
}
