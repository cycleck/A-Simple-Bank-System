import java.time.LocalDate;
import java.util.ArrayList;

public class CustomerList {
    private ArrayList<Customer> customerList;

    public CustomerList(){
        customerList = new ArrayList<Customer>();
    }

    public ArrayList<Customer> getCustomers(){
        return this.customerList;
    }

    public void addCustomer(Customer customer) {
        customerList.add(customer);
    }

    // remove customer from the list
    public void removeCustomer(Customer customer) {
        customerList.remove(customer);
    }
    
    public void printCustomers() {
        System.out.println("==========================");
        for (Customer customer : customerList) {
            System.out.println("Name: " + customer.getName());
            System.out.println("Age: " + customer.getAge());
            System.out.println("Credit: " + customer.getCredit());
            System.out.println("Date of birth: " + customer.getBirthday());
            System.out.println("Address: " + customer.getAddress());
            System.out.println("----------------------");
            for (Account account : customer.getAccounts()) {
                System.out.println("   Account number: " + account.getPin());
                System.out.println("   AccPin: " + account.getPin());
                System.out.println("   Balance: " + account.getBalance());
                System.out.println("----------------------");
                // print other bank account details if needed
            }
            System.out.println("==========================");
        }
        System.out.println("==========================");
    }

}
