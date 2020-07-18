package Client.ClientController;

import Client.Client;
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

    public static String digest() {

        String func = "Digest";
        Client.sendMessage(func);

        return Client.receiveMessage();
//        Gson gson = new GsonBuilder().serializeNulls().create();
//        String data = gson.toJson(getProduct());
//        return data;
    }

    public static void addToCart() throws ExceptionsLibrary.SelectASeller, ExceptionsLibrary.NotEnoughNumberAvailableException {

        String func = "Add To Cart";
        Client.sendMessage(func);

        Client.sendObject(getProduct());
        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.SelectASeller)
            throw new ExceptionsLibrary.SelectASeller();
        else if (response instanceof ExceptionsLibrary.NotEnoughNumberAvailableException)
            throw new ExceptionsLibrary.NotEnoughNumberAvailableException();
        else
            return;
//        if (getProduct().getSeller() == null) {
//            throw new ExceptionsLibrary.SelectASeller();
//        } else {
//            if (getProduct().getAvailability() >= 1) {
//                CartController.getCartProducts().put(getProduct(), 1);
//            } else {
//                throw new ExceptionsLibrary.NotEnoughNumberAvailableException();
//            }
//        }
    }

    public static void selectSeller(String sellerUsername) throws ExceptionsLibrary.NoAccountException {

        String func = "Select Seller";
        Client.sendMessage(func);

        Client.sendMessage(sellerUsername);
        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoAccountException)
            throw new ExceptionsLibrary.NoAccountException();
        else
            return;
//        Seller seller = (Seller) GetDataFromDatabase.getAccount(sellerUsername);
//        product.setSeller(seller);
    }

    public static Product attributes() {
        return getProduct();
    }

    public static Product[] compare(int productId) throws ExceptionsLibrary.NoProductException, ExceptionsLibrary.CategoriesNotMatch {
        Product[] products = new Product[2];

        String func = "Compare";
        Client.sendMessage(func);



        Client.sendObject(productId);
        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoProductException)
            throw new ExceptionsLibrary.NoProductException();
        else if (response instanceof ExceptionsLibrary.CategoriesNotMatch)
            throw new ExceptionsLibrary.CategoriesNotMatch();
        else
            products = (Product[]) response;

        return products;
//        products[0] = getProduct();
//        Product product1 = GetDataFromDatabase.getProduct(productId);
//        products[1] = product1;
//        if (!products[0].getCategory().getName().equals(products[1].getCategory().getName())){
//            throw new ExceptionsLibrary.CategoriesNotMatch();
//        }
//        return products;
    }

    public static ArrayList<Comment> comments() {
        String func = "Show Comments";
        Client.sendMessage(func);

        Client.sendObject(getProduct());

        return (ArrayList<Comment>) Client.receiveObject();
    }

    public static void addComment(String title, String commentText) throws ExceptionsLibrary.NotLoggedInException, ExceptionsLibrary.NoAccountException, ExceptionsLibrary.NoProductException {

        String func = "Add Comment";
        Client.sendObject(func);

        Object[] toSend = new Object[3];
        toSend[0] = getProduct();
        toSend[1] = title;
        toSend[2] = commentText;
        Client.sendObject(toSend);

        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NotLoggedInException)
            throw new ExceptionsLibrary.NotLoggedInException();
        if (response instanceof ExceptionsLibrary.NoAccountException)
            throw new ExceptionsLibrary.NoAccountException();
        if (response instanceof ExceptionsLibrary.NoProductException)
            throw new ExceptionsLibrary.NoProductException();

//        if (Main.checkLoggedIn() != null) {
//            Comment comment = new Comment(controller.CustomerController.getCustomer().getUsername(), getProduct(), commentText, RequestOrCommentCondition.PENDING_TO_ACCEPT, isBoughtByCommenter(controller.CustomerController.getCustomer(), getProduct()), title);
//            comment.setBoughtByCommenter(isBoughtByCommenter(CustomerController.getCustomer(), getProduct()));
//            getProduct().getProductComments().add(comment);
//            SetDataToDatabase.setProduct(getProduct());
//            SetDataToDatabase.updateSellerOfProduct(getProduct(),0);
//        }
//        else {
//            throw new ExceptionsLibrary.NotLoggedInException();
//        }

    }

    public static boolean isBoughtByCommenter(Customer customer, Product product) {

        String func = "Is Bought By Commenter";
        Client.sendMessage(func);

        Object[] toSend = new Object[2];
        toSend[0] = customer;
        toSend[1] = product;

        Client.sendObject(toSend);

        return (boolean) Client.receiveObject();
//        for (BuyLog i : customer.getCustomerLog()) {
//            for (String[] j : i.getLogProducts()) {
//                if (Integer.parseInt(j[0]) == product.getProductId()) {
//                    return true;
//                }
//            }
//        }
//        return false;
    }


}
