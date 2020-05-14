package model;

import java.util.ArrayList;
import java.util.Random;

public class Product {
    private int productId;
    private ProductOrOffCondition productCondition;
    private String name;
    private String company;
    private double price;
    private double priceWithOff;
    private transient Seller seller;
    private int availability;
    private Category category;
    private ArrayList<Feature> categoryFeatures;
    private String description;
    private String date;
    private ArrayList<Rate> rates;
    private ArrayList<Comment> productComments;

    public Product(ProductOrOffCondition productCondition, String name, String company, double price, Seller seller, int availability, Category category, ArrayList<Feature> categoryFeatures, String description, ArrayList<Rate> rates, ArrayList<Comment> productComments, String date,Double priceWithOff) {
        Random random = new Random();
        this.productId = random.nextInt(10000);
        this.productCondition = productCondition;
        this.name = name;
        this.company = company;
        this.price = price;
        this.seller = seller;
        this.availability = availability;
        this.category = category;
        this.categoryFeatures = categoryFeatures;
        this.description = description;
        this.rates = new ArrayList<>();
        this.productComments = new ArrayList<>();
        this.priceWithOff = priceWithOff;
        this.date = date;
    }

    public double getPriceWithOff() {
        return priceWithOff;
    }

    public void setPriceWithOff(double priceWithOff) {
        this.priceWithOff = priceWithOff;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public ArrayList<Feature> getCategoryFeatures() {
        return categoryFeatures;
    }

    public void setCategoryFeatures(ArrayList<Feature> categoryFeatures) {
        this.categoryFeatures = categoryFeatures;
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
}
