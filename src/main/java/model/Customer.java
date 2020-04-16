package model;


import java.util.ArrayList;

public class Customer extends Account {
    public static ArrayList<Customer> allCustomers;
    private ArrayList<BuyLog> customerLog;

    public Customer(String username, String password, String role, String firstName, String lastName, String email, String phoneNumber, ArrayList<Sale> saleCodes, double credit, ArrayList<BuyLog> customerLog) {
        super(username, password, role, firstName, lastName, email, phoneNumber, saleCodes, credit);
        this.customerLog = customerLog;
    }

    public ArrayList<BuyLog> getCustomerLog() {
        return customerLog;
    }

    public void setCustomerLog(ArrayList<BuyLog> customerLog) {
        this.customerLog = customerLog;
    }
}