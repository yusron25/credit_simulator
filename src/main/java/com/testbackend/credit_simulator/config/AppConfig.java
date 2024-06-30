package com.testbackend.credit_simulator.config;

import com.testbackend.credit_simulator.view.ConsoleView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class AppConfig {

    @Bean
    public ConsoleView consoleView() {
        return new ConsoleView();
    }
}