package Bank;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static Bank.BankInitialize.accountIds;

public class BankAccount {
    private int accountId;
    private double balance;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    public BankAccount(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.accountId = ThreadLocalRandom.current().nextInt(10000, 100000);
        while (accountIds.contains(accountId)) {
            this.accountId = ThreadLocalRandom.current().nextInt(10000, 100000);
        }
        accountIds.add(accountId);
        this.balance = 0;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
