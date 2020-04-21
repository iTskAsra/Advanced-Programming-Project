package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;
import view.MessagesLibrary;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class AdminController {
    private static Admin admin;


    public AdminController(Admin admin) {
        this.admin = admin;
    }

    public static Admin getAdmin() {
        return admin;
    }

    //TODO check errors

    public static void setAdmin(Admin admin) {
        AdminController.admin = admin;
    }

    public static String showAdminInfo() {
        Gson gson = new Gson();
        if (getAdmin() == null) {
            MessagesLibrary.errorLibrary(4);
            return null;
        }
        Admin admin = (Admin) GetDataFromDatabase.getAccount(getAdmin().getUsername());
        setAdmin(admin);
        String data = gson.toJson(admin);
        return data;
    }

    public static void editAdminInfo(HashMap<String, String> dataToEdit) {
        Admin admin = (Admin) GetDataFromDatabase.getAccount(getAdmin().getUsername());
        for (String i : dataToEdit.keySet()) {
            try {
                try {
                    Field field = Admin.class.getSuperclass().getDeclaredField(i);
                    field.setAccessible(true);
                    field.set(admin, dataToEdit.get(i));
                } catch (NoSuchFieldException e) {
                    Field field = Admin.class.getDeclaredField(i);
                    field.setAccessible(true);
                    field.set(admin, dataToEdit.get(i));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                MessagesLibrary.errorLibrary(5);
                e.printStackTrace();
            }
        }
        Gson gson = new Gson();
        setAdmin(admin);
        String editedDetails = gson.toJson(admin);
        try {
            String path = "src/main/resources/Accounts/Admin/" + getAdmin().getUsername() + ".json";
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(editedDetails);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Request> showAdminRequests() {
        ArrayList<Request> allRequests = new ArrayList<>();
        String path = "src/main/resources/Requests";
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
            Request request = GetDataFromDatabase.getRequest(Integer.parseInt(requestId));
            allRequests.add(request);
        }
        return allRequests;
    }

    public static String showRequest(int requestId) {
        Request request = GetDataFromDatabase.getRequest(requestId);
        if (request != null) {
            Gson gson = new Gson();
            String reuestData = gson.toJson(request);
            return reuestData;
        } else {
            MessagesLibrary.errorLibrary(8);
            return null;
        }

    }

    public static void processRequest(int requestId, boolean acceptStatus) {
        Request request = GetDataFromDatabase.getRequest(requestId);
        if (request != null) {
            Gson gson = new GsonBuilder().serializeNulls().create();
            switch (request.getRequestType()) {
                case ADD_OFF:
                    if (acceptStatus) {
                        Off off = gson.fromJson(request.getRequestDescription(), Off.class);
                        off.setOffCondition(ProductOrOffCondition.ACCEPTED);
                        while (checkIfOffExist(off.getOffId())) {
                            Random random = new Random();
                            off.setOffId(random.nextInt(10000));
                        }
                        String offDetails = gson.toJson(off);
                        Seller seller = (Seller) GetDataFromDatabase.getAccount(request.getRequestSeller().getUsername());
                        seller.getSellerOffs().add(off);
                        try {
                            String offPath = "src/main/resources/Offs/" + off.getOffId() + ".json";
                            String sellerPath = "src/main/resources/Accounts/Seller" + seller.getUsername() + ".json";
                            File file = new File(offPath);
                            file.createNewFile();
                            FileWriter fileWriter = new FileWriter(file);
                            fileWriter.write(offDetails);
                            fileWriter.close();
                            FileWriter fileWriterSeller = new FileWriter(sellerPath);
                            Gson gsonSeller = new GsonBuilder().serializeNulls().create();
                            String sellerData = gsonSeller.toJson(seller);
                            fileWriterSeller.write(sellerData);
                            fileWriterSeller.close();
                            MessagesLibrary.messagesLibrary(4);
                            String requestPath = "src/main/resources/Requests/" + request.getRequestId() + ".json";
                            File fileRequest = new File(requestPath);
                            fileRequest.delete();
                        } catch (IOException e) {
                            MessagesLibrary.errorLibrary(9);
                            e.printStackTrace();
                        }
                    } else {
                        String requestPath = "src/main/resources/Requests/" + request.getRequestId() + ".json";
                        File fileRequest = new File(requestPath);
                        fileRequest.delete();
                        MessagesLibrary.messagesLibrary(6);

                    }
                    break;
                case EDIT_OFF:
                    if (acceptStatus) {
                        Off off = gson.fromJson(request.getRequestDescription(), Off.class);
                        off.setOffCondition(ProductOrOffCondition.ACCEPTED);
                        String offDetails = gson.toJson(off);
                        Seller seller = (Seller) GetDataFromDatabase.getAccount(request.getRequestSeller().getUsername());
                        Iterator iterator = seller.getSellerOffs().iterator();
                        while (iterator.hasNext()) {
                            Off tempOff = (Off) iterator.next();
                            if (tempOff.getOffId() == off.getOffId()) {
                                iterator.remove();
                            }
                        }
                        seller.getSellerOffs().add(off);
                        try {
                            String offPath = "src/main/resources/Offs/" + off.getOffId() + ".json";
                            String sellerPath = "src/main/resources/Accounts/Seller" + seller.getUsername() + ".json";
                            FileWriter fileWriter = new FileWriter(offPath);
                            fileWriter.write(offDetails);
                            fileWriter.close();
                            FileWriter fileWriterSeller = new FileWriter(sellerPath);
                            Gson gsonSeller = new GsonBuilder().serializeNulls().create();
                            String sellerData = gsonSeller.toJson(seller);
                            fileWriterSeller.write(sellerData);
                            fileWriterSeller.close();
                            MessagesLibrary.messagesLibrary(4);
                            String requestPath = "src/main/resources/Requests/" + request.getRequestId() + ".json";
                            File fileRequest = new File(requestPath);
                            fileRequest.delete();
                        } catch (IOException e) {
                            MessagesLibrary.errorLibrary(9);
                            e.printStackTrace();
                        }
                    } else {
                        String requestPath = "src/main/resources/Requests/" + request.getRequestId() + ".json";
                        File fileRequest = new File(requestPath);
                        fileRequest.delete();
                        MessagesLibrary.messagesLibrary(6);
                    }
                    break;
                case ADD_PRODUCT:
                    if (acceptStatus) {
                        Product product = gson.fromJson(request.getRequestDescription(), Product.class);
                        product.setProductCondition(ProductOrOffCondition.ACCEPTED);
                        while (checkIfProductExist(product.getProductId())) {
                            Random random = new Random();
                            product.setProductId(random.nextInt(10000));
                        }
                        String productDetails = gson.toJson(product);
                        Seller seller = (Seller) GetDataFromDatabase.getAccount(request.getRequestSeller().getUsername());
                        seller.getSellerProducts().add(product);
                        try {
                            String productPath = "src/main/resources/Products/" + product.getProductId() + ".json";
                            String sellerPath = "src/main/resources/Accounts/Seller" + seller.getUsername() + ".json";
                            File file = new File(productPath);
                            file.createNewFile();
                            FileWriter fileWriter = new FileWriter(file);
                            fileWriter.write(productDetails);
                            fileWriter.close();
                            FileWriter fileWriterSeller = new FileWriter(sellerPath);
                            Gson gsonSeller = new GsonBuilder().serializeNulls().create();
                            String sellerData = gsonSeller.toJson(seller);
                            fileWriterSeller.write(sellerData);
                            fileWriterSeller.close();
                            MessagesLibrary.messagesLibrary(4);
                            String requestPath = "src/main/resources/Requests/" + request.getRequestId() + ".json";
                            File fileRequest = new File(requestPath);
                            fileRequest.delete();
                        } catch (IOException e) {
                            MessagesLibrary.errorLibrary(9);
                            e.printStackTrace();
                        }
                    } else {
                        String requestPath = "src/main/resources/Requests/" + request.getRequestId() + ".json";
                        File fileRequest = new File(requestPath);
                        fileRequest.delete();
                    }
                    break;
                case EDIT_PODUCT:
                    if (acceptStatus) {
                        Product product = gson.fromJson(request.getRequestDescription(), Product.class);
                        product.setProductCondition(ProductOrOffCondition.ACCEPTED);
                        String productDetails = gson.toJson(product);
                        Seller seller = (Seller) GetDataFromDatabase.getAccount(request.getRequestSeller().getUsername());
                        Iterator iterator = seller.getSellerProducts().iterator();
                        while (iterator.hasNext()) {
                            Product tempProduct = (Product) iterator.next();
                            if (tempProduct.getProductId() == product.getProductId()) {
                                iterator.remove();
                            }
                        }
                        seller.getSellerProducts().add(product);
                        try {
                            String productPath = "src/main/resources/Products/" + product.getProductId() + ".json";
                            String sellerPath = "src/main/resources/Accounts/Seller" + seller.getUsername() + ".json";
                            FileWriter fileWriter = new FileWriter(productPath);
                            fileWriter.write(productDetails);
                            fileWriter.close();
                            FileWriter fileWriterSeller = new FileWriter(sellerPath);
                            Gson gsonSeller = new GsonBuilder().serializeNulls().create();
                            String sellerData = gsonSeller.toJson(seller);
                            fileWriterSeller.write(sellerData);
                            fileWriterSeller.close();
                            MessagesLibrary.messagesLibrary(4);
                            String requestPath = "src/main/resources/Requests/" + request.getRequestId() + ".json";
                            File fileRequest = new File(requestPath);
                            fileRequest.delete();
                        } catch (IOException e) {
                            MessagesLibrary.errorLibrary(9);
                            e.printStackTrace();
                        }
                    } else {
                        String requestPath = "src/main/resources/Requests/" + request.getRequestId() + ".json";
                        File fileRequest = new File(requestPath);
                        fileRequest.delete();
                    }
                    break;
                case REGISTER_SELLER:
                    if (acceptStatus) {
                        Seller seller = gson.fromJson(request.getRequestDescription(), Seller.class);
                        String sellerDetails = gson.toJson(seller);
                        try {
                            String sellerPath = "src/main/resources/Accounts/Seller" + seller.getUsername() + ".json";
                            File file = new File(sellerPath);
                            file.createNewFile();
                            FileWriter fileWriterSeller = new FileWriter(sellerPath);
                            Gson gsonSeller = new GsonBuilder().serializeNulls().create();
                            String sellerData = gsonSeller.toJson(seller);
                            fileWriterSeller.write(sellerData);
                            fileWriterSeller.close();
                            MessagesLibrary.messagesLibrary(4);
                            String requestPath = "src/main/resources/Requests/" + request.getRequestId() + ".json";
                            File fileRequest = new File(requestPath);
                            fileRequest.delete();
                        } catch (IOException e) {
                            MessagesLibrary.errorLibrary(9);
                            e.printStackTrace();
                        }
                    } else {
                        String requestPath = "src/main/resources/Requests/" + request.getRequestId() + ".json";
                        File fileRequest = new File(requestPath);
                        fileRequest.delete();
                    }
                    break;
            }
        } else {
            MessagesLibrary.errorLibrary(8);
        }
    }

    private static boolean checkIfOffExist(int offId) {
        String path = "src/main/resources/Offs/" + offId + ".json";
        File file = new File(path);
        if (!file.exists()) {
            return false;
        } else {
            return true;
        }
    }

    public static ArrayList<Sale> showSales() {
        ArrayList<Sale> allSales = new ArrayList<>();
        String path = "src/main/resources/Sales";
        File file = new File(path);
        for (File i : file.listFiles()) {
            String fileName = i.getName();
            String saleCode = fileName.replace(".json", "");
            Sale sale = GetDataFromDatabase.getSale(saleCode);
            allSales.add(sale);
        }
        return allSales;
    }

    public static void editSaleInfo(String saleCode, HashMap<String, String> dataToEdit) {
        Sale sale = (Sale) GetDataFromDatabase.getSale(saleCode);
        for (String i : dataToEdit.keySet()) {
            try {
                Field field = Sale.class.getDeclaredField(i);
                if (i.equals("salePercent")) {
                    field.setAccessible(true);
                    field.set(sale, Double.parseDouble(dataToEdit.get(i)));
                } else if (i.equals("saleMaxAmount")) {
                    field.setAccessible(true);
                    field.set(sale, Double.parseDouble(dataToEdit.get(i)));
                } else if (i.equals("validTimes")) {
                    field.setAccessible(true);
                    field.set(sale, Integer.parseInt(dataToEdit.get(i)));
                } else {
                    field.setAccessible(true);
                    field.set(sale, dataToEdit.get(i));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                MessagesLibrary.errorLibrary(5);
                e.printStackTrace();
            }
        }
        Gson gson = new GsonBuilder().serializeNulls().create();
        String editedDetails = gson.toJson(sale);
        try {
            String path = "src/main/resources/Sales/" + sale.getSaleCode() + ".json";
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(editedDetails);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addSale(String saleDetails) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Sale sale = gson.fromJson(saleDetails, Sale.class);
        while (checkSaleCode(sale.getSaleCode())) {
            sale.setSaleCode(Sale.getRandomSaleCode());
        }
        String newSaleDetails = gson.toJson(sale);
        try {
            String path = "src/main/resources/Sales/" + sale.getSaleCode() + ".json";
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(newSaleDetails);
            fileWriter.close();
            MessagesLibrary.messagesLibrary(7);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, ArrayList<Account>> showAllUsers() {
        String customerPath = "src/main/resources/Accounts/Customer";
        String sellerPath = "src/main/resources/Accounts/Seller";
        String adminPath = "src/main/resources/Accounts/Admin";
        ArrayList<Account> customersList = new ArrayList<>();
        ArrayList<Account> sellersList = new ArrayList<>();
        ArrayList<Account> adminsList = new ArrayList<>();
        HashMap<String, ArrayList<Account>> list = new HashMap<>();
        list.put("Customers", customersList);
        list.put("Sellers", sellersList);
        list.put("Admins", adminsList);
        File customerFolder = new File(customerPath);
        File sellerFolder = new File(sellerPath);
        File adminFolder = new File(adminPath);
        for (File i : customerFolder.listFiles()) {
            String fileName = i.getName();
            String username = fileName.replace(".json", "");
            Account account = GetDataFromDatabase.getAccount(username);
            customersList.add(account);
        }
        for (File i : sellerFolder.listFiles()) {
            String fileName = i.getName();
            String username = fileName.replace(".json", "");
            Account account = GetDataFromDatabase.getAccount(username);
            sellersList.add(account);
        }

        for (File i : adminFolder.listFiles()) {
            String fileName = i.getName();
            String username = fileName.replace(".json", "");
            Account account = GetDataFromDatabase.getAccount(username);
            adminsList.add(account);
        }
        return list;
    }

    public static String showUserDetails(String username) {
        Account account = GetDataFromDatabase.getAccount(username);
        if (account != null) {
            Gson gson = new GsonBuilder().serializeNulls().create();
            return gson.toJson(account);
        } else {
            return null;
        }
    }

    public static void deleteUser(String username) {
        Account account = GetDataFromDatabase.getAccount(username);
        if (account != null) {
            String path = "src/main/resources/Accounts/"+account.getRole()+".json";
            File file = new File(path);
            file.delete();
            MessagesLibrary.messagesLibrary(2);//delete user
        }
        else {
            MessagesLibrary.errorLibrary(10);//no user with this username
        }
    }

    public static String addAdminAccount(String newAdminDetails) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Admin admin= gson.fromJson(newAdminDetails,Admin.class);
        if (RegisterAndLogin.checkUsername(admin.getUsername())){
            String path = "src/main/resources/Accounts/Admin"+admin.getUsername()+".json";
            try {
                File file = new File(path);
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(newAdminDetails);
                fileWriter.close();
                return admin.getRole();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {
            return null;
        }
        return null;
    }

    public static void deleteProduct(int productId) {
        Product product = GetDataFromDatabase.getProduct(productId);
        if (product != null) {
            String path = "src/main/resources/Products/"+product.getProductId()+".json";
            File file = new File(path);
            file.delete();
            MessagesLibrary.messagesLibrary(2);//delete product
        }
        else {
            MessagesLibrary.errorLibrary(10);//no Product with this productId
        }
    }

    public static ArrayList<Category> showCategories() {
        ArrayList<Category> allCategories = new ArrayList<>();
        String path = "src/main/resources/Categories";
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

    public static void deleteCategory(String categoryName) {
        Category category = GetDataFromDatabase.getCategory(categoryName);
        if (category != null) {
            String path = "src/main/resources/Categories/"+category.getName()+".json";
            File file = new File(path);
            file.delete();
            MessagesLibrary.messagesLibrary(2);//delete category
        }
        else {
            MessagesLibrary.errorLibrary(10);//no category with this name
        }
    }

    public static void editCategory(String categoryName, HashMap<String, String> dataToEdit) {
        Category category =  GetDataFromDatabase.getCategory(categoryName);
        for (String i : dataToEdit.keySet()) {
            try {
                Field field = Sale.class.getDeclaredField(i);
                if (i.equals("features")) {
                    field.setAccessible(true);
                    String[] splitFeatures = dataToEdit.get(i).split("\\s*,\\s*");
                    ArrayList<Feature> newFeatures = new ArrayList<>();
                    for (String j : splitFeatures){
                        newFeatures.add(new Feature(j,null));
                    }
                    field.set(category, newFeatures);
                } else {
                    field.setAccessible(true);
                    field.set(category, dataToEdit.get(i));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                MessagesLibrary.errorLibrary(5);
                e.printStackTrace();
            }
        }
        Gson gson = new GsonBuilder().serializeNulls().create();
        String editedDetails = gson.toJson(category);
        try {
            String path = "src/main/resources/Categories/" + category.getName() + ".json";
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(editedDetails);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addCategory(String categoryDetails) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Category category = gson.fromJson(categoryDetails, Category.class);
        if (!checkCategoryName(category.getName())) {
            String newSaleDetails = gson.toJson(category);
            try {
                String path = "src/main/resources/Categories/" + category.getName() + ".json";
                FileWriter fileWriter = new FileWriter(path);
                fileWriter.write(newSaleDetails);
                fileWriter.close();
                MessagesLibrary.messagesLibrary(8);
            } catch (IOException e) {
                //Error
                e.printStackTrace();
            }
        }
    }

    public static String viewSaleCodeDetails(String saleCode) {
        Sale sale = GetDataFromDatabase.getSale(saleCode);
        if (sale != null) {
            Gson gson = new GsonBuilder().serializeNulls().create();
            return gson.toJson(sale);
        } else {
            return null;
        }
    }

    public static void removeSaleCode(String saleCode) {
        Sale sale = GetDataFromDatabase.getSale(saleCode);
        if (sale != null) {
            String path = "src/main/resources/Sales/"+sale.getSaleCode()+".json";
            File file = new File(path);
            file.delete();
            MessagesLibrary.messagesLibrary(2);//delete sale
        }
        else {
            MessagesLibrary.errorLibrary(10);//no sale with this code
        }
    }

    private static boolean checkCategoryName(String categoryName) {
        String path = "src/main/resources/Categories";
        File folder = new File(path);
        for (File i : folder.listFiles()) {
            String fileName = i.getName();
            String fileCategoryName = fileName.replace(".json", "");
            if (categoryName.equals(fileCategoryName)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkSaleCode(String saleCode) {
        String path = "src/main/resources/Sales";
        File folder = new File(path);
        for (File i : folder.listFiles()) {
            String fileName = i.getName();
            String fileSaleCode = fileName.replace(".json", "");
            if (saleCode.equals(fileSaleCode)) {
                return true;
            }
        }
        return false;
    }


    private static boolean checkIfProductExist(int productId) {
        String path = "src/main/resources/Products/" + productId + ".json";
        File file = new File(path);
        if (!file.exists()) {
            return false;
        } else {
            return true;
        }
    }

}
