package controller;

import com.google.gson.Gson;
import model.Account;
import model.Admin;
import model.Customer;
import model.Seller;
import view.MessagesLibrary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

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

        String path = "src/main/resources/Accounts/" + role + "/" + username + ".json";
        File file = new File(path);
        File folder = new File("src/main/resources/Accounts");
        if (!checkUsername(username)) {
            MessagesLibrary.errorLibrary(3);
        } else {
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(dataToRegister);
                fileWriter.close();
                MessagesLibrary.messagesLibrary(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean checkUsername(String username) {
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

    public static void login(HashMap<String, String> dataToLogin) {
        Account account = GetDataFromDatabase.getAccount(dataToLogin.get("username"));
        if (account != null) {
            if (account.getPassword().equals(dataToLogin.get("password"))) {
                MessagesLibrary.messagesLibrary(2);
                if (account.getRole().equals("Customer")) {
                    CustomerController customerController = new CustomerController((Customer) account);
                } else if (account.getRole().equals("Seller")) {
                    SellerController sellerController = new SellerController((Seller) account);
                } else if (account.getRole().equals("Admin")) {
                    AdminController adminController = new AdminController((Admin) account);
                }
            } else MessagesLibrary.errorLibrary(2);
        } else {
            MessagesLibrary.errorLibrary(1);//Show Error account not found!
        }

    }
}
