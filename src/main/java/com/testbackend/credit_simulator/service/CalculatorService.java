package com.testbackend.credit_simulator.service;

import com.testbackend.credit_simulator.model.RequestLoan;
import com.testbackend.credit_simulator.model.ResultLoan;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CalculatorService {

    public CalculatorService(Integer totalLoan, Integer dp, Double rate, Integer currentTenor) {
        this.pokokPinjaman= Double.valueOf(totalLoan-dp);
        this.rate=rate;
        this.tenor=currentTenor;
    }

    private Double pokokPinjaman;
    private Double rate;
    private Integer tenor;

    public ResultLoan calc(){
        ResultLoan resultLoan = new ResultLoan();
        resultLoan.setPokokPinjaman(getPokokPinjaman());
        resultLoan.setRate(getRate());
        resultLoan.setTotalPinjaman(resultLoan.getPokokPinjaman()+(resultLoan.getPokokPinjaman()*resultLoan.getRate()/100));
        resultLoan.setInstallmentMonthly(resultLoan.getTotalPinjaman()/(12*tenor));
        resultLoan.setInstallmentYearly(resultLoan.getInstallmentMonthly()*12);
        return resultLoan;
    }
}
