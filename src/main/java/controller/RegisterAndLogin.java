package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;
import view.MessagesLibrary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Random;

public class RegisterAndLogin {
    public static void register(String dataToRegister) {
        Gson gson = new Gson();
        Account account = gson.fromJson(dataToRegister, Account.class);
        String username = account.getUsername();
        if (!Files.exists(Paths.get("src/main/resources/Accounts"))) {
            File folder = new File("src/main/resources/Accounts");
            folder.mkdir();
        }
        if (Files.exists(Paths.get("src/main/resources/Accounts"))) {
            if (!Files.exists(Paths.get("src/main/resources/Accounts/Customer"))) {
                File folder1 = new File("src/main/resources/Accounts/Customer");
                folder1.mkdir();
            }
            if (!Files.exists(Paths.get("src/main/resources/Accounts/Admin"))) {
                File folder1 = new File("src/main/resources/Accounts/Admin");
                folder1.mkdir();
            }
            if (!Files.exists(Paths.get("src/main/resources/Accounts/Seller"))) {
                File folder1 = new File("src/main/resources/Accounts/Seller");
                folder1.mkdir();
            }
        }
        String role = account.getRole();
        String accountPath = "src/main/resources/Accounts/" + role + "/" + username + ".json";
        File file = new File(accountPath);
        if (!checkUsername(username)) {
            MessagesLibrary.errorLibrary(3);
        } else {
            if (role.equals("Admin")){
                if (new File("src/main/resources/Accounts/Admin").listFiles().length!=0){
                    MessagesLibrary.errorLibrary(10);
                }
                else {
                    String firstAdminPath = "src/main/resources/Accounts/Admin" + account.getUsername() + ".json";
                    try {
                        File adminFile = new File(firstAdminPath);
                        adminFile.createNewFile();
                        FileWriter fileWriter = new FileWriter(adminFile);
                        fileWriter.write(dataToRegister);
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (role.equals("Seller")){
                Gson gsonSeller =new GsonBuilder().serializeNulls().create();
                Seller seller = gsonSeller.fromJson(dataToRegister,Seller.class);
                Request request = new Request(dataToRegister,RequestType.REGISTER_SELLER,RequestOrCommentCondition.PENDING_TO_ACCEPT,seller);
                String requestPath="src/main/resources/Requests/" + request.getRequestId() + ".json";
                while (Files.exists(Paths.get(requestPath))) {
                    Random random = new Random();
                    request.setRequestId(random.nextInt(10000));
                }
                Gson gsonRequest = new GsonBuilder().serializeNulls().create();
                String requestDetails = gsonRequest.toJson(request);
                try {
                    FileWriter fileWriter = new FileWriter(requestPath);
                    fileWriter.write(requestDetails);
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MessagesLibrary.messagesLibrary(8);
            }
            else {
                try (FileWriter fileWriter = new FileWriter(file)) {
                    fileWriter.write(dataToRegister);
                    fileWriter.close();
                    MessagesLibrary.messagesLibrary(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean checkUsername(String username) {
        File folder1 = new File("src/main/resources/Accounts/Customer");
        File folder2 = new File("src/main/resources/Accounts/Admin");
        File folder3 = new File("src/main/resources/Accounts/Seller");
        if (new File(folder1,username+".json").exists()){
            return false;
        }
        else if (new File(folder2,username+".json").exists()){
            return false;
        }
        else return !new File(folder3, username + ".json").exists();
    }

    public static String login(HashMap<String, String> dataToLogin) {
        Account account = GetDataFromDatabase.getAccount(dataToLogin.get("username"));
        if (account != null) {
            if (account.getPassword().equals(dataToLogin.get("password"))) {
                MessagesLibrary.messagesLibrary(2);
                if (account.getRole().equals("Customer")) {
                    CustomerController customerController = new CustomerController((Customer) account);
                    return account.getRole();
                } else if (account.getRole().equals("Seller")) {
                    SellerController sellerController = new SellerController((Seller) account);
                    return account.getRole();
                } else if (account.getRole().equals("Admin")) {
                    AdminController adminController = new AdminController((Admin) account);
                    return account.getRole();
                }
            } else {
                MessagesLibrary.errorLibrary(2);
                return null;
            }
        } else {
            MessagesLibrary.errorLibrary(1);
            return null;
        }
        return null;
    }
}
