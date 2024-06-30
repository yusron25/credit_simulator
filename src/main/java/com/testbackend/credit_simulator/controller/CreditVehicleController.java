package com.testbackend.credit_simulator.controller;

import com.testbackend.credit_simulator.model.RequestLoan;
import com.testbackend.credit_simulator.model.ResultLoan;
import com.testbackend.credit_simulator.service.CalculatorService;
import com.testbackend.credit_simulator.view.ConsoleView;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

public class CreditVehicleController {

    private List<RequestLoan> requestLoans = new ArrayList<>();

    private ConsoleView view;

    public CreditVehicleController(ConsoleView view) {
        this.view = view;
    }

    public CreditVehicleController() {
        this.view = view;
    }

    private Double carBaseInterest= 8.0;
    private Double motorBaseInterest= 9.0;

    @Value("${using-file}")
    private String USING_FILE;

    @Value("${vehicle.type}")
    private String VEHICLE_TYPE;
    @Value("${vehicle.status}")
    private String VEHICLE_STATUS;
    @Value("${vehicle.price}")
    private Integer PRICE;
    @Value("${vehicle.dp}")
    private Integer DP;
    @Value("${vehicle.tenor}")
    private Integer TENOR;

    public Boolean startSimulation() {
        Integer menu ;
        if (USING_FILE!=null && USING_FILE.equalsIgnoreCase("true")){
            menu = 1;
        }else {
            menu = view.displayWelcomeMessage();
        }
        switch (menu){
            case 1:
                if (USING_FILE!=null && USING_FILE.equalsIgnoreCase("true")){
                    RequestLoan requestLoan = RequestLoan.builder()
                            .vehicleType(VEHICLE_TYPE)
                            .vehicleStatus(VEHICLE_STATUS)
                            .totalLoan(PRICE)
                            .dp(DP)
                            .tenor(TENOR)
                            .build();
                    requestLoan.setResultLoans(simulateCredit(requestLoan));
                    showFullData(requestLoan);
                }else {
                    RequestLoan requestLoan = view.displayVehicleDetailsPrompt();
                    requestLoan.setResultLoans(simulateCredit(requestLoan));
                    showFullData(requestLoan);
                    if (view.displaySaveCalculation()){
                        requestLoans.add(requestLoan);
                    }
                }
                this.startSimulation();
                break;
            case 2:
                Integer loadData = view.displayLoadData(this.requestLoans);
                if (loadData!=0){
                    showFullData(requestLoans.get(loadData-1));
                }
                this.startSimulation();
                break;
            case 3:
                return true;
            default:
                return true;
        }
        return true;
    }

    public List<ResultLoan> simulateCredit(RequestLoan requestLoan) {
        // Placeholder for credit simulation logic
        System.out.println("Credit simulation result:");

        Double baseInterest = requestLoan.getVehicleType().equalsIgnoreCase("car")?carBaseInterest:motorBaseInterest;

        Double valDefaultInterest = 0.1;
        Double valSecondInterest = 0.5;

        List<ResultLoan> resultLoan = new ArrayList<>();

        int currentInterest = 0;
        Double currentRate =baseInterest;
        for (int iTenor = 0;iTenor<requestLoan.getTenor();iTenor++) {
            switch (currentInterest){
                case 1:
                    currentRate = currentRate+valDefaultInterest;
                    currentInterest++;
                    break;
                case 2:
                    currentRate = currentRate+valSecondInterest;
                    currentInterest=1;
                    break;
                default:
                    currentInterest++;

            }

            Integer totalLoan = requestLoan.getTotalLoan();
            if (resultLoan.size()>0){
                ResultLoan last=resultLoan.get(resultLoan.size()-1);
                totalLoan = last.getTotalPinjaman().intValue()-last.getInstallmentYearly().intValue()+requestLoan.getDp();
            }
            ResultLoan calculatorService = new CalculatorService(totalLoan,requestLoan.getDp(),currentRate,requestLoan.getTenor()-iTenor).calc();

            resultLoan.add(calculatorService);
        }

        return resultLoan;
    }

    private void showFullData(RequestLoan requestLoan){
        System.out.println("Total Price : "+requestLoan.getTotalLoan());
        System.out.println("DP : "+requestLoan.getDp());
        System.out.println("Total Loan : "+(requestLoan.getTotalLoan()-requestLoan.getDp()));
        System.out.println("Tenor : "+requestLoan.getTenor());
        for (int i=0;i<requestLoan.getResultLoans().size();i++){

            System.out.println("tahun "+(i+1)+" : Rp. "+requestLoan.getResultLoans().get(i).getInstallmentMonthly().intValue()+"/bln , Suku Bunga : "+requestLoan.getResultLoans().get(i).getRate() +"% \n");
        }
    }
}