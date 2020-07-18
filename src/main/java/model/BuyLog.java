package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class BuyLog {
    private int logId;
    private String logDate;
    private double value;
    private double discountApplied;
    private HashMap<String, String> receiverInfo;
    private ArrayList<String[]> logProducts;
    private DeliveryCondition deliveryCondition;

    public BuyLog(String logDate, double value, double discountApplied, ArrayList<String[]> logProducts, DeliveryCondition deliveryCondition, HashMap<String, String> receiverInfo) {
        Random random = new Random();
        this.logId = random.nextInt(1000000);
        this.logDate = logDate;
        this.value = value;
        this.discountApplied = discountApplied;
        this.logProducts = logProducts;
        this.deliveryCondition = deliveryCondition;
        this.receiverInfo = receiverInfo;
    }

    public HashMap<String, String> getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(HashMap<String, String> receiverInfo) {
        this.receiverInfo = receiverInfo;
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

    public ArrayList<String[]> getLogProducts() {
        return logProducts;
    }

    public void setLogProducts(ArrayList<String[]> logProducts) {
        this.logProducts = logProducts;
    }

    public DeliveryCondition getDeliveryCondition() {
        return deliveryCondition;
    }

    public void setDeliveryCondition(DeliveryCondition deliveryCondition) {
        this.deliveryCondition = deliveryCondition;
    }
}
