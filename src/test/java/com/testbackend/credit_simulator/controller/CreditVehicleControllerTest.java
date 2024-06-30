package com.testbackend.credit_simulator.controller;

import com.testbackend.credit_simulator.CreditSimulatorApplication;
import com.testbackend.credit_simulator.model.RequestLoan;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestComponent;

import static org.junit.jupiter.api.Assertions.*;
@TestComponent
class CreditVehicleControllerTest {
    @Test
    public void testCase1(){
        RequestLoan requestLoan = RequestLoan.builder()
                .vehicleType("car")
                .vehicleStatus("used")
                .totalLoan(100000000)
                .dp(25000000)
                .tenor(3)
                .build();
        CreditVehicleController creditVehicleController = new CreditVehicleController();

        requestLoan.setResultLoans(creditVehicleController.simulateCredit(requestLoan));

        Assertions.assertThat(requestLoan).isNotNull();
        Assertions.assertThat(requestLoan.getResultLoans().get(0).getInstallmentMonthly()).isEqualTo(2250000);
    }
}