package Server.ServerController;

import Server.ClientHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;
import Client.ClientView.MainMenuStage.Main;


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
            ClientHandler.sendObject(new ExceptionsLibrary.SelectASeller());
            return;
        } else {
            if (getProduct().getAvailability() >= 1) {
                CartController.getCartProducts().put(getProduct(), 1);
            } else {
                ClientHandler.sendObject(new ExceptionsLibrary.NotEnoughNumberAvailableException());
                return;
            }
        }
        ClientHandler.sendMessage("Success!");
    }

    public static void selectSeller() throws ExceptionsLibrary.NoAccountException {
        String sellerUsername = ClientHandler.receiveMessage();
        Seller seller = (Seller) GetDataFromDatabaseServerSide.getAccount(sellerUsername);
        product.setSeller(seller);
        ClientHandler.sendMessage("Success!");
    }

    public static Product attributes() {
        return getProduct();
    }

    public static void compare() throws ExceptionsLibrary.NoProductException, ExceptionsLibrary.CategoriesNotMatch {
        int productId = (int) ClientHandler.receiveObject();
        Product[] products = new Product[2];
        products[0] = getProduct();
        Product product1 = GetDataFromDatabaseServerSide.getProduct(productId);
        products[1] = product1;
        if (!products[0].getCategory().getName().equals(products[1].getCategory().getName())){
            throw new ExceptionsLibrary.CategoriesNotMatch();
        }
        ClientHandler.sendObject(products);
    }

    public static void comments() {
        ClientHandler.sendObject (((Product) ClientHandler.receiveObject()).getProductComments());
    }

    public static void addComment() throws ExceptionsLibrary.NotLoggedInException, ExceptionsLibrary.NoAccountException, ExceptionsLibrary.NoProductException {
        Object[] receivedData = (Object[]) ClientHandler.receiveObject();
        String title = (String) receivedData[0];
        String commentText = (String) receivedData[1];
        if (Main.checkLoggedIn() != null) {
            Comment comment = new Comment(CustomerController.getCustomer().getUsername(), getProduct(), commentText, RequestOrCommentCondition.PENDING_TO_ACCEPT, isBoughtByCommenter(CustomerController.getCustomer(), getProduct()), title);
            comment.setBoughtByCommenter(isBoughtByCommenter(CustomerController.getCustomer(), getProduct()));
            getProduct().getProductComments().add(comment);
            SetDataToDatabase.setProduct(getProduct());
            SetDataToDatabase.updateSellerOfProduct(getProduct(),0);
        }
        else {
            ClientHandler.sendObject(new ExceptionsLibrary.NotLoggedInException());
            return;
        }

        ClientHandler.sendMessage("Success!");
    }

    public static boolean isBoughtByCommenter(Customer customer, Product product) {
        for (BuyLog i : customer.getCustomerLog()) {
            for (String[] j : i.getLogProducts()) {
                if (Integer.parseInt(j[0]) == product.getProductId()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void isBoughtByCommenter() {
        Object[] receivedData = (Object[]) ClientHandler.receiveObject();
        Customer customer = (Customer) receivedData[0];
        Product product = (Product) receivedData[1];
        for (BuyLog i : customer.getCustomerLog()) {
            for (String[] j : i.getLogProducts()) {
                if (Integer.parseInt(j[0]) == product.getProductId()) {
                    ClientHandler.sendObject(true);
                    return;
                }
            }
        }
        ClientHandler.sendObject(false);
    }


}
