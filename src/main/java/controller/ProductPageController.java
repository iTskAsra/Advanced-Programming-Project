package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;
import view.Main;

import java.util.ArrayList;

public class ProductPageController {
    private static Product product;

    public ProductPageController(Product product) {
        this.product = product;
    }

    public static Product getProduct() {
        return product;
    }

    public static void setProduct(Product product) {
        ProductPageController.product = product;
    }

    public static String digest() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String data = gson.toJson(getProduct());
        return data;
    }

    public static void addToCart() throws ExceptionsLibrary.SelectASeller, ExceptionsLibrary.NotEnoughNumberAvailableException {
        if (getProduct().getSeller() == null) {
            throw new ExceptionsLibrary.SelectASeller();
        } else {
            if (getProduct().getAvailability() >= 1) {
                CartController.getCartProducts().put(getProduct(), 1);
            } else {
                throw new ExceptionsLibrary.NotEnoughNumberAvailableException();
            }
        }
    }

    public static void selectSeller(String sellerUsername) throws ExceptionsLibrary.NoAccountException {
        Seller seller = (Seller) GetDataFromDatabase.getAccount(sellerUsername);
        product.setSeller(seller);
    }

    public static Product attributes() {
        return getProduct();
    }

    public static String[] compare(int productId) throws ExceptionsLibrary.NoProductException {
        String[] products = new String[2];
        Gson gson = new GsonBuilder().serializeNulls().create();
        String firstProductData = gson.toJson(getProduct());
        products[0] = firstProductData;
        Gson gson1 = new GsonBuilder().serializeNulls().create();
        Product product1 = GetDataFromDatabase.getProduct(productId);
        String secondProductData = gson1.toJson(product1);
        products[1] = secondProductData;
        return products;
    }

    public static ArrayList<Comment> comments() {
        return getProduct().getProductComments();
    }

    public static void addComment(String title, String commentText) throws ExceptionsLibrary.NotLoggedInException, ExceptionsLibrary.NoAccountException {
        if (Main.checkLoggedIn() != null) {
            Comment comment = new Comment(CustomerController.getCustomer().getUsername(), getProduct(), commentText, RequestOrCommentCondition.PENDING_TO_ACCEPT, isBoughtByCommenter(CustomerController.getCustomer(), getProduct()), title);
            comment.setBoughtByCommenter(isBoughtByCommenter(CustomerController.getCustomer(), getProduct()));
            getProduct().getProductComments().add(comment);
            SetDataToDatabase.setProduct(getProduct());
            SetDataToDatabase.updateSellerAndOffsOfProduct(getProduct(),0);
        }
        else {
            throw new ExceptionsLibrary.NotLoggedInException();
        }

    }

    private static boolean isBoughtByCommenter(Customer customer, Product product) {
        for (BuyLog i : customer.getCustomerLog()) {
            for (Product j : i.getLogProducts().keySet()) {
                if (j.getProductId() == product.getProductId()) {
                    return true;
                }
            }
        }
        return false;
    }


}
