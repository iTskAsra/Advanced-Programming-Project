package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;

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

    public static String digest(){
        Gson gson = new GsonBuilder().serializeNulls().create();
        String data = gson.toJson(getProduct());
        return data;
    }

    public static void addToCart(){
        if (getProduct().getSeller() == null){
            //TODO Error
        }
        else {
            if (getProduct().getAvailability()>=1){
                Cart.getCartProducts().put(getProduct(),1);
            }
            else {
                //TODO Error
            }
        }
    }

    public static void selectSeller(String sellerUsername){
        Seller seller = (Seller) GetDataFromDatabase.getAccount(sellerUsername);
        product.setSeller(seller);
    }

    public static String attributes(){
        Gson gson = new GsonBuilder().serializeNulls().create();
        String data = gson.toJson(getProduct());
        return data;
    }

    public static String[] compare(int productId){
        String[] products = new String[2];
        Gson gson = new GsonBuilder().serializeNulls().create();
        String firstProductData = gson.toJson(getProduct());
        products[0] = firstProductData;
        Gson gson1 =  new GsonBuilder().serializeNulls().create();
        Product product1 = GetDataFromDatabase.getProduct(productId);
        String secondProductData = gson1.toJson(product1);
        products[1] = secondProductData;
        return products;
    }

    public static ArrayList<Comment> comments(){
        return getProduct().getProductComments();
    }

    public static void addComment(String commentText){
        Comment comment = new Comment(CustomerController.getCustomer(),getProduct(),commentText, RequestOrCommentCondition.PENDING_TO_ACCEPT,isBoughtByCommenter(CustomerController.getCustomer(),getProduct()));
        if (isBoughtByCommenter(CustomerController.getCustomer(),getProduct())){
            getProduct().getProductComments().add(comment);
            SetDataToDatabase.setProduct(getProduct());
        }
    }

    private static boolean isBoughtByCommenter(Customer customer, Product product) {
        for (BuyLog i : customer.getCustomerLog()){
            for (Product j : i.getLogProducts()){
                if (j.getProductId() == product.getProductId()){
                    return true;
                }
            }
        }
        return false;
    }


}
