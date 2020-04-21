package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;
import view.MessagesLibrary;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class GetDataFromDatabase {
    public static Account getAccount(String username) {
        String pathCustomer = "src/main/resources/Accounts/Customer/" + username + ".json";
        String pathAdmin = "src/main/resources/Accounts/Admin/" + username + ".json";
        String pathSeller = "src/main/resources/Accounts/Seller/" + username + ".json";
        File fileCustomer = new File(pathCustomer);
        File fileAdmin = new File(pathAdmin);
        File fileSeller = new File(pathSeller);
        if (fileCustomer.exists() && !fileAdmin.exists() && !fileSeller.exists()) {
            try {
                Scanner scanner;
                scanner = new Scanner(fileCustomer);
                scanner.useDelimiter("\\z");
                String fileData = scanner.next();
                Gson gson = new Gson();
                Customer account = gson.fromJson(fileData, Customer.class);
                scanner.close();
                return account;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (!fileCustomer.exists() && fileAdmin.exists() && !fileSeller.exists()) {
            try {
                Scanner scanner;
                scanner = new Scanner(fileAdmin);
                scanner.useDelimiter("\\z");
                String fileData = scanner.next();
                Gson gson = new Gson();
                Admin account = gson.fromJson(fileData, Admin.class);
                scanner.close();
                return account;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (!fileCustomer.exists() && !fileAdmin.exists() && fileSeller.exists()) {
            try {
                Scanner scanner;
                scanner = new Scanner(fileSeller);
                scanner.useDelimiter("\\z");
                String fileData = scanner.next();
                Gson gson = new Gson();
                Seller account = gson.fromJson(fileData, Seller.class);
                scanner.close();
                return account;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (!fileCustomer.exists() && !fileAdmin.exists() && !fileSeller.exists()) {
            view.MessagesLibrary.errorLibrary(1);
            return null;
        }
        return null;
    }

    public static Product getProduct(int productId) {
        if (!Files.exists(Paths.get("src/main/resources/Products"))) {
            File folder = new File("src/main/resources/Products");
            folder.mkdir();
        }
        String productPath = "src/main/resources/Products/" + (productId) + ".json";
        File fileProduct = new File(productPath);
        try {
            Scanner scanner;
            scanner = new Scanner(fileProduct);
            scanner.useDelimiter("\\z");
            String fileData = scanner.next();
            Gson gson = new Gson();
            Product product = gson.fromJson(fileData, Product.class);
            scanner.close();
            return product;
        } catch (FileNotFoundException e) {
            MessagesLibrary.errorLibrary(6);
        }
        return null;
    }


    public static Off getOff(int offId) {
        if (!Files.exists(Paths.get("src/main/resources/Offs"))) {
            File folder = new File("src/main/resources/Offs");
            folder.mkdir();
        }
        String offPath = "src/main/resources/Offs/" + (offId) + ".json";
        File fileProduct = new File(offPath);
        try {
            Scanner scanner;
            scanner = new Scanner(fileProduct);
            scanner.useDelimiter("\\z");
            String fileData = scanner.next();
            Gson gson = new Gson();
            Off off = gson.fromJson(fileData, Off.class);
            scanner.close();
            return off;
        } catch (FileNotFoundException e) {
            MessagesLibrary.errorLibrary(6);
        }
        return null;
    }

    public static Request getRequest(int requestId) {
        if (!Files.exists(Paths.get("src/main/resources/Requests"))) {
            File folder = new File("src/main/resources/Requests");
            folder.mkdir();
        }
        String requestPath = "src/main/resources/Requests/" + (requestId) + ".json";
        File fileProduct = new File(requestPath);
        try {
            Scanner scanner;
            scanner = new Scanner(fileProduct);
            scanner.useDelimiter("\\z");
            String fileData = scanner.next();
            Gson gson = new Gson();
            Request request = gson.fromJson(fileData, Request.class);
            scanner.close();
            return request;
        } catch (FileNotFoundException e) {
            MessagesLibrary.errorLibrary(6);
        }
        return null;
    }

    public static Sale getSale(String saleCode) {
        if (!Files.exists(Paths.get("src/main/resources/Sales"))) {
            File folder = new File("src/main/resources/Sales");
            folder.mkdir();
        }
        String salePath = "src/main/resources/Sale/" + (saleCode) + ".json";
        File fileProduct = new File(salePath);
        try {
            Scanner scanner;
            scanner = new Scanner(fileProduct);
            scanner.useDelimiter("\\z");
            String fileData = scanner.next();
            Gson gson = new Gson();
            Sale sale = gson.fromJson(fileData, Sale.class);
            scanner.close();
            return sale;
        } catch (FileNotFoundException e) {
            MessagesLibrary.errorLibrary(11);
        }
        return null;
    }

    public static Category getCategory(String categoryName) {
        if (!Files.exists(Paths.get("src/main/resources/Category"))) {
            File folder = new File("src/main/resources/Category");
            folder.mkdir();
        }
        String categoryPath = "src/main/resources/Category/" + (categoryName) + ".json";
        File fileProduct = new File(categoryPath);
        try {
            Scanner scanner;
            scanner = new Scanner(fileProduct);
            scanner.useDelimiter("\\z");
            String fileData = scanner.next();
            Gson gson = new GsonBuilder().serializeNulls().create();
            Category category = gson.fromJson(fileData, Category.class);
            scanner.close();
            return category;
        } catch (FileNotFoundException e) {
            MessagesLibrary.errorLibrary(13);//No category with this name
        }
        return null;
    }

}
