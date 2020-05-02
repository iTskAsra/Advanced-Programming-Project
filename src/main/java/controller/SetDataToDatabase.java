package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Account;
import model.Product;
import model.Sale;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class SetDataToDatabase {
    public static void setProduct(Product product) {
        String path = "src/main/resources/Products"+ product.getProductId() + ".json";
        File file = new File(path);
        try {
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new GsonBuilder().serializeNulls().create();
            String data = gson.toJson(product);
            fileWriter.write(data);
            fileWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setAccount(Account account) {
        String path = "src/main/resources/Accounts/" + account.getRole() + "/" + account.getUsername() + ".json";
        File file = new File(path);
        try {
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new GsonBuilder().serializeNulls().create();
            String data = gson.toJson(account);
            fileWriter.write(data);
            fileWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setSale(Sale sale) {
        String path = "src/main/resources/Sales"+ sale.getSaleCode() + ".json";
        File file = new File(path);
        try {
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new GsonBuilder().serializeNulls().create();
            String data = gson.toJson(sale);
            fileWriter.write(data);
            fileWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
