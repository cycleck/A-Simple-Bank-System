import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Random;

public class Customer {
    private String name;
    private String address;
    private LocalDate birthDate;
    private int age;
    private int credit=-1;
    
    private ArrayList<Account> accounts;
    
    public Customer(){
        this.credit = credit;
    }

    public Customer(String name, String address,LocalDate birthDate) {
        this.name = name;
        this.address = address;
        this.accounts = new ArrayList<>();
        this.birthDate=birthDate;
        LocalDate currentDate = LocalDate.now();
        this.age = Period.between(birthDate, currentDate).getYears();
        this.credit = newCredit();
    }

    public ArrayList<Account> getAccounts() {
        return this.accounts;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public LocalDate getBirthday(){
        return this.birthDate;
    }

    public int getCredit(){
        return this.credit;
    }

    public int getAge(){
        return this.age;
    }

    public int newCredit(){
        Random r = new Random();
        int num = r.nextInt(100) + 35;
        return num;
    }

    public void changeCredit(int num){
        this.credit-=num;
    }


    public void addAccounts(Account account) {
        accounts.add(account);
    }

   


}
