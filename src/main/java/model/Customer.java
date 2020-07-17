package model;


import model.Wallet;

import java.util.ArrayList;

public class Customer extends Account {
    private ArrayList<BuyLog> customerLog;
    private Wallet wallet;

    public Customer(String username, String password, String role, String firstName, String lastName, String email, String phoneNumber, ArrayList<Sale> saleCodes, double credit, ArrayList<BuyLog> customerLog) {
        super(username, password, role, firstName, lastName, email, phoneNumber, saleCodes, credit);
        this.customerLog = new ArrayList<>();
        this.wallet = new Wallet(this, credit);
    }

    public ArrayList<BuyLog> getCustomerLog() {
        return customerLog;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setCustomerLog(ArrayList<BuyLog> customerLog) {
        this.customerLog = customerLog;
    }
}