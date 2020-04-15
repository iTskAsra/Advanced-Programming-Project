package model;

import java.util.ArrayList;

public class BuyLog {
    public static ArrayList<BuyLog> allBuyLogs;
    private int logId;
    private String logDate;
    private double value;
    private double discountApplied;
    private ArrayList<Product> logProducts;
    private String sellerName;
    private boolean deliveryCondition;

    public BuyLog(int logId, String logDate, double value, double discountApplied, ArrayList<Product> logProducts, String sellerName, boolean deliveryCondition) {
        this.logId = logId;
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

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public boolean isDeliveryCondition() {
        return deliveryCondition;
    }

    public void setDeliveryCondition(boolean deliveryCondition) {
        this.deliveryCondition = deliveryCondition;
    }
}
