import java.util.Scanner;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.*;

public class UI {
    private Account account;
    private Customer customer;
    private CustomerList customerList;
    
    public UI(){
        customerList = new CustomerList();
    }

    public void run(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Bank!");
       
        while (true) {
            System.out.println("==========================");
            System.out.println("     Banking System       ");
            System.out.println("==========================");
            System.out.println("Please select an option:");
            System.out.println("1. Login in");
            System.out.println("2. Create a new account");
            System.out.println("0. Exit");
            System.out.println("==========================");

            int n = scanner.nextInt();
            scanner.nextLine();

            switch (n) {
                case 1:
                    Login(scanner);
                    break;
                case 2:
                    Create(scanner);
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    private void Login(Scanner scanner) {
        System.out.println("Enter your Account Name:");
        String accNo = scanner.nextLine();

        System.out.println("Enter your Account Pin:");
        String accPin = scanner.nextLine();

        if(accNo == "Admin" && accPin == "1192608177"){

            System.out.println("1. Print all customers' information");
            System.out.println(" Exit (press any key)");
            int n = scanner.nextInt();
            scanner.nextLine();
            if(n==1){
                customerList.printCustomers();
            }else{
                return;
            }
            
        }else{
            int flag = checkAccout(accNo,accPin);
            if(flag == 1){
                Account_UI(accNo,scanner);
                return ;
            }
            if(flag == -1){
                System.out.println("Account does not exist!");
                return;
            }
            if(flag == 0){
                System.out.println("Wrong pin!");
                return;

            }

        }
    }

    private void Create(Scanner scanner) {
        double cash;
        System.out.println("Enter customer name:");
        String name = scanner.nextLine();

        System.out.println("Enter customer address:");
        String address = scanner.nextLine();

        System.out.println("Enter customer date of birth (yyyy-mm-dd):");
        LocalDate birthDate = LocalDate.parse(scanner.nextLine());

        int k = create_Account_UI(name,address,birthDate);
        int type = 0;
        if(k==-1)return;
        while (true){
            int n = scanner.nextInt();
            scanner.nextLine();
            if(k==0){
                switch (n) {
                    case 1:
                        type = n;
                        break;
                    case 2:
                        type = n;
                        break;
                    case 3:
                        type = n;
                        break;
                    case 0:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                        break;
                    }
            }

            if(k==1){
                switch (n) {
                    case 2:
                        type = n;
                        break;
                    case 3:
                        type = n;
                        break;
                    case 0:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                        break;
                    }
            }
            if(k==2){
                switch (n) {
                    case 1:
                        type = n;
                        break;
                    case 3:
                        type = n;
                        break;
                    case 0:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                        break;
                    }
            }
            if(k==3){
                switch (n) {
                    case 3:
                        type = n;
                        break;
                    case 0:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                        break;
                    }
            }
            if(k==4){
                switch (n) {
                    case 1:
                        type = n;
                        break;
                    case 2:
                        type = n;
                        break;
                    case 0:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                        break;
                    }
            }
            if(k==5){
                switch (n) {
                    case 2:
                        type = n;
                        break;
                    case 0:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                        break;
                    }

            }
            if(k==6){
                switch (n) {
                    case 1:
                        type = n;
                        break;
                    case 0:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                        break;
                }
            }
                
        

            System.out.println("You need to save at least 100 yuan to open an account.");
            System.out.println("Save: ");
            cash = scanner.nextDouble();
            scanner.nextLine();
            if(cash >= 100.0){
                System.out.println("Account created successfully!");
                switch(type){
                    case 1:
                        account = new CurrentAccount(customer,cash,type);
                        break;
                    case 2:
                        account = new SaverAccount(customer,cash,type);
                        break;
                    case 3:
                        account = new JuniorAccount(customer,cash,type);
                        break;
                    default:
                        break;
                }
                
                customer.addAccounts(account);
                System.out.println("Your accout number is "+account.getAccNo());
                System.out.println("Your pin is "+account.getPin());

                while(true){
                    System.out.println("==========================");
                    System.out.println("Would you like to continue operating the account?");
                    System.out.println("1. yse");
                    System.out.println("2. no");
                    System.out.println("==========================");
                    int c = scanner.nextInt();
                    scanner.nextLine();
                    String strNo = account.getAccNo(); 
                    if(c == 1){
                        Account_UI(strNo,scanner);
                        break;
                    }
                    else if(c == 2){
                        System.out.println("Logging out...");
                        break;
                    }
                    else {
                        System.out.println("Invalid choice!");
                    }

                }
                break;
            }else{
                System.out.println("Sorry, we are unable to open a new account for you.");
            
            }
        }

    }
        

    public int create_Account_UI(String name, String address, LocalDate birthDate)
    {
        customer = new Customer();
        for(Customer now:customerList.getCustomers()){
            if (now.getName().equals(name) && now.getAddress().equals(address) && now.getBirthday().equals(birthDate)) {
                customer = now;
                break;
            }
        }
        
        int age;
        int cases = 0;
        String strch[]={"Exit","Create a new Current account","Create a new Saver account","Create a new Junior account"};

        if(customer.getCredit()>=40){
            String hash = PinProducer.producePin();
            age = customer.getAge();
            for(Account no:customer.getAccounts()){
                String accno=no.getAccNo();
                if(accno == hash+"01"){
                    cases++;
                }
                if(accno == hash+"10"){
                    cases+=2;
                }
                if(accno == hash+"11"){
                    cases+=4;
                }
            }
            if(cases==7||(cases==3 && age>=16)){
                System.out.println("Sorry, as the number of your accounts have reached the limit, we are unable to open a new account");
                return -1;
            }
            System.out.println("Attention: You need to save at least 100 yuan to open an account.");
            if(age<16){
                for(int i=1;i<=3;i++){
                    if((cases>>(i-1)|1)==0){
                        System.out.println(i+". "+strch[i]);
                    }
                }
            }
            else{
                for(int i=1;i<=2;i++){
                    if((cases>>(i-1)|1)==0){
                        System.out.println(i+". "+strch[i]);
                    }
                }
            }
            System.out.println("0. Exit");
            return cases;
        }else if(customer.getCredit() > -1 && customer.getCredit() < 40){
            System.out.println("Sorry, we are unable to open a new account for you due to your credit history.");
            return -1;
        }else{
            customer = new Customer(name, address, birthDate);
            customerList.addCustomer(customer);
            int ll = customer.getCredit();

            if(customer.getCredit()<40){
                System.out.println("Sorry, we are unable to open a new account for you due to your credit history.");
                return -1;
            }else{
                age = customer.getAge();
                System.out.println("Attention: You need to save at least 100 yuan to open an account.");
                if(age<16){
                    for(int i=1;i<4;i++){
                        System.out.println(i+". "+strch[i]);
                    }

                }else{
                    for(int i=1;i<3;i++){
                        System.out.println(i+". "+strch[i]);
                    }
                    cases+=4;
                }
                System.out.println("0. Exit");
            }
            return cases;
        }

    }

    public int checkAccout(String accNo, String accPin){
        for(Customer c:customerList.getCustomers()){
            for(Account now:c.getAccounts()){
                String AccNo = now.getAccNo();
                if(accNo.equals(AccNo)){
                    if(accPin.equals(now.getPin())){
                        return 1;
                    }
                    else {
                        return 0;
                    }
                }
            }
        }
        return -1;
    }


    public void Account_UI(String accNo, Scanner scanner){
        Account pre = new Account();
        for(Customer c:customerList.getCustomers()){
            for(Account now:c.getAccounts()){
                String AccNo = now.getAccNo();
                if(accNo.equals(AccNo)){
                    pre = now;
                }
            }
        }

        String num = pre.getAccNo().substring(8);
        LocalDate o = LocalDate.now();

        switch (num) {
            case "01":
                System.out.println("Your account is current account.");
                System.out.println("The balance: "+pre.getBalance());
                System.out.println("The overdraft is 5000.00 yuan. And it rests "+ ((CurrentAccount) pre).getOverdraft()+" yuan.");
                System.out.println(account.getDay(o)+" days left until repayment date ");
                break;
            case "10":
                System.out.println("Your account is saver account.");
                System.out.println("The balance: "+pre.getBalance());
                int day = 180-Period.between(pre.getPastFull(), o).getDays();
                System.out.println(" There are at least "+day+" days left to receive interest without withdrawal");
                break;
            case "11":
                System.out.println("Your account is junior account.");
                System.out.println("The balance: "+pre.getBalance());
                System.out.println("The limit/day is 100 yuan. And it rests "+((JuniorAccount) pre).getDayOd()+" yuan.");
                break;
            default:
                break;
        }
        while (true) {
            System.out.println("======================================");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Clear funds");
            System.out.println("4. suspend account");
            System.out.println("5. close account");
            System.out.println("6. view withdraw history");
            System.out.println("7. view Deposit history");
            System.out.println("8. view Clear funds history");
            System.out.println("0. Exit");
            System.out.println("======================================");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("1. Transfer");
                    System.out.println("2. Cash");
                    System.out.println("Any else key. Cancel");
                    int ch = scanner.nextInt();
                    switch (ch) {
                        case 1:
                    
                            System.out.println("Enter the account to transfer:");
                            String toAccNo = scanner.nextLine();
                            System.out.println("Enter the amount to deposit:");
                            double DepositAmount1 = scanner.nextDouble();
                            scanner.nextLine();
                            System.out.println("Enter your Pin to confirm:");
                            String Pin = scanner.nextLine();
                            if(Pin.equals(account.getPin())){
                                account.transAmount(DepositAmount1,toAccNo,customerList);
                            }else{
                                System.out.println("Wrong Pin!");
                            }
                            break;
                        case 2:
                            System.out.println("Enter the amount to deposit:");
                            double DepositAmount2 = scanner.nextDouble();
                            scanner.nextLine();
                            account.deposit(DepositAmount2);
                            break;
                        default :
                            break;
                    }
                    break;
                case 2:
                    //withdraw
                    System.out.println("Enter the amount to withdraw:");
                    double WithdrawAmount = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println("Enter your Pin to confirm:");
                    String Pin = scanner.nextLine();
                        if(Pin.equals(account.getPin())){
                            account.withdraw(WithdrawAmount);
                        }else{
                            System.out.println("Wrong Pin!");
                        }
                    break;
                case 3:
                    //clear funds
                    account.clear_fund();
                    break;

                case 4:
                    //suspend account
                    account.changeSus(scanner);
                case 5:
                    //close account
                    
                    account.clear_fund();
                    account.close(scanner);
                    return;
                case 6:
                    //view withdraw history
                    System.out.println("Viewing withdraw history...");
                    account.viewWithdrawalHistory();
                    break;

                case 7:
                    //view deposit history
                    System.out.println("Viewing deposit history...");
                    account.viewDepositHistory();
                    break;

                case 8:
                    //view clear funds history
                    System.out.println("Viewing clear funds history...");
                    account.viewClearFundsHistory();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }
    
    



    public static void main(String[] args) {
        UI ui = new UI();
        ui.run();
    }

}

