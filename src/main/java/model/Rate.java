package model;

import java.util.ArrayList;

public class Rate {
    public static ArrayList<Rate> allRates;
    private Account rateAccount;
    private double rateScore;

    public Rate(Account rateAccount, Product rateProduct, double rateScore) {
        this.rateAccount = rateAccount;
        this.rateScore = rateScore;
    }

    public Account getRateAccount() {
        return rateAccount;
    }

    public void setRateAccount(Account rateAccount) {
        this.rateAccount = rateAccount;
    }

    public double getRateScore() {
        return rateScore;
    }

    public void setRateScore(double rateScore) {
        this.rateScore = rateScore;
    }
}
