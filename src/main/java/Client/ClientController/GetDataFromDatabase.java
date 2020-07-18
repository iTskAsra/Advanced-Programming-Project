package Client.ClientController;
import Client.Client;
import model.*;

import java.util.ArrayList;

public class GetDataFromDatabase {


    public static Account getAccount(String username) throws ExceptionsLibrary.NoAccountException {

        String func = "Get Account";
        Client.sendMessage(func);

        Client.sendMessage(username);

        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoAccountException)
            throw new ExceptionsLibrary.NoAccountException();
        else
            return (Account) response;
//        String pathCustomer = "Resources/Accounts/Customer/" + username + ".json";
//        String pathAdmin = "Resources/Accounts/Admin/" + username + ".json";
//        String pathSeller = "Resources/Accounts/Seller/" + username + ".json";
//        File fileCustomer = new File(pathCustomer);
//        File fileAdmin = new File(pathAdmin);
//        File fileSeller = new File(pathSeller);
//        if (fileCustomer.exists() && !fileAdmin.exists() && !fileSeller.exists()) {
//            try {
//                String fileData = "";
//                fileData = new String(Files.readAllBytes(Paths.get(pathCustomer)));
//                Gson gson = new GsonBuilder().serializeNulls().create();
//                Customer account = gson.fromJson(fileData, Customer.class);
//                return account;
//            } catch (FileNotFoundException e) {
//                throw new ExceptionsLibrary.NoAccountException();
//            } catch (IOException e) {
//                throw new ExceptionsLibrary.NoAccountException();
//            }
//
//        } else if (!fileCustomer.exists() && fileAdmin.exists() && !fileSeller.exists()) {
//            try {
//                String fileData = "";
//                fileData = new String(Files.readAllBytes(Paths.get(pathAdmin)));
//                Gson gson = new GsonBuilder().serializeNulls().create();
//                Admin account = gson.fromJson(fileData, Admin.class);
//                return account;
//            } catch (IOException e) {
//                throw new ExceptionsLibrary.NoAccountException();
//            }
//        } else if (!fileCustomer.exists() && !fileAdmin.exists() && fileSeller.exists()) {
//            try {
//                String fileData = "";
//                fileData = new String(Files.readAllBytes(Paths.get(pathSeller)));
//                Gson gson = new GsonBuilder().serializeNulls().create();
//                Seller account = gson.fromJson(fileData, Seller.class);
//                return account;
//            } catch (FileNotFoundException e) {
//                throw new ExceptionsLibrary.NoAccountException();
//            } catch (IOException e) {
//                throw new ExceptionsLibrary.NoAccountException();
//            }
//        } else if (!fileCustomer.exists() && !fileAdmin.exists() && !fileSeller.exists()) {
//            throw new ExceptionsLibrary.NoAccountException();
//        }
//        return null;
    }

    public static Product getProduct(int productId) throws ExceptionsLibrary.NoProductException {

        String func = "Get Product";
        Client.sendMessage(func);

        Client.sendObject(productId);

        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoProductException)
            throw new ExceptionsLibrary.NoProductException();
        else
            return (Product) response;
//        String productPath = "Resources/Products/" + (productId) + ".json";
//        File fileProduct = new File(productPath);
//        try {
//            String fileData = "";
//            fileData = new String(Files.readAllBytes(Paths.get(productPath)));
//            Gson gson = new GsonBuilder().serializeNulls().create();
//            Product product = gson.fromJson(fileData, Product.class);
//            return product;
//        } catch (FileNotFoundException e) {
//            throw new ExceptionsLibrary.NoProductException();
//        } catch (IOException e) {
//            throw new ExceptionsLibrary.NoProductException();
//        }
    }


    public static Off getOff(int offId) throws ExceptionsLibrary.NoOffException {

        String func = "Get Off";
        Client.sendMessage(func);

        Client.sendObject(offId);

        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoOffException)
            throw new ExceptionsLibrary.NoOffException();
        else
            return (Off) response;
//        if (!Files.exists(Paths.get("Resources/Offs"))) {
//            File folder = new File("Resources/Offs");
//            folder.mkdir();
//        }
//        String offPath = "Resources/Offs/" + (offId) + ".json";
//        File fileProduct = new File(offPath);
//        try {
//            String fileData = "";
//            fileData = new String(Files.readAllBytes(Paths.get(offPath)));
//            Gson gson = new GsonBuilder().serializeNulls().create();
//            Off off = gson.fromJson(fileData, Off.class);
//            return off;
//        } catch (FileNotFoundException e) {
//            throw new ExceptionsLibrary.NoOffException();
//        } catch (IOException e) {
//            throw new ExceptionsLibrary.NoOffException();
//        }
    }

    public static Request getRequest(int requestId) throws ExceptionsLibrary.NoRequestException {

        String func = "Get Request";
        Client.sendMessage(func);

        Client.sendObject(requestId);

        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoRequestException)
            throw new ExceptionsLibrary.NoRequestException();
        else
            return (Request) response;
