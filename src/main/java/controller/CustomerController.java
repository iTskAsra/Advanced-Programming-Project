package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
        CustomerController.customer = customer;
    }

    public static String showCustomerInfo() throws ExceptionsLibrary.NoAccountException {
        Gson gson = new GsonBuilder().serializeNulls().create();
        if (getCustomer() == null) {
            throw new ExceptionsLibrary.NoAccountException();
        }
        Customer customer = (Customer) GetDataFromDatabase.getAccount(getCustomer().getUsername());
        setCustomer(customer);
        String data = gson.toJson(customer);
        return data;
    }

    public static void editCustomerInfo(HashMap<String, String> dataToEdit) throws ExceptionsLibrary.NoAccountException, ExceptionsLibrary.NoFeatureWithThisName, ExceptionsLibrary.ChangeUsernameException {
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
    }

    public static double showCustomerBalance() {
        return getCustomer().getCredit();
    }

    public static ArrayList<Sale> showDiscountCodes() {
        return getCustomer().getSaleCodes();
    }

    public static ArrayList<BuyLog> showCustomerLogs() {
        try {
            setCustomer((Customer) GetDataFromDatabase.getAccount(getCustomer().getUsername()));
        } catch (ExceptionsLibrary.NoAccountException e) {
            e.printStackTrace();
        }
        return getCustomer().getCustomerLog();
    }

    public static BuyLog showCustomerLogDetail(int logId) throws ExceptionsLibrary.NoLogException {
        for (BuyLog i : getCustomer().getCustomerLog()) {
            if (i.getLogId() == logId) {
                return i;
            }
        }
        throw new ExceptionsLibrary.NoLogException();
    }

    public static void rateProduct(int productId, double rateScore) throws ExceptionsLibrary.NoProductException {
        Product product = GetDataFromDatabase.getProduct(productId);
        if (product != null) {
            Rate rate = new Rate(getCustomer(), product, rateScore);
            product.getRates().add(rate);
            SetDataToDatabase.setProduct(product);
        } else {
            throw new ExceptionsLibrary.NoProductException();
        }
    }


}
