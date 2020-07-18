package Server.ServerController;

import Client.Client;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;

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
            Client.sendObject(new ExceptionsLibrary.NoAccountException());
        }
        Customer customer = (Customer) GetDataFromDatabase.getAccount(Client.receiveMessage());
        setCustomer(customer);
        String data = gson.toJson(customer);
        Client.sendMessage(data);
    }

    public static void editCustomerInfo() throws ExceptionsLibrary.NoAccountException, ExceptionsLibrary.NoFeatureWithThisName, ExceptionsLibrary.ChangeUsernameException {
        HashMap<String, String> dataToEdit = (HashMap<String, String>) Client.receiveObject();
        Customer customer = (Customer) GetDataFromDatabase.getAccount(getCustomer().getUsername());
        for (String i : dataToEdit.keySet()) {
            try {
                if (i.equals("username")) {
                    throw new ExceptionsLibrary.ChangeUsernameException();
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
        Client.sendMessage("Success!");
    }

    public static void showCustomerBalance() {
        Client.sendObject(getCustomer().getCredit()); ;
    }

    public static void showDiscountCodes() {
        Client.sendObject(getCustomer().getSaleCodes());
    }

    public static void showCustomerLogs() {
        try {
            setCustomer((Customer) GetDataFromDatabase.getAccount(getCustomer().getUsername()));
        } catch (ExceptionsLibrary.NoAccountException e) {
            e.printStackTrace();
        }
        Client.sendObject(getCustomer().getCustomerLog());
    }

    public static void showCustomerLogDetail() throws ExceptionsLibrary.NoLogException {
        int logId = Integer.parseInt(Client.receiveMessage());
        for (BuyLog i : getCustomer().getCustomerLog()) {
            if (i.getLogId() == logId) {
                Client.sendObject(i);
            }
        }
        Client.sendObject(new ExceptionsLibrary.NoLogException());
    }

    public static void rateProduct() throws ExceptionsLibrary.NoProductException {
        Object[] receivedData = (Object[]) Client.receiveObject();
        int productId = (int) receivedData[0];
        double rateScore = (double) receivedData[1];
        Product product = GetDataFromDatabase.getProduct(productId);
        if (product != null) {
            Rate rate = new Rate(getCustomer(), product, rateScore);
            product.getRates().add(rate);
            SetDataToDatabase.setProduct(product);
        } else {
            Client.sendObject(new ExceptionsLibrary.NoProductException());
        }
    }


}
