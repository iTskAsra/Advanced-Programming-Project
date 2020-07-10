package Client.controller;
import Client.Client;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.ExceptionsLibrary;
import controller.GetDataFromDatabase;
import controller.SetDataToDatabase;
import model.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
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
        Client.controller.CustomerController.customer = customer;
    }

    public static String showCustomerInfo() throws ExceptionsLibrary.NoAccountException {

        if (getCustomer() == null) {
            throw new ExceptionsLibrary.NoAccountException();
        }

        String func = "Show Customer Info";
        Client.sendMessage(func);

        String customerUsername = getCustomer().getUsername();
        Client.sendMessage(customerUsername);

        Object data = Client.receiveObject();

        if(data instanceof Exception){
            throw new ExceptionsLibrary.NoAccountException();
        }
        else{
            return String.valueOf(data);
        }
//        Gson gson = new GsonBuilder().serializeNulls().create();
//        if (getCustomer() == null) {
//            throw new ExceptionsLibrary.NoAccountException();
//        }
//        Customer customer = (Customer) GetDataFromDatabase.getAccount(getCustomer().getUsername());
//        setCustomer(customer);
//        String data = gson.toJson(customer);
//        return data;
    }

    public static void editCustomerInfo(HashMap<String, String> dataToEdit) throws ExceptionsLibrary.NoAccountException, ExceptionsLibrary.NoFeatureWithThisName, ExceptionsLibrary.ChangeUsernameException {

        for (String i : dataToEdit.keySet()) {
            if (i.equals("username")) {
                throw new ExceptionsLibrary.ChangeUsernameException();
            }
            String func = "Edit Customer Info";
            Client.sendMessage(func);

            Object[] toSend = new Object[2];
            toSend[0] = getCustomer().getUsername();
            toSend[1] = dataToEdit;
            Client.sendObject(toSend);
            Object response = Client.receiveObject();

            if (response instanceof ExceptionsLibrary.NoFeatureWithThisName)
                throw new ExceptionsLibrary.NoFeatureWithThisName();
            if (response instanceof ExceptionsLibrary.NoAccountException)
                throw new ExceptionsLibrary.NoAccountException();
            else
                return;
        }
//        Customer customer = (Customer) GetDataFromDatabase.getAccount(getCustomer().getUsername());
//        for (String i : dataToEdit.keySet()) {
//            try {
//                if (i.equals("username")) {
//                    throw new ExceptionsLibrary.ChangeUsernameException();
//                }
//                Field field = Customer.class.getSuperclass().getDeclaredField(i);
//                field.setAccessible(true);
//                field.set(customer, dataToEdit.get(i));
//            } catch (NoSuchFieldException | IllegalAccessException e) {
//                throw new ExceptionsLibrary.NoFeatureWithThisName();
//            }
//        }
//        Gson gson = new GsonBuilder().serializeNulls().create();
//        setCustomer(customer);
//        SetDataToDatabase.setAccount(getCustomer());
    }

    public static double showCustomerBalance() {


        String func = "Show Customer Balance";
        Client.sendMessage(func);

        Client.sendObject(getCustomer());

        return (double) Client.receiveObject();
    }

    public static ArrayList<Sale> showDiscountCodes() {

        String func = "Show Customer Discount Codes";
        Client.sendMessage(func);

        Client.sendObject(getCustomer());

        return (ArrayList<Sale>) Client.receiveObject();
    }

    public static ArrayList<BuyLog> showCustomerLogs() throws ExceptionsLibrary.NoAccountException{

        String func = "Show Customer Logs";
        Client.sendMessage(func);

        Client.sendObject(getCustomer());
        Object response = Client.receiveObject();

        if(response instanceof ExceptionsLibrary.NoAccountException)
            throw new ExceptionsLibrary.NoAccountException();
        else
            return (ArrayList<BuyLog>) response;

//        try {
//            setCustomer((Customer) GetDataFromDatabase.getAccount(getCustomer().getUsername()));
//        } catch (ExceptionsLibrary.NoAccountException e) {
//            e.printStackTrace();
//        }
//        return getCustomer().getCustomerLog();
    }

    public static BuyLog showCustomerLogDetail(int logId) throws ExceptionsLibrary.NoLogException {

        String func = "Show Customer Log Details";
        Client.sendMessage(func);

        Client.sendMessage(String.valueOf(logId));
        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoLogException)
            throw new ExceptionsLibrary.NoLogException();
        else
            return (BuyLog) response;
//        for (BuyLog i : getCustomer().getCustomerLog()) {
//            if (i.getLogId() == logId) {
//                return i;
//            }
//        }
//        throw new ExceptionsLibrary.NoLogException();
    }

    public static void rateProduct(int productId, double rateScore) throws ExceptionsLibrary.NoProductException {

        String func = "Rate Product";
        Client.sendMessage(func);

        Object[] toSend = new Object[2];
        toSend[0] = productId;
        toSend[1] = rateScore;

        Object response = Client.receiveObject();
        if (response instanceof ExceptionsLibrary.NoProductException)
            throw new ExceptionsLibrary.NoProductException();
        else
            return;

//        Product product = GetDataFromDatabase.getProduct(productId);
//        if (product != null) {
//            Rate rate = new Rate(getCustomer(), product, rateScore);
//            product.getRates().add(rate);
//            SetDataToDatabase.setProduct(product);
//        } else {
//            throw new ExceptionsLibrary.NoProductException();
//        }
    }
}
