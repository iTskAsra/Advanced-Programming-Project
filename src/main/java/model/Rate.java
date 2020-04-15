package model;

import java.util.ArrayList;

public class Rate {
    public static ArrayList<Rate> allRates;
    private Account rateAccount;
    private Product rateProduct;
    private double rateScore;

    public Rate(Account rateAccount, Product rateProduct, double rateScore) {
        this.rateAccount = rateAccount;
        this.rateProduct = rateProduct;
        this.rateScore = rateScore;
    }

    public Account getRateAccount() {
        return rateAccount;
    }

    public void setRateAccount(Account rateAccount) {
        this.rateAccount = rateAccount;
    }

    public Product getRateProduct() {
        return rateProduct;
    }

    public void setRateProduct(Product rateProduct) {
        this.rateProduct = rateProduct;
    }

    public double getRateScore() {
        return rateScore;
    }

    public void setRateScore(double rateScore) {
        this.rateScore = rateScore;
    }
}
