package Client.ClientController;

import Client.Client;
import model.*;
import LocalExceptions.ExceptionsLibrary;
import java.util.ArrayList;
import java.util.HashMap;

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
        if (getSeller() == null) {
            throw new ExceptionsLibrary.NoAccountException();
        }

        String func = "Show Seller Info";
        Client.sendMessage(func);

        String adminUsername = getSeller().getUsername();
        Client.sendMessage(adminUsername);

        Object data = Client.receiveObject();

        if(data instanceof Exception){
            throw new ExceptionsLibrary.NoAccountException();
        }
        else{
            return String.valueOf(data);
        }
//        Gson gson = new GsonBuilder().serializeNulls().create();
//        if (getSeller() == null) {
//            throw new ExceptionsLibrary.NoAccountException();
//        }
//        Seller seller = (Seller) GetDataFromDatabase.getAccount(getSeller().getUsername());
//        setSeller(seller);
//        String data = gson.toJson(seller);
//        return data;
    }

    public static void editSellerInfo(HashMap<String, String> dataToEdit) throws ExceptionsLibrary.NoAccountException, ExceptionsLibrary.NoFeatureWithThisName, ExceptionsLibrary.ChangeUsernameException {

        for (String i : dataToEdit.keySet()) {
            if (i.equals("username")) {
                throw new ExceptionsLibrary.ChangeUsernameException();
            }
            String func = "Edit Seller Info";
            Client.sendMessage(func);

            Object[] toSend = new Object[2];
            toSend[0] = getSeller().getUsername();
            toSend[1] = dataToEdit;
            Client.sendObject(toSend);
            Object response = Client.receiveObject();

            if (response instanceof ExceptionsLibrary.NoFeatureWithThisName)
                throw new ExceptionsLibrary.NoFeatureWithThisName();
            if (response instanceof ExceptionsLibrary.NoAccountException)
                throw new ExceptionsLibrary.NoAccountException();
            else
                return;
//        Seller seller = (Seller) GetDataFromDatabase.getAccount(getSeller().getUsername());
//        for (String i : dataToEdit.keySet()) {
//            if (i.equals("username")) {
//                throw new ExceptionsLibrary.ChangeUsernameException();
//            }
//            try {
//                try {
//                    Field field = Seller.class.getSuperclass().getDeclaredField(i);
//                    field.setAccessible(true);
//                    field.set(seller, dataToEdit.get(i));
//                } catch (NoSuchFieldException e) {
//                    Field field = Seller.class.getDeclaredField(i);
//                    field.setAccessible(true);
//                    field.set(seller, dataToEdit.get(i));
//                }
//            } catch (NoSuchFieldException | IllegalAccessException e) {
//                throw new ExceptionsLibrary.NoFeatureWithThisName();
//            }
//        }
//        Gson gson = new GsonBuilder().serializeNulls().create();
//        setSeller(seller);
//        String editedDetails = gson.toJson(seller);
//        try {
//            String path = "Resources/Accounts/Seller/" + getSeller().getUsername() + ".json";
//            FileWriter fileWriter = new FileWriter(path);
//            fileWriter.write(editedDetails);
//            fileWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        }
    }
    public static String showSellerCompany() {

        String func = "Show Seller Company";
        Client.sendMessage(func);

        Client.sendObject(getSeller());

        return (String) Client.receiveObject();
    }

    public static ArrayList<Product> showSellerProducts() throws ExceptionsLibrary.NoAccountException {

        ArrayList<Product> products = new ArrayList<Product>();

        String func = "Show Seller Products";
        Client.sendMessage(func);

        Client.sendObject(getSeller());

        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoAccountException)
            throw new ExceptionsLibrary.NoAccountException();

        else
            products = (ArrayList<Product>) response;

        return products;
//        Seller seller = (Seller) GetDataFromDatabase.getAccount(getSeller().getUsername());
//        return seller.getSellerProducts();
    }

    public static ArrayList<SellLog> showSellerLogs() {

        String func = "Show Seller Logs";
        Client.sendMessage(func);

        Client.sendObject(getSeller());

        return (ArrayList<SellLog>) Client.receiveObject();
//        return getSeller().getSellerLogs();
    }

    public static void editProductRequest(int productId, HashMap<String, String> dataToEdit) throws ExceptionsLibrary.NoProductException, ExceptionsLibrary.NoFeatureWithThisName, ExceptionsLibrary.CannotChangeThisFeature {

        String func = "Edit Product Request";
        Client.sendMessage(func);

        Object[] toSend = new Object[2];
        toSend[0] = productId;
        toSend[1] = dataToEdit;
        Client.sendObject(toSend);

        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoProductException)
            throw new ExceptionsLibrary.NoProductException();
        else if (response instanceof ExceptionsLibrary.NoFeatureWithThisName)
            throw new ExceptionsLibrary.NoFeatureWithThisName();
        else if (response instanceof ExceptionsLibrary.CannotChangeThisFeature)
            throw new ExceptionsLibrary.CannotChangeThisFeature();
        else
            return;
