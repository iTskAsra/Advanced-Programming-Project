package model;

import java.util.ArrayList;

public class SellLog {
    public static ArrayList<SellLog> allSellLogs;
    private int logId;
    private String logDate;
    private double value;
    private double discountApplied;
    private ArrayList<Product> logProducts;
    private Customer buyerName;
    private boolean deliveryCondition;

    public SellLog(int logId, String logDate, double value, double discountApplied, ArrayList<Product> logProducts, Customer buyerName, boolean deliveryCondition) {
        this.logId = logId;
        this.logDate = logDate;
        this.value = value;
        this.discountApplied = discountApplied;
        this.logProducts = logProducts;
        this.buyerName = buyerName;
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

    public Customer getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(Customer buyerName) {
        this.buyerName = buyerName;
    }

    public boolean isDeliveryCondition() {
        return deliveryCondition;
    }

    public void setDeliveryCondition(boolean deliveryCondition) {
        this.deliveryCondition = deliveryCondition;
    }
}
