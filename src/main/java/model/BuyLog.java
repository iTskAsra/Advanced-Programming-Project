package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class BuyLog {
    private int logId;
    private String logDate;
    private double value;
    private double discountApplied;
    private HashMap<String,String> receiverInfo;
    //private ArrayList<Product> logProducts;
    //private ArrayList<String> sellerName;
    private HashMap<Product,Integer> logProducts;
    private String deliveryCondition;

    public BuyLog(String logDate, double value, double discountApplied, HashMap<Product,Integer> logProducts, String deliveryCondition,HashMap<String,String> receiverInfo) {
        Random random = new Random();
        this.logId = random.nextInt(10000);
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

    public HashMap<Product,Integer> getLogProducts() {
        return logProducts;
    }

    public void setLogProducts(HashMap<Product,Integer> logProducts) {
        this.logProducts = logProducts;
    }

    /*public ArrayList<String> getSellerName() {
        return sellerName;
    }

    public void setSellerName(ArrayList<String> sellerName) {
        this.sellerName = sellerName;
    }*/

    public String getDeliveryCondition() {
        return deliveryCondition;
    }

    public void setDeliveryCondition(String deliveryCondition) {
        this.deliveryCondition = deliveryCondition;
    }
}
