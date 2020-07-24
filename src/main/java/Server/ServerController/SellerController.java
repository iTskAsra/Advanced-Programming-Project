package Server.ServerController;

import Server.ClientHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;
import LocalExceptions.ExceptionsLibrary;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

    public static void showSellerInfo() throws ExceptionsLibrary.NoAccountException {
        String adminUsername = ClientHandler.receiveMessage();
        Gson gson = new GsonBuilder().serializeNulls().create();
        if (getSeller() == null) {
            throw new ExceptionsLibrary.NoAccountException();
        }
        Seller seller = (Seller) GetDataFromDatabaseServerSide.getAccount(adminUsername);
        setSeller(seller);
        String data = gson.toJson(seller);
        ClientHandler.sendMessage(data);
    }

    public static void editSellerInfo() throws ExceptionsLibrary.NoAccountException, ExceptionsLibrary.NoFeatureWithThisName, ExceptionsLibrary.ChangeUsernameException {
        HashMap<String, String> dataToEdit = (HashMap<String, String>) ClientHandler.receiveObject();
        Seller seller = (Seller) GetDataFromDatabaseServerSide.getAccount(getSeller().getUsername());
        for (String i : dataToEdit.keySet()) {
            if (i.equals("username")) {
                ClientHandler.sendObject(new ExceptionsLibrary.ChangeUsernameException());
            }
            try {
                try {
                    Field field = Seller.class.getSuperclass().getDeclaredField(i);
                    field.setAccessible(true);
                    field.set(seller, dataToEdit.get(i));
                } catch (NoSuchFieldException e) {
                    ClientHandler.sendObject(e);
                    Field field = Seller.class.getDeclaredField(i);
                    field.setAccessible(true);
                    field.set(seller, dataToEdit.get(i));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                ClientHandler.sendObject(new ExceptionsLibrary.NoFeatureWithThisName());
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
            ClientHandler.sendObject(e);
        }
    }

    public static String showSellerCompany() {
        return getSeller().getCompanyName();
    }

    public static void showSellerProducts() throws ExceptionsLibrary.NoAccountException {
        Seller seller = (Seller) GetDataFromDatabaseServerSide.getAccount(getSeller().getUsername());
        ClientHandler.sendObject(seller.getSellerProducts());
    }

    public static ArrayList<SellLog> showSellerLogs() {
        return getSeller().getSellerLogs();
    }

    public static void editProductRequest() throws ExceptionsLibrary.NoProductException, ExceptionsLibrary.NoFeatureWithThisName, ExceptionsLibrary.CannotChangeThisFeature {
        Object[] receivedData = (Object[]) ClientHandler.receiveObject();
        int productId = Integer.parseInt((String) receivedData[0]);
        HashMap<String, String> dataToEdit = (HashMap<String, String>) receivedData[1];
        Product product = GetDataFromDatabaseServerSide.getProduct(productId);
        if (product != null) {
            for (String i : dataToEdit.keySet()) {
                try {
                    if (i.equalsIgnoreCase("productId")){
                        ClientHandler.sendObject(new ExceptionsLibrary.CannotChangeThisFeature());
                    }
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
                    ClientHandler.sendObject(new ExceptionsLibrary.NoFeatureWithThisName());
                }
            }
            Gson gsonProduct = new GsonBuilder().serializeNulls().create();
            product.setProductCondition(ProductOrOffCondition.PENDING_TO_EDIT);
            String editedProduct = gsonProduct.toJson(product);
            Request request = new Request(editedProduct, RequestType.EDIT_PRODUCT, RequestOrCommentCondition.PENDING_TO_ACCEPT, getSeller().getUsername());
            Gson gsonRequest = new GsonBuilder().serializeNulls().create();
            String requestDetails = gsonRequest.toJson(request);
            try {
                String path = "Resources/Requests/" + request.getRequestId() + ".json";
                FileWriter fileWriter = new FileWriter(path);
                fileWriter.write(requestDetails);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
                ClientHandler.sendObject(e);
            }
        } else {
            ClientHandler.sendObject(new ExceptionsLibrary.NoProductException());
        }
    }

    public static void removeProductRequest() throws ExceptionsLibrary.NoProductException, ExceptionsLibrary.NoAccountException {
        int productId = Integer.parseInt(ClientHandler.receiveMessage());
        Product product = GetDataFromDatabaseServerSide.getProduct(productId);
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().serializeNulls().create();
        String productDetails = gson.toJson(product);
        Request request = new Request(productDetails,RequestType.REMOVE_PRODUCT,RequestOrCommentCondition.PENDING_TO_ACCEPT,getSeller().getUsername());
        Gson gsonRequest = new GsonBuilder().enableComplexMapKeySerialization().serializeNulls().create();
        try {
            String path = "Resources/Requests/" + request.getRequestId() + ".json";
            while (Files.exists(Paths.get(path))) {
                Random random = new Random();
                request.setRequestId(random.nextInt(1000000));
                path = "Resources/Requests/" + request.getRequestId() + ".json";
            }
            String requestDetails = gsonRequest.toJson(request);
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(requestDetails);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            ClientHandler.sendObject(e);
        }
    }

    public static void addProductRequest() {
        Product product = (Product) ClientHandler.receiveObject();
        product.setSeller(getSeller());
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().serializeNulls().create();
        String productDetails = gson.toJson(product);
        Request request = new Request(productDetails, RequestType.ADD_PRODUCT, RequestOrCommentCondition.PENDING_TO_ACCEPT, getSeller().getUsername());
        Gson gsonRequest = new GsonBuilder().enableComplexMapKeySerialization().serializeNulls().create();
        try {
            String path = "Resources/Requests/" + request.getRequestId() + ".json";
            while (Files.exists(Paths.get(path))) {
                Random random = new Random();
                request.setRequestId(random.nextInt(1000000));
                path = "Resources/Requests/" + request.getRequestId() + ".json";
            }
            String requestDetails = gsonRequest.toJson(request);
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(requestDetails);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            ClientHandler.sendObject(e);
        }

    }

    public static void editOffRequest() throws ExceptionsLibrary.NoOffException, ExceptionsLibrary.NoFeatureWithThisName, ExceptionsLibrary.CannotChangeThisFeature {
        Object[] receivedData = (Object[]) ClientHandler.receiveObject();
        int offId = Integer.parseInt((String) receivedData[0]);
        HashMap<String, String> dataToEdit = (HashMap<String, String>) receivedData[1];
        Off off = GetDataFromDatabaseServerSide.getOff(offId);
        if (off != null) {
            for (Off i : getSeller().getSellerOffs()) {
                if (i.getOffId() == off.getOffId()) {
                    for (String s : dataToEdit.keySet()) {
                        try {
                            if (s.equalsIgnoreCase("offId")){
                                ClientHandler.sendObject(new ExceptionsLibrary.CannotChangeThisFeature());
                            }
                            Field field = Off.class.getDeclaredField(s);
                            if (s.equals("offAmount")) {
                                field.setAccessible(true);
                                field.set(off, Double.parseDouble(dataToEdit.get(s)));
                            } else {
                                field.setAccessible(true);
                                field.set(off, dataToEdit.get(s));
                            }
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            ClientHandler.sendObject(new ExceptionsLibrary.NoFeatureWithThisName());
                        }
                    }
                    off.setOffCondition(ProductOrOffCondition.PENDING_TO_EDIT);
                    Gson gsonOff = new GsonBuilder().serializeNulls().create();
                    String editedOff = gsonOff.toJson(off);
                    Request request = new Request(editedOff, RequestType.EDIT_OFF, RequestOrCommentCondition.PENDING_TO_ACCEPT, getSeller().getUsername());
                    Gson gsonRequest = new GsonBuilder().serializeNulls().create();
                    String requestDetails = gsonRequest.toJson(request);
                    try {
                        String path = "Resources/Requests/" + request.getRequestId() + ".json";
                        FileWriter fileWriter = new FileWriter(path);
                        fileWriter.write(requestDetails);
                        fileWriter.close();
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                        ClientHandler.sendObject(e);
                    }
                }
            }
        } else {
            ClientHandler.sendObject(new ExceptionsLibrary.NoOffException());
        }
    }

    public static void addOffRequest() throws ExceptionsLibrary.NoProductException {
        Object[] receivedData = (Object[]) ClientHandler.receiveObject();
        Off off = (Off) receivedData[0];
        String offProducts = (String) receivedData[1];
        Gson gsonOff = new GsonBuilder().serializeNulls().create();
        off.setOffCondition(ProductOrOffCondition.PENDING_TO_CREATE);
        ArrayList<String> offProductsList = new ArrayList<>();
        String[] productIds = offProducts.split("\\s*,\\s*");
        for (String i : productIds){
            try {
                Product temp = GetDataFromDatabaseServerSide.getProduct(Integer.parseInt(i));
                Iterator<Product> iterator = getSeller().getSellerProducts().iterator();
                while (iterator.hasNext()) {
                    Product tempProduct = iterator.next();
                    if (tempProduct.getProductId() == temp.getProductId()) {
                        offProductsList.add(i);
                    }
                }
            }
            catch (Exception e){
                ClientHandler.sendObject(new ExceptionsLibrary.NoProductException());
            }
        }
        off.setOffProducts(offProductsList);
        String offDetails = gsonOff.toJson(off);
        Request request = new Request(offDetails, RequestType.ADD_OFF, RequestOrCommentCondition.PENDING_TO_ACCEPT, getSeller().getUsername());
        Gson gsonRequest = new GsonBuilder().serializeNulls().create();
        if (!Files.exists(Paths.get("Resources/Requests"))) {
            File folder = new File("Resources/Requests");
            folder.mkdir();
        }
        try {
            String path = "Resources/Requests/" + request.getRequestId() + ".json";
            while (Files.exists(Paths.get(path))) {
                Random random = new Random();
                request.setRequestId(random.nextInt(1000000));
                path = "Resources/Requests/" + request.getRequestId() + ".json";
            }
            String requestDetails = gsonRequest.toJson(request);
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(requestDetails);
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
            ClientHandler.sendObject(e);
        }
    }

    public static void showOffs() throws ExceptionsLibrary.NoAccountException {
        Seller seller = (Seller) GetDataFromDatabaseServerSide.getAccount(getSeller().getUsername());
        ClientHandler.sendObject(seller.getSellerOffs());
    }

    public static void showOffDetails() throws ExceptionsLibrary.NoOffException {
        int offId = Integer.parseInt(ClientHandler.receiveMessage());
        for (Off i : getSeller().getSellerOffs()) {
            if (i.getOffId() == offId) {
                ClientHandler.sendObject(i);
            }
        }
        ClientHandler.sendObject(new ExceptionsLibrary.NoOffException());
    }

    public static void showProductDetails() throws ExceptionsLibrary.NoProductException {
        int productId = Integer.parseInt(ClientHandler.receiveMessage());
        Product product = null;
        try {
            product = GetDataFromDatabaseServerSide.getProduct(productId);
        } catch (ExceptionsLibrary.NoProductException e) {
            e.printStackTrace();
            ClientHandler.sendObject(e);
            return;
        }
        ClientHandler.sendObject(product);
    }

    public static void showProductBuyers() throws ExceptionsLibrary.NoProductException, ExceptionsLibrary.NoAccountException {
        int productId = Integer.parseInt(ClientHandler.receiveMessage());
        Product product = GetDataFromDatabaseServerSide.getProduct(productId);
        ArrayList<SellLog> buyersLogs = new ArrayList<>();
        Seller seller = (Seller) GetDataFromDatabaseServerSide.getAccount(getSeller().getUsername());
        for (SellLog i : seller.getSellerLogs()) {
            for (String[] j : i.getLogProducts()) {
                if (Integer.parseInt(j[0]) == productId) {
                    buyersLogs.add(i);
                }
            }
        }
        ClientHandler.sendObject(buyersLogs);
    }

    public static double showBalance() {
        return getSeller().getCredit();
    }


    public static void showCategories() throws ExceptionsLibrary.NoCategoryException {
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
            Category category = GetDataFromDatabaseServerSide.getCategory(categoryName);
            allCategories.add(category);
        }
        ClientHandler.sendObject(allCategories);
    }

    public static void checkIfCategoryExists() {
        String categoryName = ClientHandler.receiveMessage();
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
                ClientHandler.sendObject(true);
            }
        }
        ClientHandler.sendObject(false);
    }

    public static void showSellerRequests() throws ExceptionsLibrary.NoRequestException {
        ArrayList<Request> sellerRequests = new ArrayList<>();
        String path = "Resources/Requests";
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
            String requestId = fileName.replace(".json", "");
            Request request = GetDataFromDatabaseServerSide.getRequest(Integer.parseInt(requestId));
            if (request.getRequestSeller().equals(getSeller().getUsername())) {
                sellerRequests.add(request);
            }
        }
        ClientHandler.sendObject(sellerRequests);
    }

}