//        Product product = GetDataFromDatabase.getProduct(productId);
//        if (product != null) {
//            for (String i : dataToEdit.keySet()) {
//                try {
//                    if (i.equalsIgnoreCase("productId")){
//                        throw new ExceptionsLibrary.CannotChangeThisFeature();
//                    }
//                    Field field = Product.class.getDeclaredField(i);
//                    if (i.equals("price")) {
//                        field.setAccessible(true);
//                        field.set(product, Double.parseDouble(dataToEdit.get(i)));
//                    } else if (i.equals("availability")) {
//                        field.setAccessible(true);
//                        field.set(product, Integer.parseInt(dataToEdit.get(i)));
//                    } else {
//                        field.setAccessible(true);
//                        field.set(product, dataToEdit.get(i));
//                    }
//                } catch (NoSuchFieldException | IllegalAccessException e) {
//                    throw new ExceptionsLibrary.NoFeatureWithThisName();
//                }
//            }
//            Gson gsonProduct = new GsonBuilder().serializeNulls().create();
//            product.setProductCondition(ProductOrOffCondition.PENDING_TO_EDIT);
//            String editedProduct = gsonProduct.toJson(product);
//            Request request = new Request(editedProduct, RequestType.EDIT_PRODUCT, RequestOrCommentCondition.PENDING_TO_ACCEPT, getSeller().getUsername());
//            Gson gsonRequest = new GsonBuilder().serializeNulls().create();
//            String requestDetails = gsonRequest.toJson(request);
//            try {
//                String path = "Resources/Requests/" + request.getRequestId() + ".json";
//                FileWriter fileWriter = new FileWriter(path);
//                fileWriter.write(requestDetails);
//                fileWriter.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            throw new ExceptionsLibrary.NoProductException();
//        }
    }

    public static void removeProductRequest(int productId) throws ExceptionsLibrary.NoProductException, ExceptionsLibrary.NoAccountException {

        String func = "Remove Product Request";
        Client.sendMessage(func);

        Client.sendObject(productId);

        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoProductException)
            throw new ExceptionsLibrary.NoProductException();
        else if (response instanceof ExceptionsLibrary.NoAccountException)
            throw new ExceptionsLibrary.NoAccountException();
        else
            return;
//        Product product = GetDataFromDatabase.getProduct(productId);
//        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().serializeNulls().create();
//        String productDetails = gson.toJson(product);
//        Request request = new Request(productDetails,RequestType.REMOVE_PRODUCT,RequestOrCommentCondition.PENDING_TO_ACCEPT,getSeller().getUsername());
//        Gson gsonRequest = new GsonBuilder().enableComplexMapKeySerialization().serializeNulls().create();
//        try {
//            String path = "Resources/Requests/" + request.getRequestId() + ".json";
//            while (Files.exists(Paths.get(path))) {
//                Random random = new Random();
//                request.setRequestId(random.nextInt(1000000));
//                path = "Resources/Requests/" + request.getRequestId() + ".json";
//            }
//            String requestDetails = gsonRequest.toJson(request);
//            FileWriter fileWriter = new FileWriter(path);
//            fileWriter.write(requestDetails);
//            fileWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void addProductRequest(Product product) {

        String func = "Add Product Request";
        Client.sendMessage(func);

        Client.sendObject(product);
        return;
