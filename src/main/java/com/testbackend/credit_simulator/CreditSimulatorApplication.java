package com.testbackend.credit_simulator;

import com.testbackend.credit_simulator.controller.CreditVehicleController;
import com.testbackend.credit_simulator.view.ConsoleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CreditSimulatorApplication  {

    public static void main(String[] args) {
        SpringApplication.run(CreditSimulatorApplication.class, args);
    }

    private ConsoleView consoleView;

    @Autowired
    public CreditSimulatorApplication(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            CreditVehicleController controller = new CreditVehicleController(consoleView);
            controller.startSimulation();
        };
    }
}
