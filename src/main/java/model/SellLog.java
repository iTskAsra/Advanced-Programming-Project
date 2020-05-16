package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SellLog {
    public static ArrayList<SellLog> allSellLogs;
    private int logId;
    private String logDate;
    private double value;
    private double discountApplied;
    private HashMap<Product,Integer> logProducts;
    private String buyer;
    private String deliveryCondition;

    public SellLog(String logDate, double value, double discountApplied, HashMap<Product,Integer> logProducts, String buyer, String deliveryCondition) {
        Random random = new Random();
        this.logId = random.nextInt(10000);
        this.logDate = logDate;
        this.value = value;
        this.discountApplied = discountApplied;
        this.logProducts = logProducts;
        this.buyer = buyer;
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

    public HashMap<Product,Integer> getLogProducts() {
        return logProducts;
    }

    public void setLogProducts(HashMap<Product,Integer> logProducts) {
        this.logProducts = logProducts;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getDeliveryCondition() {
        return deliveryCondition;
    }

    public void setDeliveryCondition(String deliveryCondition) {
        this.deliveryCondition = deliveryCondition;
    }
}
