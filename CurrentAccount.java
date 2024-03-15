import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentAccount extends Account{
    private double od = 5000.0;
   
    public CurrentAccount(Customer owner, double cash, int n) {
        super(owner, cash, n);
        this.od = 5000.0;
        //TODO Auto-generated constructor stub
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
            if ((super.getBalance() - amount) >= 0) {
                setNoin(getNoin()-amount);
                pre.setNoin(pre.getNoin()+amount);
                depositHistory.add(new Deposit(-amount));
                pre.depositHistory.add(new Deposit(amount));
                System.out.println("A successful transfer!\n");
            }
            else {
                if (amount >= super.getBalance()+this.od+super.getNoin() ) {
                    setNoin(getNoin()-amount); 
                    depositHistory.add(new Deposit(amount));
                    double numnow = super.getBalance()+this.od+super.getNoin();
                    System.out.println("Balance is not enough, you now overdraft your account after clearing funds");
                    System.out.println("You will overdraft " + (5000-numnow) + " yuan after this exchange. ");
                    System.out.println("you can still withdraw " + numnow + " yuan.\n");

                }
                else {
                    System.out.println("Exceed of overdraft amount£¬transaction deny£¡\n");
                }
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
            if ((super.getBalance() - amount) >= 0) {
                setBalance(getBalance() - amount);
                withdrawalHistory.add(new Withdrawal(formattedDate, amount));

                System.out.println("A successful withdraw!" +
                        "Now, the account balance is " + getBalance() + ".\n");
            }
            else {
                if (amount >= super.getBalance()+this.od ) {
                    this.od = this.od + super.getBalance() - amount;  
                    setBalance(0);
                    withdrawalHistory.add(new Withdrawal(formattedDate, amount));

                    System.out.println("Balance is not enough, you now overdraft your account");
                    System.out.println("You now overdraft " + (5000-this.od) + " yuan after this exchange. ");
                    System.out.println("you can still withdraw " + this.od + " yuan.\n");

                }
                else {
                    System.out.println("Exceed of overdraft amount£¬transaction deny£¡\n");
                }
            }
        }
        else{
            System.out.println("your account has been suspended, transaction deny\n");
        }
    }

    public double getOverdraft(){
        return this.od;
    }
    
}
