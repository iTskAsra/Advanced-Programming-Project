package model;


import java.util.ArrayList;

public class Seller extends Account {
    public static ArrayList<Seller> allSellers;
    private String companyName;
    private ArrayList<Product> sellerProducts;
    private ArrayList<SellLog> sellerLogs;
    private ArrayList<Off> sellerOffs;

    public Seller(String username, String password, String firstName, String lastName, String email, String phoneNumber, double credit, String companyName, ArrayList<Product> sellerProducts, ArrayList<SellLog> sellerLogs, ArrayList<Off> sellerOffs) {
        super(username, password, firstName, lastName, email, phoneNumber, credit);
        this.companyName = companyName;
        this.sellerProducts = sellerProducts;
        this.sellerLogs = sellerLogs;
        this.sellerOffs = sellerOffs;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public ArrayList<Product> getSellerProducts() {
        return sellerProducts;
    }

    public void setSellerProducts(ArrayList<Product> sellerProducts) {
        this.sellerProducts = sellerProducts;
    }

    public ArrayList<SellLog> getSellerLogs() {
        return sellerLogs;
    }

    public void setSellerLogs(ArrayList<SellLog> sellerLogs) {
        this.sellerLogs = sellerLogs;
    }

    public ArrayList<Off> getSellerOffs() {
        return sellerOffs;
    }

    public void setSellerOffs(ArrayList<Off> sellerOffs) {
        this.sellerOffs = sellerOffs;
    }
}