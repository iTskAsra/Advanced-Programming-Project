package Server.model;

import java.util.ArrayList;

public class Category {
    public static ArrayList<Category> allCategories;
    private String name;
    private ArrayList<Feature> features;
    private transient ArrayList<Product> categoryProducts;

    public Category(String name, ArrayList<Feature> features, ArrayList<Product> categoryProducts) {
        this.name = name;
        this.features = features;
        this.categoryProducts = categoryProducts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<Feature> features) {
        this.features = features;
    }

    public ArrayList<Product> getCategoryProducts() {
        return categoryProducts;
    }

    public void setCategoryProducts(ArrayList<Product> categoryProducts) {
        this.categoryProducts = categoryProducts;
    }
}
