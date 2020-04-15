package model;


import java.util.ArrayList;

public class Customer extends Account {
    public static ArrayList<Customer> allCustomers;
    private ArrayList<BuyLog> customerLog;

    public Customer(String username, String password, String firstName, String lastName, String email, String phoneNumber, double credit, ArrayList<BuyLog> customerLog) {
        super(username, password, firstName, lastName, email, phoneNumber, credit);
        this.customerLog = customerLog;
    }

    public ArrayList<BuyLog> getCustomerLog() {
        return customerLog;
    }

    public void setCustomerLog(ArrayList<BuyLog> customerLog) {
        this.customerLog = customerLog;
    }
}