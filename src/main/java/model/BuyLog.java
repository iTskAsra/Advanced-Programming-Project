package model;

import java.util.ArrayList;
import java.util.Random;

public class BuyLog {
    public static ArrayList<BuyLog> allBuyLogs;
    private int logId;
    private String logDate;
    private double value;
    private double discountApplied;
    private ArrayList<Product> logProducts;
    private ArrayList<String> sellerName;
    private String deliveryCondition;

    public BuyLog(String logDate, double value, double discountApplied, ArrayList<Product> logProducts, ArrayList<String> sellerName, String deliveryCondition) {
        Random random = new Random();
        this.logId = random.nextInt(10000);
        this.logDate = logDate;
        this.value = value;
        this.discountApplied = discountApplied;
        this.logProducts = logProducts;
        this.sellerName = sellerName;
        this.deliveryCondition = deliveryCondition;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getDiscountApplied() {
        return discountApplied;
    }

    public void setDiscountApplied(double discountApplied) {
        this.discountApplied = discountApplied;
    }

    public ArrayList<Product> getLogProducts() {
        return logProducts;
    }

    public void setLogProducts(ArrayList<Product> logProducts) {
        this.logProducts = logProducts;
    }

    public ArrayList<String> getSellerName() {
        return sellerName;
    }

    public void setSellerName(ArrayList<String> sellerName) {
        this.sellerName = sellerName;
    }

    public String getDeliveryCondition() {
        return deliveryCondition;
    }

    public void setDeliveryCondition(String deliveryCondition) {
        this.deliveryCondition = deliveryCondition;
    }
}
