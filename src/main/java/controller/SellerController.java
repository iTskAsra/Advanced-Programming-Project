package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    public static String showSellerInfo() throws ExceptionsLibrary.NoAccountException {
        Gson gson = new GsonBuilder().serializeNulls().create();
        if (getSeller() == null) {
            throw new ExceptionsLibrary.NoAccountException();
        }
        Seller seller = (Seller) GetDataFromDatabase.getAccount(getSeller().getUsername());
        setSeller(seller);
        String data = gson.toJson(seller);
        return data;
    }

    public static void editSellerInfo(HashMap<String, String> dataToEdit) throws ExceptionsLibrary.NoAccountException, ExceptionsLibrary.NoFeatureWithThisName, ExceptionsLibrary.ChangeUsernameException {
        Seller seller = (Seller) GetDataFromDatabase.getAccount(getSeller().getUsername());
        for (String i : dataToEdit.keySet()) {
            if (i.equals("username")) {
                throw new ExceptionsLibrary.ChangeUsernameException();
            }
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
                throw new ExceptionsLibrary.NoFeatureWithThisName();
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

    public static ArrayList<Product> showSellerProducts() throws ExceptionsLibrary.NoAccountException {
        Seller seller = (Seller) GetDataFromDatabase.getAccount(getSeller().getUsername());
        return seller.getSellerProducts();
    }

    public static ArrayList<SellLog> showSellerLogs() {
        return getSeller().getSellerLogs();
    }

    public static void editProductRequest(int productId, HashMap<String, String> dataToEdit) throws ExceptionsLibrary.NoProductException, ExceptionsLibrary.NoFeatureWithThisName {
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
                    throw new ExceptionsLibrary.NoFeatureWithThisName();
                }
            }
            Gson gsonProduct = new GsonBuilder().serializeNulls().create();
            product.setProductCondition(ProductOrOffCondition.PENDING_TO_EDIT);
            String editedProduct = gsonProduct.toJson(product);
            Request request = new Request(editedProduct, RequestType.EDIT_PODUCT, RequestOrCommentCondition.PENDING_TO_ACCEPT, getSeller());
            Gson gsonRequest = new GsonBuilder().serializeNulls().create();
            String requestDetails = gsonRequest.toJson(request);
            try {
                String path = "Resources/Requests/" + request.getRequestId() + ".json";
                FileWriter fileWriter = new FileWriter(path);
                fileWriter.write(requestDetails);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new ExceptionsLibrary.NoProductException();
        }
    }

    public static void removeProduct(int productId) throws ExceptionsLibrary.NoProductException, ExceptionsLibrary.NoAccountException {
        Product product = GetDataFromDatabase.getProduct(productId);
        String path = "Resources/Products/" + product.getProductId() + ".json";
        SetDataToDatabase.updateSellerAndOffsOfProduct(product,1);
        File file = new File(path);
        file.delete();
        SetDataToDatabase.setAccount(getSeller());
    }

    public static void addProductRequest(Product product, String category) {
        product.setSeller(getSeller());
        Gson gson = new GsonBuilder().serializeNulls().create();
        String productDetails = gson.toJson(product);
        Request request = new Request(productDetails, RequestType.ADD_PRODUCT, RequestOrCommentCondition.PENDING_TO_ACCEPT, getSeller());
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

    public static void editOffRequest(int offId, HashMap<String, String> dataToEdit) throws ExceptionsLibrary.NoOffException, ExceptionsLibrary.NoFeatureWithThisName {
        Off off = GetDataFromDatabase.getOff(offId);
        if (off != null) {
            for (Off i : getSeller().getSellerOffs()) {
                if (i.getOffId() == off.getOffId()) {
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
                            throw new ExceptionsLibrary.NoFeatureWithThisName();
                        }
                    }
                    off.setOffCondition(ProductOrOffCondition.PENDING_TO_EDIT);
                    Gson gsonOff = new GsonBuilder().serializeNulls().create();
                    String editedOff = gsonOff.toJson(off);
                    Request request = new Request(editedOff, RequestType.EDIT_OFF, RequestOrCommentCondition.PENDING_TO_ACCEPT, getSeller());
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
        } else {
            throw new ExceptionsLibrary.NoOffException();
        }
    }

    public static void addOffRequest(Off off, String offProducts) throws ExceptionsLibrary.NoProductException {
        Gson gsonOff = new GsonBuilder().serializeNulls().create();
        off.setOffCondition(ProductOrOffCondition.PENDING_TO_CREATE);
        ArrayList<Product> offProductsList = new ArrayList<>();
        String[] productIds = offProducts.split("\\s*,\\s*");
        for (String i : productIds){
            try {
                Product temp = GetDataFromDatabase.getProduct(Integer.parseInt(i));
                if (getSeller().getSellerProducts().contains(temp)) {
                    offProductsList.add(temp);
                }
            }
            catch (Exception e){
                throw new ExceptionsLibrary.NoProductException();
            }
        }
        off.setOffProducts(offProductsList);
        String offDetails = gsonOff.toJson(off);
        Request request = new Request(offDetails, RequestType.ADD_OFF, RequestOrCommentCondition.PENDING_TO_ACCEPT, getSeller());
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

    public static ArrayList<Off> showOffs() throws ExceptionsLibrary.NoAccountException {
        Seller seller = (Seller) GetDataFromDatabase.getAccount(getSeller().getUsername());
        return seller.getSellerOffs();
    }

    public static Off showOffDetails(int offId) throws ExceptionsLibrary.NoOffException {
        for (Off i : getSeller().getSellerOffs()) {
            if (i.getOffId() == offId) {
                return i;
            }
        }
        throw new ExceptionsLibrary.NoOffException();
    }

    public static Product showProductDetails(int productId) throws ExceptionsLibrary.NoProductException {
        Product product = null;
        try {
            product = GetDataFromDatabase.getProduct(productId);
        } catch (ExceptionsLibrary.NoProductException e) {
            throw new ExceptionsLibrary.NoProductException();
        }
        return product;
    }

    public static ArrayList<SellLog> showProductBuyers(int productId) throws ExceptionsLibrary.NoProductException, ExceptionsLibrary.NoAccountException {
        Product product = GetDataFromDatabase.getProduct(productId);
        ArrayList<SellLog> buyersLogs = new ArrayList<>();
        Seller seller = (Seller) GetDataFromDatabase.getAccount(getSeller().getUsername());
        for (SellLog i : seller.getSellerLogs()) {
            for (Product j : i.getLogProducts().keySet()) {
                if (j.getProductId() == productId) {
                    buyersLogs.add(i);
                }
            }
        }
        return buyersLogs;
    }

    public static double showBalance() {
        return getSeller().getCredit();
    }


    public static ArrayList<Category> showCategories() throws ExceptionsLibrary.NoCategoryException {
        ArrayList<Category> allCategories = new ArrayList<>();
        String path = "Resources/Category";
        File file = new File(path);
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File file1) {
                if (file1.getName().endsWith(".json")) {
                    return true;
                }
                return false;
            }
        };
        for (File i : file.listFiles(fileFilter)) {
            String fileName = i.getName();
            String categoryName = fileName.replace(".json", "");
            Category category = GetDataFromDatabase.getCategory(categoryName);
            allCategories.add(category);
        }
        return allCategories;
    }

    public static boolean checkIfCategoryExists(String categoryName) {
        String path = "Resources/Category";
        File folder = new File(path);
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File file1) {
                if (file1.getName().endsWith(".json")) {
                    return true;
                }
                return false;
            }
        };
        for (File i : folder.listFiles(fileFilter)) {
            String fileName = i.getName();
            String fileCategoryName = fileName.replace(".json", "");
            if (categoryName.equals(fileCategoryName)) {
                return true;
            }
        }
        return false;
    }

}
