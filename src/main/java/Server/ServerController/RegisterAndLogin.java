package Server.ServerController;

import Client.Client;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Random;

public class RegisterAndLogin {
    public static void register() throws ExceptionsLibrary.AdminExist, ExceptionsLibrary.UsernameAlreadyExists {

        String dataToRegister = Client.receiveMessage();

        Gson gson = new GsonBuilder().serializeNulls().create();
        Account account = gson.fromJson(dataToRegister, Account.class);
        String username = account.getUsername();
        String role = account.getRole();
        String accountPath = "Resources/Accounts/" + role + "/" + username + ".json";
        File file = new File(accountPath);
        if (!checkUsername(username)) {
            Client.sendObject(new ExceptionsLibrary.UsernameAlreadyExists());
            return;
        } else {
            if (role.equals("Admin")) {
                if (new File("Resources/Accounts/Admin").listFiles().length != 0) {
                    Client.sendObject(new ExceptionsLibrary.AdminExist());
                    return;
                } else {
                    String firstAdminPath = "Resources/Accounts/Admin" + account.getUsername() + ".json";
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
            } else if (role.equals("Seller")) {
                Gson gsonSeller = new GsonBuilder().serializeNulls().create();
                Seller seller = gsonSeller.fromJson(dataToRegister, Seller.class);
                Request request = new Request(dataToRegister, RequestType.REGISTER_SELLER, RequestOrCommentCondition.PENDING_TO_ACCEPT, seller.getUsername());
                String requestPath = "Resources/Requests/" + request.getRequestId() + ".json";
                while (Files.exists(Paths.get(requestPath))) {
                    Random random = new Random();
                    request.setRequestId(random.nextInt(1000000));
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
            } else {
                try (FileWriter fileWriter = new FileWriter(file)) {
                    fileWriter.write(dataToRegister);
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Client.sendObject("Done");
    }

    public static void registerAdmin() {
        String data = Client.receiveMessage();
        Gson gson = new GsonBuilder().serializeNulls().create();
        Admin admin = gson.fromJson(data, Admin.class);
        File file = new File("Resources/Accounts/Admin/" + admin.getUsername() + ".json");
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(data);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void registerAdmin(String data) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Admin admin = gson.fromJson(data, Admin.class);
        File file = new File("Resources/Accounts/Admin/" + admin.getUsername() + ".json");
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(data);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void login() throws ExceptionsLibrary.WrongUsernameException, ExceptionsLibrary.WrongPasswordException, ExceptionsLibrary.NoAccountException {
        HashMap<String, String> dataToLogin = (HashMap<String, String>) Client.receiveObject();
        Account account = GetDataFromDatabaseServerSide.getAccount(dataToLogin.get("username"));
        if (account != null) {
            if (account.getPassword().equals(dataToLogin.get("password"))) {
                if (account.getRole().equals("Customer")) {
                    CustomerController customerController = new CustomerController((Customer) account);
                    Client.sendMessage(account.getRole());
                    return;
                } else if (account.getRole().equals("Seller")) {
                    SellerController sellerController = new SellerController((Seller) account);
                    Client.sendMessage(account.getRole());
                    return;
                } else if (account.getRole().equals("Admin")) {
                    AdminController adminController = new AdminController((Admin) account);
                    Client.sendMessage(account.getRole());
                    return;
                }
            } else {
                Client.sendObject(new ExceptionsLibrary.WrongPasswordException());
                return;
            }
        } else {
            Client.sendObject(new ExceptionsLibrary.WrongUsernameException());
            return;
        }
        Client.sendObject(null);
    }


    public static void checkUsername() {
        String username = Client.receiveMessage();
        File folder1 = new File("Resources/Accounts/Customer/");
        File folder2 = new File("Resources/Accounts/Admin/");
        File folder3 = new File("Resources/Accounts/Seller/");
        if (new File(folder1, username + ".json").exists()) {
            Client.sendObject(false);
        } else if (new File(folder2, username + ".json").exists()) {
            Client.sendObject(true);
        } else  Client.sendObject(!new File(folder3, username + ".json").exists());
    }

    public static boolean checkUsername(String username) {

        File folder1 = new File("Resources/Accounts/Customer/");
        File folder2 = new File("Resources/Accounts/Admin/");
        File folder3 = new File("Resources/Accounts/Seller/");
        if (new File(folder1, username + ".json").exists()) {
            return false;
        } else if (new File(folder2, username + ".json").exists()) {
            return true;
        } else  return !new File(folder3, username + ".json").exists();
    }
}