//        product.setSeller(getSeller());
//        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().serializeNulls().create();
//        String productDetails = gson.toJson(product);
//        Request request = new Request(productDetails, RequestType.ADD_PRODUCT, RequestOrCommentCondition.PENDING_TO_ACCEPT, getSeller().getUsername());
//        Gson gsonRequest = new GsonBuilder().enableComplexMapKeySerialization().serializeNulls().create();
//        try {
//            String path = "Resources/Requests/" + request.getRequestId() + ".json";
//            while (Files.exists(Paths.get(path))) {
//                Random random = new Random();
//                request.setRequestId(random.nextInt(1000000));
//                path = "Resources/Requests/" + request.getRequestId() + ".json";
//            }
//            String requestDetails = gsonRequest.toJson(request);
//            FileWriter fileWriter = new FileWriter(path);
//            fileWriter.write(requestDetails);
//            fileWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    public static void editOffRequest(int offId, HashMap<String, String> dataToEdit) throws ExceptionsLibrary.NoOffException, ExceptionsLibrary.NoFeatureWithThisName, ExceptionsLibrary.CannotChangeThisFeature {

        String func = "Edit Off Request";
        Client.sendObject(func);

        Object[] toSend = new Object[2];
        toSend[0] = offId;
        toSend[1] = dataToEdit;
        Client.sendObject(toSend);

        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoOffException)
            throw new ExceptionsLibrary.NoOffException();
        else if (response instanceof ExceptionsLibrary.NoFeatureWithThisName)
            throw new ExceptionsLibrary.NoFeatureWithThisName();
        else if (response instanceof ExceptionsLibrary.CannotChangeThisFeature)
            throw new ExceptionsLibrary.CannotChangeThisFeature();
        else
            return;
//        Off off = GetDataFromDatabase.getOff(offId);
//        if (off != null) {
//            for (Off i : getSeller().getSellerOffs()) {
//                if (i.getOffId() == off.getOffId()) {
//                    for (String s : dataToEdit.keySet()) {
//                        try {
//                            if (s.equalsIgnoreCase("offId")){
//                                throw new ExceptionsLibrary.CannotChangeThisFeature();
//                            }
//                            Field field = Off.class.getDeclaredField(s);
//                            if (s.equals("offAmount")) {
//                                field.setAccessible(true);
//                                field.set(off, Double.parseDouble(dataToEdit.get(s)));
//                            } else {
//                                field.setAccessible(true);
//                                field.set(off, dataToEdit.get(s));
//                            }
//                        } catch (NoSuchFieldException | IllegalAccessException e) {
//                            throw new ExceptionsLibrary.NoFeatureWithThisName();
//                        }
//                    }
//                    off.setOffCondition(ProductOrOffCondition.PENDING_TO_EDIT);
//                    Gson gsonOff = new GsonBuilder().serializeNulls().create();
//                    String editedOff = gsonOff.toJson(off);
//                    Request request = new Request(editedOff, RequestType.EDIT_OFF, RequestOrCommentCondition.PENDING_TO_ACCEPT, getSeller().getUsername());
//                    Gson gsonRequest = new GsonBuilder().serializeNulls().create();
//                    String requestDetails = gsonRequest.toJson(request);
//                    try {
//                        String path = "Resources/Requests/" + request.getRequestId() + ".json";
//                        FileWriter fileWriter = new FileWriter(path);
//                        fileWriter.write(requestDetails);
//                        fileWriter.close();
//                        return;
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        } else {
//            throw new ExceptionsLibrary.NoOffException();
//        }
    }

    public static void addOffRequest(Off off, String offProducts) throws ExceptionsLibrary.NoProductException {

        String func = "Add Off Request";
        Client.sendMessage(func);

        Object[] toSend = new Object[2];
        toSend[0] = off;
        toSend[1] = offProducts;
        Client.sendObject(toSend);

        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoProductException)
            throw new ExceptionsLibrary.NoProductException();
        else
            return;
