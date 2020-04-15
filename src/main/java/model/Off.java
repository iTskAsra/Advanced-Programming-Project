package model;

import java.util.ArrayList;

public class Off {
    public static ArrayList<Off> allOffs;
    private int offId;
    private ArrayList<Product> offProducts;
    private ProductOrOffCondition offCondition;
    private String startDate;
    private String endDate;
    private double offAmount;

    public Off(int offId, ArrayList<Product> offProducts, ProductOrOffCondition offCondition, String startDate, String endDate, double offAmount) {
        this.offId = offId;
        this.offProducts = offProducts;
        this.offCondition = offCondition;
        this.startDate = startDate;
        this.endDate = endDate;
        this.offAmount = offAmount;
    }

    public int getOffId() {
        return offId;
    }

    public void setOffId(int offId) {
        this.offId = offId;
    }

    public ArrayList<Product> getOffProducts() {
        return offProducts;
    }

    public void setOffProducts(ArrayList<Product> offProducts) {
        this.offProducts = offProducts;
    }

    public ProductOrOffCondition getOffCondition() {
        return offCondition;
    }

    public void setOffCondition(ProductOrOffCondition offCondition) {
        this.offCondition = offCondition;
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

    public double getOffAmount() {
        return offAmount;
    }

    public void setOffAmount(double offAmount) {
        this.offAmount = offAmount;
    }
}
