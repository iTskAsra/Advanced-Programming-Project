package model;

import java.util.ArrayList;
import java.util.Random;

public class Off {
    private int offId;
    private ArrayList<String> offProducts;
    private ProductOrOffCondition offCondition;
    private String startDate;
    private String endDate;
    private double offAmount;

    public Off(ArrayList<String> offProducts, ProductOrOffCondition offCondition, String startDate, String endDate, double offAmount) {
        Random random = new Random();
        this.offId = random.nextInt(1000000);
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

    public ArrayList<String> getOffProducts() {
        return offProducts;
    }

    public void setOffProducts(ArrayList<String> offProducts) {
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