//        Gson gsonOff = new GsonBuilder().serializeNulls().create();
//        off.setOffCondition(ProductOrOffCondition.PENDING_TO_CREATE);
//        ArrayList<String> offProductsList = new ArrayList<>();
//        String[] productIds = offProducts.split("\\s*,\\s*");
//        for (String i : productIds){
//            try {
//                Product temp = GetDataFromDatabase.getProduct(Integer.parseInt(i));
//                Iterator<Product> iterator = getSeller().getSellerProducts().iterator();
//                while (iterator.hasNext()) {
//                    Product tempProduct = iterator.next();
//                    if (tempProduct.getProductId() == temp.getProductId()) {
//                        offProductsList.add(i);
//                    }
//                }
//            }
//            catch (Exception e){
//                throw new ExceptionsLibrary.NoProductException();
//            }
//        }
//        off.setOffProducts(offProductsList);
//        String offDetails = gsonOff.toJson(off);
//        Request request = new Request(offDetails, RequestType.ADD_OFF, RequestOrCommentCondition.PENDING_TO_ACCEPT, getSeller().getUsername());
//        Gson gsonRequest = new GsonBuilder().serializeNulls().create();
//        if (!Files.exists(Paths.get("Resources/Requests"))) {
//            File folder = new File("Resources/Requests");
//            folder.mkdir();
//        }
//        try {
//            String path = "Resources/Requests/" + request.getRequestId() + ".json";
//            while (Files.exists(Paths.get(path))) {
//                Random random = new Random();
//                request.setRequestId(random.nextInt(1000000));
//                path = "Resources/Requests/" + request.getRequestId() + ".json";
//            }
//            String requestDetails = gsonRequest.toJson(request);
//            FileWriter fileWriter = new FileWriter(path);
//            fileWriter.write(requestDetails);
//            fileWriter.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static ArrayList<Off> showOffs() throws ExceptionsLibrary.NoAccountException {

        ArrayList<Off> offs = new ArrayList<>();
        String func = "Show Offs";
        Client.sendMessage(func);

        Client.sendObject(getSeller());

        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoAccountException)
            throw new ExceptionsLibrary.NoAccountException();
        else
            offs = (ArrayList<Off>) response;

        return offs;

//        Seller seller = (Seller) GetDataFromDatabase.getAccount(getSeller().getUsername());
//        return seller.getSellerOffs();
    }

    public static Off showOffDetails(int offId) throws ExceptionsLibrary.NoOffException {

        String func = "Show Off Details";
        Client.sendMessage(func);

        Client.sendObject(offId);

        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoOffException)
            throw new ExceptionsLibrary.NoOffException();
        else
            return (Off) response;
//        for (Off i : getSeller().getSellerOffs()) {
//            if (i.getOffId() == offId) {
//                return i;
//            }
//        }
//        throw new ExceptionsLibrary.NoOffException();
    }

    public static Product showProductDetails(int productId) throws ExceptionsLibrary.NoProductException {

        String func = "Show Product Details";
        Client.sendMessage(func);

        Client.sendObject(productId);

        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoProductException)
            throw new ExceptionsLibrary.NoProductException();
        else
            return (Product) response;
