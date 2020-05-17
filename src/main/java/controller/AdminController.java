package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class AdminController {
    private static Admin admin;


    public AdminController(Admin admin) {
        this.admin = admin;
    }

    public static Admin getAdmin() {
        return admin;
    }


    public static void setAdmin(Admin admin) {
        AdminController.admin = admin;
    }

    public static String showAdminInfo() throws ExceptionsLibrary.NoAccountException {
        Gson gson = new GsonBuilder().serializeNulls().create();
        if (getAdmin() == null) {
            throw new ExceptionsLibrary.NoAccountException();
        }
        Admin admin = (Admin) GetDataFromDatabase.getAccount(getAdmin().getUsername());
        setAdmin(admin);
        String data = gson.toJson(admin);
        return data;
    }

    public static void editAdminInfo(HashMap<String, String> dataToEdit) throws ExceptionsLibrary.NoFeatureWithThisName, ExceptionsLibrary.NoAccountException, ExceptionsLibrary.ChangeUsernameException {
        Admin admin = (Admin) GetDataFromDatabase.getAccount(getAdmin().getUsername());
        for (String i : dataToEdit.keySet()) {
            if (i.equals("username")){
                throw new ExceptionsLibrary.ChangeUsernameException();
            }
            try {
                Field field = Admin.class.getSuperclass().getDeclaredField(i);
                field.setAccessible(true);
                field.set(admin, dataToEdit.get(i));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new ExceptionsLibrary.NoFeatureWithThisName();
            }
        }
        Gson gson = new GsonBuilder().serializeNulls().create();
        setAdmin(admin);
        SetDataToDatabase.setAccount(getAdmin());
    }

    public static ArrayList<Request> showAdminRequests() throws ExceptionsLibrary.NoRequestException {
        ArrayList<Request> allRequests = new ArrayList<>();
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
            Request request = GetDataFromDatabase.getRequest(Integer.parseInt(requestId));
            allRequests.add(request);
        }
        return allRequests;
    }

    public static String showRequest(int requestId) throws ExceptionsLibrary.NoRequestException {
        Request request = GetDataFromDatabase.getRequest(requestId);
        if (request != null) {
            Gson gson = new GsonBuilder().serializeNulls().create();
            String reuestData = gson.toJson(request);
            return reuestData;
        } else {
            throw new ExceptionsLibrary.NoRequestException();
        }

    }

    public static void processRequest(int requestId, boolean acceptStatus) throws ExceptionsLibrary.NoRequestException, ExceptionsLibrary.NoAccountException, ExceptionsLibrary.UsernameAlreadyExists {
        //TODO check exceptions and messages in process request
        Request request = GetDataFromDatabase.getRequest(requestId);
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
                    Seller seller = (Seller) GetDataFromDatabase.getAccount(request.getRequestSeller());
                    seller.getSellerOffs().add(off);
                    try {
                        String offPath = "Resources/Offs/" + off.getOffId() + ".json";
                        File file = new File(offPath);
                        file.createNewFile();
                        FileWriter fileWriter = new FileWriter(file);
                        fileWriter.write(offDetails);
                        fileWriter.close();
                        SetDataToDatabase.setAccount(seller);
                        String requestPath = "Resources/Requests/" + request.getRequestId() + ".json";
                        for (Product i :off.getOffProducts()){
                            i.setPriceWithOff(i.getPrice()-off.getOffAmount());
                            SetDataToDatabase.setProduct(i);
                            SetDataToDatabase.updateSellerAndOffsOfProduct(i,0);
                        }
                        File fileRequest = new File(requestPath);
                        fileRequest.delete();
                    } catch (IOException e) {
                        String requestPath = "Resources/Requests/" + request.getRequestId() + ".json";
                        File fileRequest = new File(requestPath);
                        fileRequest.delete();
                        throw new ExceptionsLibrary.NoAccountException();
                    }
                } else {
                    String requestPath = "Resources/Requests/" + request.getRequestId() + ".json";
                    File fileRequest = new File(requestPath);
                    fileRequest.delete();

                }
                break;
            case EDIT_OFF:
                if (acceptStatus) {
                    Off off = gson.fromJson(request.getRequestDescription(), Off.class);
                    off.setOffCondition(ProductOrOffCondition.ACCEPTED);
                    String offDetails = gson.toJson(off);
                    Seller seller = (Seller) GetDataFromDatabase.getAccount(request.getRequestSeller());
                    Iterator iterator = seller.getSellerOffs().iterator();
                    while (iterator.hasNext()) {
                        Off tempOff = (Off) iterator.next();
                        if (tempOff.getOffId() == off.getOffId()) {
                            iterator.remove();
                        }
                    }
                    for (Product i : off.getOffProducts()){
                        i.setPriceWithOff(i.getPrice()-off.getOffAmount());
                        SetDataToDatabase.setProduct(i);
                        SetDataToDatabase.updateSellerAndOffsOfProduct(i,0);
                    }
                    seller.getSellerOffs().add(off);
                    try {
                        String offPath = "Resources/Offs/" + off.getOffId() + ".json";
                        String sellerPath = "Resources/Accounts/Seller/" + seller.getUsername() + ".json";
                        FileWriter fileWriter = new FileWriter(offPath);
                        fileWriter.write(offDetails);
                        fileWriter.close();
                        SetDataToDatabase.setAccount(seller);
                        String requestPath = "Resources/Requests/" + request.getRequestId() + ".json";
                        File fileRequest = new File(requestPath);
                        fileRequest.delete();
                    } catch (IOException e) {
                        String requestPath = "Resources/Requests/" + request.getRequestId() + ".json";
                        File fileRequest = new File(requestPath);
                        fileRequest.delete();
                        throw new ExceptionsLibrary.NoAccountException();
                    }
                } else {
                    String requestPath = "Resources/Requests/" + request.getRequestId() + ".json";
                    File fileRequest = new File(requestPath);
                    fileRequest.delete();
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
                    Seller seller = (Seller) GetDataFromDatabase.getAccount(request.getRequestSeller());
                    seller.getSellerProducts().add(product);
                    try {
                        String productPath = "Resources/Products/" + product.getProductId() + ".json";
                        File file = new File(productPath);
                        file.createNewFile();
                        FileWriter fileWriter = new FileWriter(file);
                        fileWriter.write(productDetails);
                        fileWriter.close();
                        SetDataToDatabase.setAccount(seller);
                        String requestPath = "Resources/Requests/" + request.getRequestId() + ".json";
                        File fileRequest = new File(requestPath);
                        fileRequest.delete();
                    } catch (IOException e) {
                        String requestPath = "Resources/Requests/" + request.getRequestId() + ".json";
                        File fileRequest = new File(requestPath);
                        fileRequest.delete();
                        throw new ExceptionsLibrary.NoAccountException();
                    }
                } else {
                    String requestPath = "Resources/Requests/" + request.getRequestId() + ".json";
                    File fileRequest = new File(requestPath);
                    fileRequest.delete();
                }
                break;
            case EDIT_PODUCT:
                if (acceptStatus) {
                    Product product = gson.fromJson(request.getRequestDescription(), Product.class);
                    product.setProductCondition(ProductOrOffCondition.ACCEPTED);
                    String productDetails = gson.toJson(product);
                    Seller seller = (Seller) GetDataFromDatabase.getAccount(request.getRequestSeller());
                    Iterator iterator = seller.getSellerProducts().iterator();
                    while (iterator.hasNext()) {
                        Product tempProduct = (Product) iterator.next();
                        if (tempProduct.getProductId() == product.getProductId()) {
                            iterator.remove();
                        }
                    }
                    seller.getSellerProducts().add(product);
                    try {
                        String productPath = "Resources/Products/" + product.getProductId() + ".json";
                        String sellerPath = "Resources/Accounts/Seller/" + seller.getUsername() + ".json";
                        FileWriter fileWriter = new FileWriter(productPath);
                        fileWriter.write(productDetails);
                        fileWriter.close();
                        SetDataToDatabase.setAccount(seller);
                        SetDataToDatabase.updateSellerAndOffsOfProduct(product,0);
                        String requestPath = "Resources/Requests/" + request.getRequestId() + ".json";
                        File fileRequest = new File(requestPath);
                        fileRequest.delete();
                    } catch (IOException e) {
                        String requestPath = "Resources/Requests/" + request.getRequestId() + ".json";
                        File fileRequest = new File(requestPath);
                        fileRequest.delete();
                        throw new ExceptionsLibrary.NoAccountException();
                    }
                } else {
                    String requestPath = "Resources/Requests/" + request.getRequestId() + ".json";
                    File fileRequest = new File(requestPath);
                    fileRequest.delete();
                }
                break;
            case REGISTER_SELLER:
                if (acceptStatus) {
                    Seller seller = (Seller) GetDataFromDatabase.getAccount(request.getRequestSeller());
                    if (RegisterAndLogin.checkUsername(seller.getUsername())) {
                        try {
                            String sellerPath = "Resources/Accounts/Seller/" + seller.getUsername() + ".json";
                            File file = new File(sellerPath);
                            file.createNewFile();
                            FileWriter fileWriterSeller = new FileWriter(sellerPath);
                            Gson gsonSeller = new GsonBuilder().serializeNulls().create();
                            String sellerData = gsonSeller.toJson(seller);
                            fileWriterSeller.write(sellerData);
                            fileWriterSeller.close();
                            String requestPath = "Resources/Requests/" + request.getRequestId() + ".json";
                            File fileRequest = new File(requestPath);
                            fileRequest.delete();
                        } catch (IOException e) {
                            throw new ExceptionsLibrary.NoAccountException();
                        }
                    } else {
                        String requestPath = "Resources/Requests/" + request.getRequestId() + ".json";
                        File fileRequest = new File(requestPath);
                        fileRequest.delete();
                        throw new ExceptionsLibrary.UsernameAlreadyExists();
                    }
                } else {
                    String requestPath = "Resources/Requests/" + request.getRequestId() + ".json";
                    File fileRequest = new File(requestPath);
                    fileRequest.delete();
                }
                break;
        }
    }

    private static boolean checkIfOffExist(int offId) {
        String path = "Resources/Offs/" + offId + ".json";
        File file = new File(path);
        if (!file.exists()) {
            return false;
        } else {
            return true;
        }
    }

    public static ArrayList<Sale> showSales() throws ExceptionsLibrary.NoSaleException {
        ArrayList<Sale> allSales = new ArrayList<>();
        String path = "Resources/Sales";
        File file = new File(path);
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.getName().endsWith(".json")) {
                    return true;
                }
                return false;
            }
        };
        for (File i : file.listFiles(fileFilter)) {
            String fileName = i.getName();
            String saleCode = fileName.replace(".json", "");
            Sale sale = GetDataFromDatabase.getSale(saleCode);
            allSales.add(sale);
        }
        return allSales;
    }

    public static void editSaleInfo(String saleCode, HashMap<String, String> dataToEdit) throws ExceptionsLibrary.NoSaleException, ExceptionsLibrary.NoFeatureWithThisName, ExceptionsLibrary.CannotChangeThisFeature {
        Sale sale = GetDataFromDatabase.getSale(saleCode);
        for (String i : dataToEdit.keySet()) {
            try {
                if (i.equalsIgnoreCase("saleId")){
                    throw new ExceptionsLibrary.CannotChangeThisFeature();
                }
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
                throw new ExceptionsLibrary.NoFeatureWithThisName();
            }
        }
        File customerFolder = new File("Resources/Accounts/Customer");
        File sellerFolder = new File("Resources/Accounts/Seller");
        File adminFolder = new File("Resources/Accounts/Admin");

        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File file1) {
                if (file1.getName().endsWith(".json")) {
                    return true;
                }
                return false;
            }
        };

        for (File i : customerFolder.listFiles(fileFilter)) {
            Gson gson = new GsonBuilder().serializeNulls().create();
            try {
                String fileData = "";
                fileData = new String(Files.readAllBytes(Paths.get(i.getPath())));
                Customer customer = gson.fromJson(fileData, Customer.class);
                Iterator<Sale> iterator = customer.getSaleCodes().iterator();
                while (iterator.hasNext()) {
                    Sale tempSale = iterator.next();
                    if (tempSale.getSaleCode().equalsIgnoreCase(sale.getSaleCode())) {
                        iterator.remove();
                    }
                }
                customer.getSaleCodes().add(sale);
                SetDataToDatabase.setAccount(customer);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (File i : sellerFolder.listFiles(fileFilter)) {
            Gson gson = new GsonBuilder().serializeNulls().create();
            try {
                String fileData = "";
                fileData = new String(Files.readAllBytes(Paths.get(i.getPath())));
                Seller seller = gson.fromJson(fileData, Seller.class);

                Iterator<Sale> iterator = seller.getSaleCodes().iterator();
                while (iterator.hasNext()) {
                    Sale tempSale = iterator.next();
                    if (tempSale.getSaleCode().equalsIgnoreCase(sale.getSaleCode())) {
                        iterator.remove();
                    }
                }
                seller.getSaleCodes().add(sale);
                SetDataToDatabase.setAccount(seller);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (File i : adminFolder.listFiles(fileFilter)) {
            Gson gson = new GsonBuilder().serializeNulls().create();
            try {
                String fileData = "";
                fileData = new String(Files.readAllBytes(Paths.get(i.getPath())));
                Admin admin = gson.fromJson(fileData, Admin.class);
                Iterator<Sale> iterator = admin.getSaleCodes().iterator();
                while (iterator.hasNext()) {
                    Sale tempSale = iterator.next();
                    if (tempSale.getSaleCode().equalsIgnoreCase(sale.getSaleCode())) {
                        iterator.remove();
                    }
                }
                admin.getSaleCodes().add(sale);
                SetDataToDatabase.setAccount(admin);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Gson gson = new GsonBuilder().serializeNulls().create();
        String editedDetails = gson.toJson(sale);
        try {
            String path = "Resources/Sales/" + sale.getSaleCode() + ".json";
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(editedDetails);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addSale(Sale sale) {
        while (checkSaleCode(sale.getSaleCode())) {
            sale.setSaleCode(Sale.getRandomSaleCode());
        }
        for (Account i : sale.getSaleAccounts()) {
            if (i.getSaleCodes() == null) {
                i.setSaleCodes(new ArrayList<>());
            }
            i.getSaleCodes().add(sale);
            SetDataToDatabase.setAccount(i);
        }
        SetDataToDatabase.setSale(sale);
    }

    public static ArrayList<Account> showAllUsers() throws ExceptionsLibrary.NoAccountException {
        String customerPath = "Resources/Accounts/Customer";
        String sellerPath = "Resources/Accounts/Seller";
        String adminPath = "Resources/Accounts/Admin";
        ArrayList<Account> list = new ArrayList<>();
        File customerFolder = new File(customerPath);
        File sellerFolder = new File(sellerPath);
        File adminFolder = new File(adminPath);
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.getName().endsWith(".json")) {
                    return true;
                }
                return false;
            }
        };
        for (File i : customerFolder.listFiles(fileFilter)) {
            String fileName = i.getName();
            String username = fileName.replace(".json", "");
            Account account = GetDataFromDatabase.getAccount(username);
            list.add(account);
        }
        for (File i : sellerFolder.listFiles(fileFilter)) {
            String fileName = i.getName();
            String username = fileName.replace(".json", "");
            Account account = GetDataFromDatabase.getAccount(username);
            list.add(account);
        }

        for (File i : adminFolder.listFiles(fileFilter)) {
            String fileName = i.getName();
            String username = fileName.replace(".json", "");
            Account account = GetDataFromDatabase.getAccount(username);
            list.add(account);
        }
        return list;
    }

    public static String showUserDetails(String username) throws ExceptionsLibrary.NoAccountException {
        Account account = GetDataFromDatabase.getAccount(username);
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(account);
    }

    public static void deleteUser(String username) throws ExceptionsLibrary.NoAccountException {
        Account account = GetDataFromDatabase.getAccount(username);
        String path = "Resources/Accounts/" + account.getRole() + "/" + account.getUsername() + ".json";
        File file = new File(path);
        file.delete();
    }

    public static void addAdminAccount(String newAdminDetails) throws ExceptionsLibrary.UsernameAlreadyExists {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Admin admin1 = gson.fromJson(newAdminDetails,Admin.class);
        if (RegisterAndLogin.checkUsername(admin1.getUsername())) {
            RegisterAndLogin.registerAdmin(newAdminDetails);
        } else {
            throw new ExceptionsLibrary.UsernameAlreadyExists();
        }
    }

    public static void deleteProduct(int productId) throws ExceptionsLibrary.NoProductException, ExceptionsLibrary.NoAccountException {
        Product product = GetDataFromDatabase.getProduct(productId);
        String path = "Resources/Products/" + product.getProductId() + ".json";
        SetDataToDatabase.updateSellerAndOffsOfProduct(product,1);
        File file = new File(path);
        file.delete();
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

    public static void deleteCategory(String categoryName) throws ExceptionsLibrary.NoCategoryException {
        try {
            Category category = GetDataFromDatabase.getCategory(categoryName);
            FileFilter fileFilter = new FileFilter() {
                @Override
                public boolean accept(File file1) {
                    if (file1.getName().endsWith(".json")) {
                        return true;
                    }
                    return false;
                }
            };
            File productsFolder = new File("Resources/Products");
            for (File i : productsFolder.listFiles(fileFilter)) {
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    String fileData = "";
                    fileData = new String(Files.readAllBytes(Paths.get(i.getPath())));
                    Product product = gson.fromJson(fileData, Product.class);
                    if (product.getCategory().getName().equals(categoryName)) {
                        SetDataToDatabase.updateSellerAndOffsOfProduct(product,1);
                        i.delete();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (ExceptionsLibrary.NoAccountException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String path = "Resources/Category/" + category.getName() + ".json";
            File file = new File(path);
            file.delete();
        } catch (ExceptionsLibrary.NoCategoryException e) {
            throw new ExceptionsLibrary.NoCategoryException();
        }

    }

    public static void editCategory(String categoryName, HashMap<String, String> dataToEdit) throws ExceptionsLibrary.CategoryExistsWithThisName, ExceptionsLibrary.NoCategoryException, ExceptionsLibrary.NoFeatureWithThisName, ExceptionsLibrary.NoAccountException {
        Category category = GetDataFromDatabase.getCategory(categoryName);
        String oldName = category.getName();
        for (String i : dataToEdit.keySet()) {
            try {
                Field field = Category.class.getDeclaredField(i);
                if (i.equals("features")) {
                    field.setAccessible(true);
                    String[] splitFeatures = dataToEdit.get(i).split("\\s*,\\s*");
                    ArrayList<Feature> newFeatures = new ArrayList<>();
                    for (String j : splitFeatures) {
                        newFeatures.add(new Feature(j, null));
                    }
                    field.set(category, newFeatures);
                } else {
                    field.setAccessible(true);
                    field.set(category, dataToEdit.get(i));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new ExceptionsLibrary.NoFeatureWithThisName();
            }
        }
        Gson gson = new GsonBuilder().serializeNulls().create();
        String newName = category.getName();
        String editedDetails = gson.toJson(category);
        if (newName.equals(oldName)) {
            try {
                String path = "Resources/Category/" + category.getName() + ".json";
                FileWriter fileWriter = new FileWriter(path);
                fileWriter.write(editedDetails);
                fileWriter.close();
                File file = new File(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                String newPath = "Resources/Category/" + newName + ".json";
                String oldPath = "Resources/Category/" + oldName + ".json";
                File file = new File(newPath);
                if (file.exists()) {
                    throw new ExceptionsLibrary.CategoryExistsWithThisName();
                }
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(newPath);
                fileWriter.write(editedDetails);
                fileWriter.close();
                File file1 = new File(oldPath);
                file1.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File folder = new File("Resources/Products");
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
            Gson gson1 = new GsonBuilder().serializeNulls().create();
            try {
                String fileData = "";
                fileData = new String(Files.readAllBytes(Paths.get(i.getPath())));
                Product product = gson1.fromJson(fileData, Product.class);
                product.setCategory(category);
                SetDataToDatabase.setProduct(product);
                SetDataToDatabase.updateSellerAndOffsOfProduct(product,0);
            } catch (ExceptionsLibrary.NoAccountException | IOException e) {
                throw new ExceptionsLibrary.NoAccountException();
            }
        }
    }

    public static void addCategory(String categoryDetails) throws ExceptionsLibrary.CategoryExistsWithThisName {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Category category = gson.fromJson(categoryDetails, Category.class);
        if (!checkCategoryName(category.getName())) {
            String newSaleDetails = gson.toJson(category);
            try {
                String path = "Resources/Category/" + category.getName() + ".json";
                File file = new File(path);
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(path);
                fileWriter.write(newSaleDetails);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new ExceptionsLibrary.CategoryExistsWithThisName();
        }
    }

    public static String viewSaleCodeDetails(String saleCode) throws ExceptionsLibrary.NoSaleException {
        Sale sale = GetDataFromDatabase.getSale(saleCode);
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(sale);
    }

    public static void removeSaleCode(String saleCode) throws ExceptionsLibrary.NoSaleException {
        try {
            Sale sale = GetDataFromDatabase.getSale(saleCode);
            FileFilter fileFilter = new FileFilter() {
                @Override
                public boolean accept(File file1) {
                    if (file1.getName().endsWith(".json")) {
                        return true;
                    }
                    return false;
                }
            };
            File customerFolder = new File("Resources/Accounts/Customer");
            File sellerFolder = new File("Resources/Accounts/Seller");
            File adminFolder = new File("Resources/Accounts/Admin");

            for (File i : customerFolder.listFiles(fileFilter)) {
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    String fileData = "";
                    fileData = new String(Files.readAllBytes(Paths.get(i.getPath())));
                    Customer customer = gson.fromJson(fileData, Customer.class);
                    Iterator<Sale> iterator = customer.getSaleCodes().iterator();
                    while (iterator.hasNext()) {
                        Sale tempSale = iterator.next();
                        if (tempSale.getSaleCode().equalsIgnoreCase(sale.getSaleCode())) {
                            iterator.remove();
                        }
                    }
                    SetDataToDatabase.setAccount(customer);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            for (File i : sellerFolder.listFiles(fileFilter)) {
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    String fileData = "";
                    fileData = new String(Files.readAllBytes(Paths.get(i.getPath())));
                    Seller seller = gson.fromJson(fileData, Seller.class);
                    Iterator<Sale> iterator = seller.getSaleCodes().iterator();
                    while (iterator.hasNext()) {
                        Sale tempSale = iterator.next();
                        if (tempSale.getSaleCode().equalsIgnoreCase(sale.getSaleCode())) {
                            iterator.remove();
                        }
                    }
                    SetDataToDatabase.setAccount(seller);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            for (File i : adminFolder.listFiles(fileFilter)) {
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    String fileData = "";
                    fileData = new String(Files.readAllBytes(Paths.get(i.getPath())));
                    Admin admin = gson.fromJson(fileData, Admin.class);
                    Iterator<Sale> iterator = admin.getSaleCodes().iterator();
                    while (iterator.hasNext()) {
                        Sale tempSale = iterator.next();
                        if (tempSale.getSaleCode().equalsIgnoreCase(sale.getSaleCode())) {
                            iterator.remove();
                        }
                    }
                    SetDataToDatabase.setAccount(admin);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            String path = "Resources/Sales/" + sale.getSaleCode() + ".json";
            File file = new File(path);
            file.delete();
        } catch (ExceptionsLibrary.NoSaleException e) {
            throw new ExceptionsLibrary.NoSaleException();
        }

    }

    public static boolean checkCategoryName(String categoryName) {
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

    public static boolean checkSaleCode(String saleCode) {
        String path = "Resources/Sales";
        File folder = new File(path);
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
            String fileName = i.getName();
            String fileSaleCode = fileName.replace(".json", "");
            if (saleCode.equals(fileSaleCode)) {
                return true;
            }
        }
        return false;
    }


    public static boolean checkIfProductExist(int productId) {
        String path = "Resources/Products/" + productId + ".json";
        File file = new File(path);
        if (!file.exists()) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkIfRequestExist(int requestId) {
        String path = "Resources/Requests/" + requestId + ".json";
        File file = new File(path);
        if (!file.exists()) {
            return false;
        } else {
            return true;
        }
    }
}
