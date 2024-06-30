package com.testbackend.credit_simulator.view;


import com.testbackend.credit_simulator.model.RequestLoan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleView {
    private Scanner scanner;

    public ConsoleView(){
        this.scanner = new Scanner(System.in);
    }

    public Integer displayWelcomeMessage() {

        System.out.println("Welcome to Credit Vehicle Simulator!");
        System.out.println("1. New Calculation");
        System.out.println("2. Load Calculation");
        System.out.println("3. Exit");
        System.out.println("Choose menu : ");
        Integer menu =  scanner.nextInt();
        return menu;
    }
    public Integer displayLoadData(List<RequestLoan> requestLoanList) {

        if (requestLoanList.size()>0){
            System.out.println("Data List : ");
            for (int i=0;i<requestLoanList.size();i++){
                RequestLoan requestLoan = requestLoanList.get(i);
                System.out.println((i+1)+". "+requestLoan.getVehicleStatus()+" "+requestLoan.getVehicleType()+" with total price "+requestLoan.getTotalLoan()+" and tenor "+requestLoan.getTenor()+" year(s)");
            }
            System.out.println("Choose Data number : ");
            return scanner.nextInt();
        }else {
            System.out.println("-- NO DATA --");
            System.out.println("0. back");

            Integer data = scanner.nextInt();
            return data;
        }

    }

    public RequestLoan displayVehicleDetailsPrompt() {
        System.out.println("Enter vehicle details:");
        String type = vehicleType();
        String status = vehicleStatus();
        Integer year = vehicleYear(status);
        Integer totalLoan = totalLoan();
        Integer tenor = tenor();
        Integer dp = dp(status,totalLoan);
        scanner.nextLine();

        return RequestLoan.builder()
                .vehicleType(type)
                .vehicleStatus(status)
                .totalLoan(totalLoan)
                .dp(dp)
                .tenor(tenor)
                .build();
    }

    public Boolean displaySaveCalculation(){
        System.out.println("You want to save this calculation (Yes/No) : ");

        String isSave = scanner.nextLine();

        if (isSave.equalsIgnoreCase("yes")){
            return true;
        }
        return false;
    }



    private String vehicleType(){
        System.out.print("Vehicle Type (Car or Motorcycle) : ");
        String type = scanner.nextLine();
        if (type==null|| (!type.equalsIgnoreCase("Car") && !type.equalsIgnoreCase("Motorcycle")) ){
            System.out.print("Invalid Vehicle Type \n");
            return this.vehicleType();
        }
        return type;
    }
    private String vehicleStatus(){
        System.out.print("Vehicle Status (New or Used) : ");
        String status = scanner.nextLine();
        if (status==null|| (!status.equalsIgnoreCase("New") && !status.equalsIgnoreCase("Used")) ){
            System.out.print("Invalid Vehicle Status \n");
            return this.vehicleStatus();
        }
        return status;
    }
    private Integer vehicleYear(String status){
        System.out.print("Year (4 digits) :  ");
        Integer year = scanner.nextInt();

        if (year!=null&&status.equalsIgnoreCase("new")){
            if (year >= LocalDateTime.now().getYear()-1 && year <= LocalDateTime.now().getYear()){
                return year;
            }else {
                System.out.print("Invalid Year, New Vehicle only in "+(LocalDateTime.now().getYear()-1)+" until "+LocalDateTime.now().getYear() +" \n");
                return this.vehicleYear(status);
            }
        }else if (year!=null&&status.equalsIgnoreCase("used")){
            if (year < (LocalDateTime.now().getYear()-1) && year > 999){
                return year;
            }else {
                System.out.print("Invalid Year, Used Vehicle only under "+(LocalDateTime.now().getYear()-1)+" \n");
                return this.vehicleYear(status);
            }
        }else {
            System.out.print("Invalid Year \n");
            return this.vehicleYear(status);
        }
    }
    private Integer totalLoan(){
        System.out.print("Total Loan ( less than equal 1 milyar) :  ");
        Integer loan = scanner.nextInt();
        if (loan==null|| loan > 1000000000 ){
            System.out.print("Invalid Loan \n");
            return this.totalLoan();
        }
        return loan;
    }
    private Integer tenor(){
        System.out.print("Tenor ( 1-6 year(s) ):  ");
        Integer tenor = scanner.nextInt();
        if (tenor==null|| tenor > 6 ){
            System.out.print("Invalid Tenor \n");
            return this.tenor();
        }
        return tenor;
    }
    private Integer dp(String status , Integer totalLoan){
        System.out.print("DP :  ");
        Integer dp = scanner.nextInt();

        if (dp!=null && status.equalsIgnoreCase("new") && (dp >= (0.35 * totalLoan) && dp <= totalLoan )){
            return dp;
        }else if (dp!=null && status.equalsIgnoreCase("used") && (dp >= (0.25 * totalLoan) && dp <= totalLoan )){
            return dp;
        }else {
            System.out.print("Invalid DP, min 35% for new vehicle and 25% for used vehicle \n");
            return this.dp(status,totalLoan);
        }
    }

}