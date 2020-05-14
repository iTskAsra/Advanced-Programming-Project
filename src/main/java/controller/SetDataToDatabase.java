package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;

import java.io.*;
import java.util.Iterator;

public class SetDataToDatabase {
    public static void setProduct(Product product) {
        String path = "Resources/Products"+ product.getProductId() + ".json";
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
        String path = "Resources/Accounts/" + account.getRole() + "/" + account.getUsername() + ".json";
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
        String path = "Resources/Sales/"+ sale.getSaleCode() + ".json";
        File file = new File(path);
        try {
            if (!file.exists()){
                file.createNewFile();
            }
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

    public static void setOff(Off off) {
        String path = "Resources/Offs/"+ off.getOffId() + ".json";
        File file = new File(path);
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new GsonBuilder().serializeNulls().create();
            String data = gson.toJson(off);
            fileWriter.write(data);
            fileWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateSellerAndOffsOfProduct(Product product,int code) throws ExceptionsLibrary.NoAccountException {
        File sellers = new File("Resources/Accounts/Seller");
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.getName().endsWith(".json")) {
                    return true;
                }
                return false;
            }
        };
        if (code == 0) {
            for (File i : sellers.listFiles(fileFilter)) {
                String sellerName = i.getName().replace(".json", "");
                Seller seller = (Seller) GetDataFromDatabase.getAccount(sellerName);
                for (Off j : seller.getSellerOffs()) {
                    Iterator iterator = j.getOffProducts().iterator();
                    while (iterator.hasNext()) {
                        Product tempProduct = (Product) iterator.next();
                        if (tempProduct.getProductId() == product.getProductId()) {
                            iterator.remove();
                        }
                    }
                    j.getOffProducts().add(product);
                    SetDataToDatabase.setOff(j);
                }
                SetDataToDatabase.setAccount(seller);
            }
        }
        else if (code == 1){
            for (File i : sellers.listFiles(fileFilter)) {
                String sellerName = i.getName().replace(".json", "");
                Seller seller = (Seller) GetDataFromDatabase.getAccount(sellerName);
                Iterator iterator = seller.getSellerProducts().iterator();
                while (iterator.hasNext()) {
                    Product tempProduct = (Product) iterator.next();
                    if (tempProduct.getProductId() == product.getProductId()) {
                        iterator.remove();
                    }
                }
                for (Off j : seller.getSellerOffs()) {
                    iterator = j.getOffProducts().iterator();
                    while (iterator.hasNext()) {
                        Product tempProduct = (Product) iterator.next();
                        if (tempProduct.getProductId() == product.getProductId()) {
                            iterator.remove();
                        }
                    }
                    SetDataToDatabase.setOff(j);
                }
                SetDataToDatabase.setAccount(seller);
            }
        }
    }
}
