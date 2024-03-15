import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SaverAccount extends Account{

    public SaverAccount(Customer owner,double cash,int n){
        super(owner,cash,n);
    } 
      
    @Override
    public void deposit(double amount) {
        if(getSuspend() == false) {
            setBalance(getBalance() - amount);
            depositHistory.add(new Deposit(amount));
            System.out.println("DEPOSIT finish, balance:" + getBalance() + " yuan\n");
            if(getBalance()>0){
                LocalDate now = LocalDate.now();
                setDate(now) ;
            }
        }
        else{
            System.out.println("your account has been suspended, transaction deny\n");
        }
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
            
            if ((super.getBalance() - amount) >= 0) {
                setBalance(getBalance() - amount);
                withdrawalHistory.add(new Withdrawal(formattedDate, amount));
                System.out.println("A successful withdraw!" +
                        "Now, the account balance is " + getBalance() + ".\n");
            }
            else {
                    System.out.println("Exceed of overdraft amount£¬transaction deny£¡\n");
                
            }
        }
        else{
            System.out.println("your account has been suspended, transaction deny\n");
        }
    }

}
