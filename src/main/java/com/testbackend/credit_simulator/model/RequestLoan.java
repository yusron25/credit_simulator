package com.testbackend.credit_simulator.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RequestLoan {

    private String vehicleType;
    private String vehicleStatus;
    private Integer dp;
    private Integer totalLoan;
    private Integer tenor;
    private List<ResultLoan> resultLoans;
}
