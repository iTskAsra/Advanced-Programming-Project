package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;
import view.MessagesLibrary;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class GetDataFromDatabase {

    public static void setResources() {
        File folder9 = new File("Resources");
        folder9.mkdir();
        File folder = new File("Resources/Accounts");
        folder.mkdir();
        File folder1 = new File("Resources/Accounts/Customer");
        folder1.mkdir();
        File folder2 = new File("Resources/Accounts/Seller");
        folder2.mkdir();
        File folder3 = new File("Resources/Accounts/Admin");
        folder3.mkdir();
        File folder4 = new File("Resources/Products");
        folder4.mkdir();
        File folder5 = new File("Resources/Requests");
        folder5.mkdir();
        File folder6 = new File("Resources/Offs");
        folder6.mkdir();
        File folder7 = new File("Resources/Sales");
        folder7.mkdir();
        File folder8 = new File("Resources/Category");
        folder8.mkdir();
    }

    public static Account getAccount(String username) throws ExceptionsLibrary.NoAccountException {
        String pathCustomer = "Resources/Accounts/Customer/" + username + ".json";
        String pathAdmin = "Resources/Accounts/Admin/" + username + ".json";
        String pathSeller = "Resources/Accounts/Seller/" + username + ".json";
        File fileCustomer = new File(pathCustomer);
        File fileAdmin = new File(pathAdmin);
        File fileSeller = new File(pathSeller);
        if (fileCustomer.exists() && !fileAdmin.exists() && !fileSeller.exists()) {
            try {
                Scanner scanner;
                scanner = new Scanner(fileCustomer);
                scanner.useDelimiter("\\z");
                String fileData = scanner.next();
                Gson gson = new GsonBuilder().serializeNulls().create();
                Customer account = gson.fromJson(fileData, Customer.class);
                scanner.close();
                return account;
            } catch (FileNotFoundException e) {
                throw new ExceptionsLibrary.NoAccountException();
            }

        } else if (!fileCustomer.exists() && fileAdmin.exists() && !fileSeller.exists()) {
            try {
                Scanner scanner;
                scanner = new Scanner(fileAdmin);
                scanner.useDelimiter("\\z");
                String fileData = scanner.next();
                Gson gson = new GsonBuilder().serializeNulls().create();
                Admin account = gson.fromJson(fileData, Admin.class);
                scanner.close();
                return account;
            } catch (FileNotFoundException e) {
                throw new ExceptionsLibrary.NoAccountException();
            }
        } else if (!fileCustomer.exists() && !fileAdmin.exists() && fileSeller.exists()) {
            try {
                Scanner scanner;
                scanner = new Scanner(fileSeller);
                scanner.useDelimiter("\\z");
                String fileData = "";
                fileData = new String(Files.readAllBytes(Paths.get(pathSeller)));
                Gson gson = new GsonBuilder().serializeNulls().create();
                Seller account = gson.fromJson(fileData, Seller.class);
                scanner.close();
                return account;
            } catch (FileNotFoundException e) {
                throw new ExceptionsLibrary.NoAccountException();
            } catch (IOException e) {
                throw new ExceptionsLibrary.NoAccountException();
            }
        } else if (!fileCustomer.exists() && !fileAdmin.exists() && !fileSeller.exists()) {
            throw new ExceptionsLibrary.NoAccountException();
        }
        return null;
    }

    public static Product getProduct(int productId) throws ExceptionsLibrary.NoProductException {
        String productPath = "Resources/Products/" + (productId) + ".json";
        File fileProduct = new File(productPath);
        try {
            Scanner scanner;
            scanner = new Scanner(fileProduct);
            scanner.useDelimiter("\\z");
            String fileData = "";
            fileData = new String(Files.readAllBytes(Paths.get(productPath)));
            Gson gson = new GsonBuilder().serializeNulls().create();
            Product product = gson.fromJson(fileData, Product.class);
            scanner.close();
            return product;
        } catch (FileNotFoundException e) {
            throw new ExceptionsLibrary.NoProductException();
        } catch (IOException e) {
            throw new ExceptionsLibrary.NoProductException();
        }
    }


    public static Off getOff(int offId) throws ExceptionsLibrary.NoOffException {
        if (!Files.exists(Paths.get("Resources/Offs"))) {
            File folder = new File("Resources/Offs");
            folder.mkdir();
        }
        String offPath = "Resources/Offs/" + (offId) + ".json";
        File fileProduct = new File(offPath);
        try {
            Scanner scanner;
            scanner = new Scanner(fileProduct);
            scanner.useDelimiter("\\z");
            String fileData = "";
            fileData = new String(Files.readAllBytes(Paths.get(offPath)));
            Gson gson = new GsonBuilder().serializeNulls().create();
            Off off = gson.fromJson(fileData, Off.class);
            scanner.close();
            return off;
        } catch (FileNotFoundException e) {
            throw new ExceptionsLibrary.NoOffException();
        } catch (IOException e) {
            throw new ExceptionsLibrary.NoOffException();
        }
    }

    public static Request getRequest(int requestId) throws ExceptionsLibrary.NoRequestException {
        if (!Files.exists(Paths.get("Resources/Requests"))) {
            File folder = new File("Resources/Requests");
            folder.mkdir();
        }
        String requestPath = "Resources/Requests/" + (requestId) + ".json";
        File fileProduct = new File(requestPath);
        try {
            Scanner scanner;
            scanner = new Scanner(fileProduct);
            scanner.useDelimiter("\\z");
            //TODO replace readAllBytes with scanner
            //String fileData = scanner.next();
            String fileData = "";
            fileData = new String(Files.readAllBytes(Paths.get(requestPath)));
            Gson gson = new GsonBuilder().serializeNulls().create();
            Request request = gson.fromJson(fileData, Request.class);
            scanner.close();
            return request;
        } catch (FileNotFoundException e) {
            throw new ExceptionsLibrary.NoRequestException();
        } catch (IOException e) {
            throw new ExceptionsLibrary.NoRequestException();
        }
    }

    public static Sale getSale(String saleCode) throws ExceptionsLibrary.NoSaleException {
        if (!Files.exists(Paths.get("Resources/Sales"))) {
            File folder = new File("Resources/Sales");
            folder.mkdir();
        }
        String salePath = "Resources/Sales/" + (saleCode) + ".json";
        File fileProduct = new File(salePath);
        try {
            Scanner scanner;
            scanner = new Scanner(fileProduct);
            scanner.useDelimiter("\\z");
            String fileData = scanner.next();
            Gson gson = new GsonBuilder().serializeNulls().create();
            Sale sale = gson.fromJson(fileData, Sale.class);
            scanner.close();
            return sale;
        } catch (FileNotFoundException e) {
            throw new ExceptionsLibrary.NoSaleException();
        }
    }

    public static Category getCategory(String categoryName) throws ExceptionsLibrary.NoCategoryException {
        if (!Files.exists(Paths.get("Resources/Category"))) {
            File folder = new File("Resources/Category");
            folder.mkdir();
        }
        String categoryPath = "Resources/Category/" + (categoryName) + ".json";
        File fileCategory = new File(categoryPath);
        try {
            Scanner scanner;
            scanner = new Scanner(fileCategory);
            scanner.useDelimiter("\\z");
            String fileData = scanner.next();
            Gson gson = new GsonBuilder().serializeNulls().create();
            Category category = gson.fromJson(fileData, Category.class);
            scanner.close();
            return category;
        } catch (FileNotFoundException e) {
            throw new ExceptionsLibrary.NoCategoryException();
        }
    }

    public static boolean checkIfAnyAdminExists() {
        return new File("Resources/Accounts/Admin").listFiles().length==0;
    }

    public static ArrayList<Seller> findSellersFromProductId(int productId) {
        File folder = new File("Resources/Accounts/Seller");
        ArrayList<Seller> sellers =new ArrayList<>();
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.getName().endsWith(".json")) {
                    return true;
                }
                return false;
            }
        };
        for (File i : folder.listFiles(fileFilter)) {
            try {
                Scanner scanner;
                scanner = new Scanner(i);
                scanner.useDelimiter("\\z");
                String fileData = "";
                fileData = new String(Files.readAllBytes(Paths.get(i.getPath())));
                Gson gson = new GsonBuilder().serializeNulls().create();
                Seller seller = gson.fromJson(fileData, Seller.class);
                for (Product j : seller.getSellerProducts()){
                    if (j.getProductId() == productId){
                        sellers.add(seller);
                    }
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sellers;
    }
}