//        if (!Files.exists(Paths.get("Resources/Requests"))) {
//            File folder = new File("Resources/Requests");
//            folder.mkdir();
//        }
//        String requestPath = "Resources/Requests/" + (requestId) + ".json";
//        File fileProduct = new File(requestPath);
//        try {
//            String fileData = "";
//            fileData = new String(Files.readAllBytes(Paths.get(requestPath)));
//            Gson gson = new GsonBuilder().serializeNulls().create();
//            Request request = gson.fromJson(fileData, Request.class);
//
//            return request;
//        } catch (FileNotFoundException e) {
//            throw new ExceptionsLibrary.NoRequestException();
//        } catch (IOException e) {
//            throw new ExceptionsLibrary.NoRequestException();
//        }
    }

    public static Sale getSale(String saleCode) throws ExceptionsLibrary.NoSaleException {

        String func = "Get Sale";
        Client.sendMessage(func);

        Client.sendObject(saleCode);

        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoSaleException)
            throw new ExceptionsLibrary.NoSaleException();
        else
            return (Sale) response;

//        if (!Files.exists(Paths.get("Resources/Sales"))) {
//            File folder = new File("Resources/Sales");
//            folder.mkdir();
//        }
//        String salePath = "Resources/Sales/" + (saleCode) + ".json";
//        File fileProduct = new File(salePath);
//        try {
//            String fileData = "";
//            fileData = new String(Files.readAllBytes(Paths.get(salePath)));
//            Gson gson = new GsonBuilder().serializeNulls().create();
//            Sale sale = gson.fromJson(fileData, Sale.class);
//            return sale;
//        } catch (IOException e) {
//            throw new ExceptionsLibrary.NoSaleException();
//        }
    }

    public static Category getCategory(String categoryName) throws ExceptionsLibrary.NoCategoryException {

        String func = "Get Category";
        Client.sendMessage(func);

        Client.sendObject(categoryName);

        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoCategoryException)
            throw new ExceptionsLibrary.NoCategoryException();
        else
            return (Category) response;

//        if (!Files.exists(Paths.get("Resources/Category"))) {
//            File folder = new File("Resources/Category");
//            folder.mkdir();
//        }
//        String categoryPath = "Resources/Category/" + (categoryName) + ".json";
//        File fileCategory = new File(categoryPath);
//        try {
//            String fileData = "";
//            fileData = new String(Files.readAllBytes(Paths.get(categoryPath)));
//            Gson gson = new GsonBuilder().serializeNulls().create();
//            Category category = gson.fromJson(fileData, Category.class);
//            return category;
//        } catch (IOException e) {
//            throw new ExceptionsLibrary.NoCategoryException();
//        }
    }

    public static boolean checkIfAnyAdminExists() {

        String func = "Check If Any Admin Exists";
        Client.sendMessage(func);

        return (boolean) Client.receiveObject();
//        return new File("Resources/Accounts/Admin").listFiles().length==0;
    }

    public static ArrayList<Seller> findSellersFromProductId(int productId) {

        String func = "Find Sellers From Product Id";
        Client.sendMessage(func);

        Client.sendObject(productId);

        return (ArrayList<Seller>) Client.receiveObject();
//        File folder = new File("Resources/Accounts/Seller");
//        ArrayList<Seller> sellers =new ArrayList<>();
//        FileFilter fileFilter = new FileFilter() {
//            @Override
//            public boolean accept(File file) {
//                if (file.getName().endsWith(".json")) {
//                    return true;
//                }
//                return false;
//            }
//        };
//        for (File i : folder.listFiles(fileFilter)) {
//            try {
//                String fileData = "";
//                fileData = new String(Files.readAllBytes(Paths.get(i.getPath())));
//                Gson gson = new GsonBuilder().serializeNulls().create();
//                Seller seller = gson.fromJson(fileData, Seller.class);
//                for (Product j : seller.getSellerProducts()){
//                    if (j.getProductId() == productId){
//                        sellers.add(seller);
//                    }
//                }
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return sellers;
    }
}
