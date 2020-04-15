package model;

import java.util.ArrayList;

public class Product {
    public static ArrayList<Product> allProducts;
    private int productId;
    private ProductOrOffCondition productCondition;
    private String name;
    private String company;
    private double price;
    private Seller seller;
    private int availability;
    private Category category;
    private ArrayList<Feature> categoryFetures;
    private String description;
    private ArrayList<Rate> rates;
    private ArrayList<Comment> productComments;
    private ArrayList<Customer> productBuyers;

    public Product(int productId, ProductOrOffCondition productCondition, String name, String company, double price, Seller seller, int availability, Category category, ArrayList<Feature> categoryFetures, String description, ArrayList<Rate> rates, ArrayList<Comment> productComments, ArrayList<Customer> productBuyers) {
        this.productId = productId;
        this.productCondition = productCondition;
        this.name = name;
        this.company = company;
        this.price = price;
        this.seller = seller;
        this.availability = availability;
        this.category = category;
        //specify category features
        this.categoryFetures = categoryFetures;
        this.description = description;
        this.rates = new ArrayList<>();
        this.productComments = new ArrayList<>();
        this.productBuyers = new ArrayList<>();
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public ProductOrOffCondition getProductCondition() {
        return productCondition;
    }

    public void setProductCondition(ProductOrOffCondition productCondition) {
        this.productCondition = productCondition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ArrayList<Feature> getCategoryFetures() {
        return categoryFetures;
    }

    public void setCategoryFetures(ArrayList<Feature> categoryFetures) {
        this.categoryFetures = categoryFetures;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Rate> getRates() {
        return rates;
    }

    public void setRates(ArrayList<Rate> rates) {
        this.rates = rates;
    }

    public ArrayList<Comment> getProductComments() {
        return productComments;
    }

    public void setProductComments(ArrayList<Comment> productComments) {
        this.productComments = productComments;
    }

    public ArrayList<Customer> getProductBuyers() {
        return productBuyers;
    }

    public void setProductBuyers(ArrayList<Customer> productBuyers) {
        this.productBuyers = productBuyers;
    }
}
