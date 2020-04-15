package model;

import java.util.ArrayList;

public class Sale {
    public static ArrayList<Sale> allSales;
    private String saleCode;
    private String startDate;
    private String endDate;
    private double salePercent;
    private double saleMaxAmount;
    private int validTimes;
    private ArrayList<Customer> saleCustomer;

    public Sale(String saleCode, String startDate, String endDate, double salePercent, double saleMaxAmount, int validTimes, ArrayList<Customer> saleCustomer) {
        this.saleCode = saleCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.salePercent = salePercent;
        this.saleMaxAmount = saleMaxAmount;
        this.validTimes = validTimes;
        this.saleCustomer = saleCustomer;
    }

    public String getSaleCode() {
        return saleCode;
    }

    public void setSaleCode(String saleCode) {
        this.saleCode = saleCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getSalePercent() {
        return salePercent;
    }

    public void setSalePercent(double salePercent) {
        this.salePercent = salePercent;
    }

    public double getSaleMaxAmount() {
        return saleMaxAmount;
    }

    public void setSaleMaxAmount(double saleMaxAmount) {
        this.saleMaxAmount = saleMaxAmount;
    }

    public int getValidTimes() {
        return validTimes;
    }

    public void setValidTimes(int validTimes) {
        this.validTimes = validTimes;
    }

    public ArrayList<Customer> getSaleCustomer() {
        return saleCustomer;
    }

    public void setSaleCustomer(ArrayList<Customer> saleCustomer) {
        this.saleCustomer = saleCustomer;
    }
}
