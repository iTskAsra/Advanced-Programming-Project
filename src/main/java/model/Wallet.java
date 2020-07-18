package model;

import model.Customer;
import model.Seller;


public class Wallet {

    private Account account;
    private double balance;
    private int bankAccountId;
    private static double leastAmount = 0;

    public Wallet(Account account, double balance) {
        this.account = account;
        this.balance = balance;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
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
