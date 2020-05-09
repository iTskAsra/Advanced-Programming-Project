package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.BuyLog;
import model.Customer;
import model.Product;
import model.Rate;
import view.MessagesLibrary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    public static String showCustomerInfo() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        if (getCustomer() == null) {
            MessagesLibrary.errorLibrary(4);
            return null;
        }
        Customer customer = (Customer) GetDataFromDatabase.getAccount(getCustomer().getUsername());
        setCustomer(customer);
        String data = gson.toJson(customer);
        return data;
    }

    public static void editCustomerInfo(HashMap<String, String> dataToEdit) {
        Customer customer = (Customer) GetDataFromDatabase.getAccount(getCustomer().getUsername());
        for (String i : dataToEdit.keySet()) {
            try {
                Field field = Customer.class.getSuperclass().getDeclaredField(i);
                field.setAccessible(true);
                field.set(customer, dataToEdit.get(i));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                MessagesLibrary.errorLibrary(5);
                e.printStackTrace();
            }
        }
        Gson gson = new GsonBuilder().serializeNulls().create();
        setCustomer(customer);
        String editedDetails = gson.toJson(customer);
        try {
            String path = "Resources/Accounts/Customer/" + getCustomer().getUsername() + ".json";
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(editedDetails);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double showCustomerBalance() {
        return getCustomer().getCredit();
    }

    public static String showDiscountCodes() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(getCustomer().getSaleCodes());
    }

    public static String showCustomerLogs() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(getCustomer().getCustomerLog());
    }

    public static String showCustomerLogDetail(int logId) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        for (BuyLog i : getCustomer().getCustomerLog()) {
            if (i.getLogId() == logId) {
                return gson.toJson(i);
            }
        }
        return null;
    }

    public static void rateProduct(int productId, double rateScore) {
        Product product = GetDataFromDatabase.getProduct(productId);
        if (product != null) {
            Rate rate = new Rate(getCustomer(), product, rateScore);
            product.getRates().add(rate);
            Gson gson = new GsonBuilder().serializeNulls().create();
            String ratedProduct = gson.toJson(product);
            String path = "Resources/Products/" + Integer.toString(productId) + ".json";
            File file = new File(path);
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(ratedProduct);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        }
    }


}
