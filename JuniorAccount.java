import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class JuniorAccount extends Account {
    private double dayod = 100.0;
    public JuniorAccount(Customer owner, double cash, int n){
        super(owner, cash, n);
        this.dayod = 100.0;

    }

    @Override
    public void transAmount(double amount, String toAccNo, CustomerList customerList) {
        Account pre = new Account();
        for(Customer c:customerList.getCustomers()){
            for(Account now:c.getAccounts()){
                String AccNo = now.getAccNo();
                if(toAccNo.equals(AccNo)){
                    pre = now;
                }
            }
        }
        if(super.getSuspend() == false && pre.getSuspend() == false) {
            if ((super.getBalance() - amount) >= 0 && amount <= this.dayod) {
                setNoin(getNoin()-amount);
                this.dayod -= amount;
                pre.setNoin(pre.getNoin()+amount);
                depositHistory.add(new Deposit(-amount));
                pre.depositHistory.add(new Deposit(amount));
                System.out.println("A successful transfer!\n");
                System.out.println("you can still withdraw " + this.dayod + " yuan today.\n");
            }
            else {
                System.out.println("Exceed of overdraft amount£¬transaction deny£¡\n");
                
            }
        }
        else{
            System.out.println("One account has been suspended, transaction deny\n");
        }
    }

    @Override
    public void withdraw(double amount) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDate = now.format(formatter);
        if(super.getSuspend() == false) {
            if ((super.getBalance() - amount) >= 0 && amount <= this.dayod) {
                setBalance(getBalance() - amount);
                withdrawalHistory.add(new Withdrawal(formattedDate, amount));
                this.dayod -= amount;
                System.out.println("A successful withdraw!" );
                System.out.println("you can still withdraw " + this.dayod + " yuan today.\n");
            }
            else {
                
                    System.out.println("Exceed of overdraft amount£¬transaction deny£¡\n");
            }
            
        }
        else{
            System.out.println("your account has been suspended, transaction deny\n");
        }
    }

    @Override
    public void clear_fund() {
        this.dayod = 100.0;
        LocalDate today = LocalDate.now();
        int days = this.getDay(getPast());
        if(days<0){
            setSus(); 
        }
        if(getSuspend() == false) {
            setBalance(getBalance()+getNoin());
            clearFundHistory.add(new ClearFund(getNoin()));
            setNoin(0);
            System.out.println("DEPOSIT finish, balance:" + getBalance() + " yuan\n");
            if(getBalance()>=0){
                setDate(today);
            }
            if(getBalance()< 0 ){
                setDatef(today); 
                System.out.println("Please pay off the debt within "+ days +" days, otherwise your account will be suspended");
            }
        }
        else{
            System.out.println("your account has been suspended, transaction deny\n");
        }
    }

    public double getDayOd(){
        return this.dayod;
    }
}
