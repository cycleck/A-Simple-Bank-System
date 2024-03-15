import java.sql.Blob;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.text.html.parser.Parser;

public class Account {
    private String accNo;
    private String accPin;

    private double balance;
    private double not_in;
    private boolean suspend = false;
    private LocalDate dates;
    private LocalDate dates_f; 
    private Customer Owner;

    protected ArrayList<Withdrawal> withdrawalHistory = new ArrayList<Withdrawal>();
    protected ArrayList<Deposit> depositHistory = new ArrayList<Deposit>();
    protected ArrayList<ClearFund> clearFundHistory = new ArrayList<ClearFund>();

    protected double un_cleared_balance = 0.0;

    public Account() {
        
    }

    public Account(Customer owner,double cash,int n){
        String hash = PinProducer.producePin();

        switch(n){
            case 1:
                hash += "01";
                break;
            case 2:
                hash += "10";
                break;
            case 3:
                hash += "11";
                break;
            default:
                break;
        }
        this.accNo = hash;
       
        this.accPin = PinProducer.producePin();
        this.balance = cash;
        this.suspend = suspend;
        this.not_in = 0.0;
        this.dates = LocalDate.now();
        this.dates_f = LocalDate.now();
        this.Owner = owner;

    }

    public LocalDate getPast(){
        return this.dates;
    }

    public LocalDate getPastFull(){
        return this.dates_f;
    }

    public int getDay(LocalDate past){
        LocalDate now = LocalDate.now();
        return 30-Period.between(past,now).getDays();
    }
    
    public String getAccNo(){
        return this.accNo;
    }

    public String getPin(){
        return this.accPin;
    }

    public double getBalance() {
        return this.balance;
    }

    public boolean getSuspend(){
        return this.suspend;
    }

    public double getNoin(){
        return this.not_in;
    }

    public void setDate(LocalDate now){
        this.dates = now;
    }

    public void setDatef(LocalDate now){
        this.dates_f = now;
    }

    public void setBalance(double amount){
        this.balance = amount;
    }

    public void setNoin(double amount){
        this.not_in = amount;
    }

    public void deposit(double amount) {
        if(this.suspend == false) {
            this.balance += amount;
            depositHistory.add(new Deposit(amount));
            System.out.println("DEPOSIT finish, balance:" + getBalance() + " yuan\n");
            if(this.balance>=0){
                this.dates=LocalDate.now();
            }
        }
        else{
            System.out.println("your account has been suspended, transaction deny\n");
        }
    }

    public void transAmount(double depositAmount1, String toAccNo,CustomerList customList) {

    }

    public void withdraw(double amount) {
    }

    public void clear_fund() {
        LocalDate today = LocalDate.now();
        int days = this.getDay(dates);
        if(days<0){
            this.suspend = true; 
        }
        if(this.suspend == false) {
            this.balance += this.not_in;
            clearFundHistory.add(new ClearFund(this.not_in));
            this.not_in = 0;
            System.out.println("DEPOSIT finish, balance:" + getBalance() + " yuan\n");
            if(this.balance>=0){
                this.dates = today;
            }
            if(this.balance < 0 ){
                this.dates_f = today;
                System.out.println("Please pay off the debt within "+ days +" days, otherwise your account will be suspended");
            }
        }
        else{
            System.out.println("your account has been suspended, transaction deny\n");
        }
    }

    public void changeSus(Scanner n){
        System.out.println("-------------------------------------------------------------------");
        System.out.println("ATTENTION:  ");
        System.out.println("Your account will be suspended for trading once it becomes a suspect account");
        System.out.println("If you need to restore your account, ");
        System.out.println("please go to the offline office to handle it. ");
        System.out.println("Enter your Pin to Confirm: ");
        String Pin = n.nextLine();
        if(Pin.equals(this.accPin)){
            this.suspend = true;
        }else{
            System.out.println("Wrong Pin!");
        }

    }

    
    public void setSus(){
        this.suspend = true;
    }
    
    
    protected class Withdrawal {
        private String date;
        private double amount;

        public Withdrawal(String date, double amount) {
            this.date = date;
            this.amount = amount;
        }

        public String getDate() {
            return date;
        }

        public double getAmount() {
            return amount;
        }
    }

    protected class Deposit {
        private LocalDate date;
        private double amount;

        public Deposit(double amount) {
            this.date = LocalDate.now();
            this.amount = amount;
       
        }

        public LocalDate getDate() {
            return date;
        }

        public double getAmount() {
            return amount;
        }
    }

    protected class ClearFund {
        private LocalDate date;
        private double amount;

        public ClearFund(double amount) {
            this.date = LocalDate.now();
            this.amount = amount;

        }

        public LocalDate getDate() {
            return date;
        }

        public double getAmount() {
            return amount;
        }
    }


    public void viewWithdrawalHistory() {
        if (withdrawalHistory.size() == 0) {
            System.out.println("No withdrawals have been made from this account.");
        } else {
            System.out.println("Withdrawal History for Account " + getAccNo() + ":");
            for (Withdrawal withdrawal : withdrawalHistory) {
                System.out.println("Date: " + withdrawal.getDate() + ", Amount: " + withdrawal.getAmount());
            }
        }
    }

    //View the Deposit operation history of the account
    public void viewDepositHistory() {
        if (depositHistory.size() == 0) {
            System.out.println("No deposits have been made from this account.");
        } else {
            System.out.println("Deposit History for Account " + getAccNo() + ":");
            for (Deposit deposit : depositHistory) {
                System.out.println("Date: " + deposit.getDate() + ", Amount: " + deposit.getAmount());
            }
        }
    }

    //View the Clear Funds operation history of the account
    public void viewClearFundsHistory() {
        if (depositHistory.size() == 0) {
            System.out.println("No clear funds action have been made from this account.");
        } else {
            System.out.println("Clear funds History for Account " + getAccNo() + ":");
            for (ClearFund clearFund : clearFundHistory) {
                System.out.println("Date: " + clearFund.getDate() + ", Amount: " + clearFund.getAmount());
            }
        }
    }

    public void close(Scanner n) {
        System.out.println("-------------------------------------------------------------------");
        if(this.getBalance() == 0.0){
            System.out.println("This account can be closed");
            System.out.println("Please input your Pin to confirm: ");
            String str = n.nextLine();
            if(this.accPin.equals(str)){
                this.Owner.getAccounts().remove(this);
                System.out.println("This account has been closed");
                return;
            }
            else{
                System.out.println("Wrong Pin!");
                return;
            }
        }else{
            System.out.println("The account has outstanding payments and cannot be closed.");
            return;
        }
        
    }

   

   

}