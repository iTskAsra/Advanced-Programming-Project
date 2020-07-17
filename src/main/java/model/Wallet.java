package model;

import model.Customer;
import model.Seller;


public class Wallet {

    private Customer customer = null;
    private Seller seller = null;
    private double balance;
    private static double leastAmount = 0;


    public Wallet(Customer customer, double balance) {
        this.customer = customer;
        this.balance = balance;
    }

    public Wallet(Seller seller, double balance) {
        this.seller = seller;
        this.balance = balance;
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public static double getLeastAmount() {
        return leastAmount;
    }

    public static void setLeastAmount(double leastAmount) {
        model.Wallet.leastAmount = leastAmount;
    }
}
