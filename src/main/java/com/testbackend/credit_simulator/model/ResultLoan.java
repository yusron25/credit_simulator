package com.testbackend.credit_simulator.model;

import lombok.Data;

@Data
public class ResultLoan {

    private Double pokokPinjaman;
    private Double rate;
    private Double totalPinjaman;
    private Double installmentMonthly;
    private Double installmentYearly;

}
