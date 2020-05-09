package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;
import view.MessagesLibrary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Random;

public class SellerController {
    private static Seller seller;

    public SellerController(Seller seller) {
        this.seller = seller;
    }

    public static Seller getSeller() {
        return seller;
    }

    public static void setSeller(Seller seller) {
        SellerController.seller = seller;
    }

    //TODO Check to update seller products,offs,info and exceptions check

    public static String showSellerInfo() throws ExceptionsLibrary.NoAccountException {
        Gson gson = new GsonBuilder().serializeNulls().create();
        if (getSeller() == null) {
            MessagesLibrary.errorLibrary(4);
            return null;
        }
        Seller seller = (Seller) GetDataFromDatabase.getAccount(getSeller().getUsername());
        setSeller(seller);
        String data = gson.toJson(seller);
        return data;
    }

    public static void editSellerInfo(HashMap<String, String> dataToEdit) throws ExceptionsLibrary.NoAccountException {
        Seller seller = (Seller) GetDataFromDatabase.getAccount(getSeller().getUsername());
        for (String i : dataToEdit.keySet()) {
            try {
                try {
                    Field field = Seller.class.getSuperclass().getDeclaredField(i);
                    field.setAccessible(true);
                    field.set(seller, dataToEdit.get(i));
                } catch (NoSuchFieldException e) {
                    Field field = Seller.class.getDeclaredField(i);
                    field.setAccessible(true);
                    field.set(seller, dataToEdit.get(i));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                MessagesLibrary.errorLibrary(5);
                e.printStackTrace();
            }
        }
        Gson gson = new GsonBuilder().serializeNulls().create();
        setSeller(seller);
        String editedDetails = gson.toJson(seller);
        try {
            String path = "Resources/Accounts/Seller/" + getSeller().getUsername() + ".json";
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(editedDetails);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String showSellerCompany() {
        return getSeller().getCompanyName();
    }

    public static String showSellerProducts() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String data = gson.toJson(getSeller().getSellerProducts());
        return data;
    }

    public static String showSellerLogs() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String data = gson.toJson(getSeller().getSellerLogs());
        return data;
    }

    public static void editProductRequest(int productId, HashMap<String, String> dataToEdit) throws ExceptionsLibrary.NoProductException {
        Product product = GetDataFromDatabase.getProduct(productId);
        if (product != null) {
            for (String i : dataToEdit.keySet()) {
                try {
                    Field field = Product.class.getDeclaredField(i);
                    if (i.equals("price")) {
                        field.setAccessible(true);
                        field.set(product, Double.parseDouble(dataToEdit.get(i)));
                    } else if (i.equals("availability")) {
                        field.setAccessible(true);
                        field.set(product, Integer.parseInt(dataToEdit.get(i)));
                    } else {
                        field.setAccessible(true);
                        field.set(product, dataToEdit.get(i));
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    MessagesLibrary.errorLibrary(5);
                    e.printStackTrace();
                }
            }
            Gson gsonProduct = new GsonBuilder().serializeNulls().create();
            product.setProductCondition(ProductOrOffCondition.PENDING_TO_EDIT);
            String editedProduct = gsonProduct.toJson(product);
            Request request = new Request(editedProduct, RequestType.EDIT_PODUCT, RequestOrCommentCondition.PENDING_TO_ACCEPT,getSeller());
            Gson gsonRequest = new GsonBuilder().serializeNulls().create();
            String requestDetails = gsonRequest.toJson(request);
            if (!Files.exists(Paths.get("Resources/Requests"))) {
                File folder = new File("Resources/Requests");
                folder.mkdir();
            }
            try {
                String path = "Resources/Requests/" + request.getRequestId() + ".json";
                FileWriter fileWriter = new FileWriter(path);
                fileWriter.write(requestDetails);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            MessagesLibrary.errorLibrary(6);
        }
    }

    public static void removeProduct(int productId) throws ExceptionsLibrary.NoProductException {
        Product product = GetDataFromDatabase.getProduct(productId);
        if (product != null) {
            Gson gsonProduct = new GsonBuilder().serializeNulls().create();
            String path = "Resources/Products/" + product.getProductId() + ".json";
            getSeller().getSellerProducts().remove(product);
            File file = new File(path);
            if (file.delete()) {
                MessagesLibrary.messagesLibrary(3);
            }
            try {
                String sellerPath = "Resources/Accounts/Seller/" + getSeller().getUsername() + ".json";
                FileWriter fileWriter = new FileWriter(sellerPath);
                Gson gson = new GsonBuilder().serializeNulls().create();
                String editedSeller = gson.toJson(getSeller());
                fileWriter.write(editedSeller);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            MessagesLibrary.errorLibrary(6);
        }
    }

    public static void addProductRequest(String productDetails) {
        Gson gsonProduct = new GsonBuilder().serializeNulls().create();
        Product product = gsonProduct.fromJson(productDetails,Product.class);
        product.setProductCondition(ProductOrOffCondition.PENDING_TO_CREATE);
        Request request = new Request(productDetails, RequestType.ADD_PRODUCT, RequestOrCommentCondition.PENDING_TO_ACCEPT,getSeller());
        Gson gsonRequest = new GsonBuilder().serializeNulls().create();
        if (!Files.exists(Paths.get("Resources/Requests"))) {
            File folder = new File("Resources/Requests");
            folder.mkdir();
        }
        try {
            String path = "Resources/Requests/" + request.getRequestId() + ".json";
            while (Files.exists(Paths.get(path))) {
                Random random = new Random();
                request.setRequestId(random.nextInt(10000));
                path = "Resources/Requests/" + request.getRequestId() + ".json";
            }
            String requestDetails = gsonRequest.toJson(request);
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(requestDetails);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void editOffRequest(int offId, HashMap<String, String> dataToEdit) throws ExceptionsLibrary.NoOffException {
        Off off = GetDataFromDatabase.getOff(offId);
        if (off != null) {
            for (Off i : getSeller().getSellerOffs()){
                if (i.getOffId() == off.getOffId()){
                    for (String s : dataToEdit.keySet()) {
                        try {
                            Field field = Off.class.getDeclaredField(s);
                            if (s.equals("offAmount")) {
                                field.setAccessible(true);
                                field.set(off, Double.parseDouble(dataToEdit.get(s)));
                            } else {
                                field.setAccessible(true);
                                field.set(off, dataToEdit.get(s));
                            }
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            MessagesLibrary.errorLibrary(5);
                            e.printStackTrace();
                        }
                    }
                    off.setOffCondition(ProductOrOffCondition.PENDING_TO_EDIT);
                    Gson gsonOff = new GsonBuilder().serializeNulls().create();
                    String editedOff = gsonOff.toJson(off);
                    Request request = new Request(editedOff, RequestType.EDIT_OFF, RequestOrCommentCondition.PENDING_TO_ACCEPT,getSeller());
                    Gson gsonRequest = new GsonBuilder().serializeNulls().create();
                    String requestDetails = gsonRequest.toJson(request);
                    if (!Files.exists(Paths.get("Resources/Requests"))) {
                        File folder = new File("Resources/Requests");
                        folder.mkdir();
                    }
                    try {
                        String path = "Resources/Requests/" + request.getRequestId() + ".json";
                        FileWriter fileWriter = new FileWriter(path);
                        fileWriter.write(requestDetails);
                        fileWriter.close();
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            MessagesLibrary.errorLibrary(7);
        } else {
            MessagesLibrary.errorLibrary(7);
        }
    }

    public static void addOffRequest(String newOffDetails) {
        Gson gsonOff = new GsonBuilder().serializeNulls().create();
        Off off = gsonOff.fromJson(newOffDetails,Off.class);
        off.setOffCondition(ProductOrOffCondition.PENDING_TO_CREATE);
        String offDetails = gsonOff.toJson(off);
        Request request = new Request(offDetails, RequestType.ADD_OFF, RequestOrCommentCondition.PENDING_TO_ACCEPT,getSeller());
        Gson gsonRequest = new GsonBuilder().serializeNulls().create();
        if (!Files.exists(Paths.get("Resources/Requests"))) {
            File folder = new File("Resources/Requests");
            folder.mkdir();
        }
        try {
            String path = "Resources/Requests/" + request.getRequestId() + ".json";
            while (Files.exists(Paths.get(path))) {
                Random random = new Random();
                request.setRequestId(random.nextInt(10000));
                path = "Resources/Requests/" + request.getRequestId() + ".json";
            }
            String requestDetails = gsonRequest.toJson(request);
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(requestDetails);
            fileWriter.close();
            MessagesLibrary.messagesLibrary(5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String showOffs() {
        Gson gsonOffs = new GsonBuilder().serializeNulls().create();
        String offs = gsonOffs.toJson(getSeller().getSellerOffs());
        return offs;
    }

    public static String showOffDetails(int offId) {
        for (Off i : getSeller().getSellerOffs()) {
            if (i.getOffId() == offId) {
                Gson gson = new GsonBuilder().serializeNulls().create();
                String offDetails = gson.toJson(i);
                return offDetails;
            }
        }
        MessagesLibrary.errorLibrary(7);
        return null;
    }

    public static String showProductDetails(int productId) throws ExceptionsLibrary.NoProductException {
        Product product = GetDataFromDatabase.getProduct(productId);
        if (product != null) {
            Gson gson = new GsonBuilder().serializeNulls().create();
            String productDetails = gson.toJson(product);
            return productDetails;
        } else {
            MessagesLibrary.errorLibrary(6);
            return null;
        }
    }

    public static String showProductBuyers(int productId) throws ExceptionsLibrary.NoProductException {
        Product product = GetDataFromDatabase.getProduct(productId);
        if (product != null) {
            Gson gson = new GsonBuilder().serializeNulls().create();
            String productBuyers = gson.toJson(product.getProductBuyers());
            return productBuyers;
        } else {
            MessagesLibrary.errorLibrary(6);
            return null;
        }
    }

    public static double showBalance() {
        return getSeller().getCredit();
    }



    //TODO further development
    public static String showCategories() {
        String temp = "Hey There!";
        return temp;
    }

}
