package Server.ServerController;

import Server.ClientHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;
import LocalExceptions.ExceptionsLibrary;
import java.lang.reflect.Field;
import java.util.HashMap;

public class CustomerController {
    private static Customer customer;

    public CustomerController(Customer customer) {
        this.customer = customer;
    }

    public static Customer getCustomer() {
        return customer;
    }

    public static void setCustomer(Customer customer) {
        CustomerController.customer = customer;
    }

    public static void showCustomerInfo() throws ExceptionsLibrary.NoAccountException {
        Gson gson = new GsonBuilder().serializeNulls().create();
        if (getCustomer() == null) {
            ClientHandler.sendObject(new ExceptionsLibrary.NoAccountException());
        }
        Customer customer = (Customer) GetDataFromDatabaseServerSide.getAccount(ClientHandler.receiveMessage());
        setCustomer(customer);
        String data = gson.toJson(customer);
        ClientHandler.sendMessage(data);
    }

    public static void editCustomerInfo() throws ExceptionsLibrary.NoAccountException, ExceptionsLibrary.NoFeatureWithThisName, ExceptionsLibrary.ChangeUsernameException {
        HashMap<String, String> dataToEdit = (HashMap<String, String>) ClientHandler.receiveObject();
        Customer customer = (Customer) GetDataFromDatabaseServerSide.getAccount(getCustomer().getUsername());
        for (String i : dataToEdit.keySet()) {
            try {
                if (i.equals("username")) {
                    ClientHandler.sendObject(new ExceptionsLibrary.ChangeUsernameException());
                }
                Field field = Customer.class.getSuperclass().getDeclaredField(i);
                field.setAccessible(true);
                field.set(customer, dataToEdit.get(i));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new ExceptionsLibrary.NoFeatureWithThisName();
            }
        }
        Gson gson = new GsonBuilder().serializeNulls().create();
        setCustomer(customer);
        SetDataToDatabase.setAccount(getCustomer());
        ClientHandler.sendMessage("Success!");
    }

    public static void showCustomerBalance() {
        ClientHandler.sendObject(getCustomer().getCredit()); ;
    }

    public static void showDiscountCodes() {
        setCustomer((Customer) ClientHandler.receiveObject());
        ClientHandler.sendObject(getCustomer().getSaleCodes());
    }

    public static void showCustomerLogs() {

        setCustomer((Customer) ClientHandler.receiveObject());
        ClientHandler.sendObject(getCustomer().getCustomerLog());
    }

    public static void showCustomerLogDetail() throws ExceptionsLibrary.NoLogException {
        int logId = Integer.parseInt(ClientHandler.receiveMessage());
        for (BuyLog i : getCustomer().getCustomerLog()) {
            if (i.getLogId() == logId) {
                ClientHandler.sendObject(i);
                return;
            }
        }
        ClientHandler.sendObject(new ExceptionsLibrary.NoLogException());
    }

    public static void rateProduct() throws ExceptionsLibrary.NoProductException {
        Object[] receivedData = (Object[]) ClientHandler.receiveObject();
        int productId = (int) receivedData[0];
        double rateScore = (double) receivedData[1];
        Product product = GetDataFromDatabaseServerSide.getProduct(productId);
        if (product != null) {
            Rate rate = new Rate(getCustomer(), product, rateScore);
            product.getRates().add(rate);
            SetDataToDatabase.setProduct(product);
        } else {
            ClientHandler.sendObject(new ExceptionsLibrary.NoProductException());
            return;
        }

        ClientHandler.sendMessage("Success!");
    }


}