//        Product product = null;
//        try {
//            product = GetDataFromDatabase.getProduct(productId);
//        } catch (ExceptionsLibrary.NoProductException e) {
//            throw new ExceptionsLibrary.NoProductException();
//        }
//        return product;
    }

    public static ArrayList<SellLog> showProductBuyers(int productId) throws ExceptionsLibrary.NoProductException, ExceptionsLibrary.NoAccountException {
        ArrayList<SellLog> buyersLogs = new ArrayList<>();

        String func = "Show Product Buyers";
        Client.sendMessage(func);

        Client.sendObject(productId);

        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoProductException)
            throw new ExceptionsLibrary.NoProductException();
        else if (response instanceof ExceptionsLibrary.NoAccountException)
            throw new ExceptionsLibrary.NoAccountException();
        else
            buyersLogs = (ArrayList<SellLog>) response;
//        Product product = GetDataFromDatabase.getProduct(productId);
//        Seller seller = (Seller) GetDataFromDatabase.getAccount(getSeller().getUsername());
//        for (SellLog i : seller.getSellerLogs()) {
//            for (String[] j : i.getLogProducts()) {
//                if (Integer.parseInt(j[0]) == productId) {
//                    buyersLogs.add(i);
//                }
//            }
//        }
        return buyersLogs;
    }

    public static double showBalance() {

        String func = "Show Balance";
        Client.sendMessage(func);

        Client.sendObject(getSeller());

        return (double) Client.receiveObject();
//        return getSeller().getCredit();
    }


    public static ArrayList<Category> showCategories() throws ExceptionsLibrary.NoCategoryException {
        ArrayList<Category> allCategories = new ArrayList<>();

        String func = "Show Categories";
        Client.sendMessage(func);

        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoCategoryException)
            throw new ExceptionsLibrary.NoCategoryException();
        else
            allCategories = (ArrayList<Category>) response;
//        String path = "Resources/Category";
//        File file = new File(path);
//        FileFilter fileFilter = new FileFilter() {
//            @Override
//            public boolean accept(File file1) {
//                if (file1.getName().endsWith(".json")) {
//                    return true;
//                }
//                return false;
//            }
//        };
//        for (File i : file.listFiles(fileFilter)) {
//            String fileName = i.getName();
//            String categoryName = fileName.replace(".json", "");
//            Category category = GetDataFromDatabase.getCategory(categoryName);
//            allCategories.add(category);
//        }
        return allCategories;
    }

    public static boolean checkIfCategoryExists(String categoryName) {

        String func = "Check If Category Exists";
        Client.sendMessage(func);

        Client.sendMessage(categoryName);

        return (boolean) Client.receiveObject();
//        String path = "Resources/Category";
//        File folder = new File(path);
//        FileFilter fileFilter = new FileFilter() {
//            @Override
//            public boolean accept(File file1) {
//                if (file1.getName().endsWith(".json")) {
//                    return true;
//                }
//                return false;
//            }
//        };
//        for (File i : folder.listFiles(fileFilter)) {
//            String fileName = i.getName();
//            String fileCategoryName = fileName.replace(".json", "");
//            if (categoryName.equals(fileCategoryName)) {
//                return true;
//            }
//        }
//        return false;
    }

    public static ArrayList<Request> showSellerRequests() throws ExceptionsLibrary.NoRequestException {
        ArrayList<Request> sellerRequests = new ArrayList<>();

        String func = "Show Seller Requests";
        Client.sendMessage(func);

        Client.sendObject(getSeller());

        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoRequestException)
            throw new ExceptionsLibrary.NoRequestException();
        else
            sellerRequests = (ArrayList<Request>) response;
//        String path = "Resources/Requests";
//        File file = new File(path);
//        FileFilter fileFilter = new FileFilter() {
//            @Override
//            public boolean accept(File file1) {
//                if (file1.getName().endsWith(".json")) {
//                    return true;
//                }
//                return false;
//            }
//        };
//        for (File i : file.listFiles(fileFilter)) {
//            String fileName = i.getName();
//            String requestId = fileName.replace(".json", "");
//            Request request = GetDataFromDatabase.getRequest(Integer.parseInt(requestId));
//            if (request.getRequestSeller().equals(getSeller().getUsername())) {
//                sellerRequests.add(request);
//            }
//        }
        return sellerRequests;
    }

}
