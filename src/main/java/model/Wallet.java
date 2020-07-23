package model;

import model.Customer;
import model.Seller;


public class Wallet implements java.io.Serializable {

    private String account;
    private double balance;
    private int bankAccountId;
    private static double leastAmount = 0;

    public Wallet(String account, double balance) {
        this.account = account;
        this.balance = balance;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(int bankAccountId) {
        this.bankAccountId = bankAccountId;
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
