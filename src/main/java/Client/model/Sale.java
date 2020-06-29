package Client.model;

import java.util.ArrayList;

public class Sale {
    public static ArrayList<Sale> allSales;
    private String saleCode;
    private String startDate;
    private String endDate;
    private double salePercent;
    private double saleMaxAmount;
    private int validTimes;
    private transient ArrayList<Account> saleAccounts;

    public Sale(String startDate, String endDate, double salePercent, double saleMaxAmount, int validTimes, ArrayList<Account> saleAccounts) {
        this.saleCode = getRandomSaleCode();
        this.startDate = startDate;
        this.endDate = endDate;
        this.salePercent = salePercent;
        this.saleMaxAmount = saleMaxAmount;
        this.validTimes = validTimes;
        this.saleAccounts = saleAccounts;
    }

    public static String getRandomSaleCode() {
        final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i =0 ; i<8; i++) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            stringBuilder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return stringBuilder.toString();
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

    public ArrayList<Account> getSaleAccounts() {
        return saleAccounts;
    }

    public void setSaleAccounts(ArrayList<Account> saleAccounts) {
        this.saleAccounts = saleAccounts;
    }
}
