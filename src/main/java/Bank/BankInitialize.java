package Bank;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Product;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class BankInitialize {

    public static HashMap<String,String> tokens;
    public static ArrayList<Integer> accountIds;
    public static ArrayList<Receipt> receipts;
    public static ArrayList<String> expiredTokens;

    public static void main(String[] args) throws IOException {
        tokens = new HashMap<>();
        accountIds = new ArrayList<>();
        receipts = new ArrayList<>();
        expiredTokens = new ArrayList<>();
        bankDatabaseInitialize();
        ServerSocket serverSocket = new ServerSocket(8090);
        while (true){
            Socket socket = serverSocket.accept();
            new BankClientHandler(socket).start();
        }
    }

    private static void bankDatabaseInitialize() {
        String receiptPath = "BankResource/Receipts.json";
        File bankResource = new File("BankResource");
        bankResource.mkdir();
        File accounts = new File("BankResource/Accounts");
        accounts.mkdir();
        File receiptsFile = new File("BankResource/Receipts.json");
        try {
            if (!receiptsFile.exists()) {
                receiptsFile.createNewFile();
            }
            else {
                String fileData = "";
                Gson gson = new GsonBuilder().serializeNulls().create();
                fileData = new String(Files.readAllBytes(Paths.get(receiptPath)));
                receipts = gson.fromJson(fileData, new TypeToken<ArrayList<Receipt>>(){}.getType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> getTokens() {
        return tokens;
    }

    public static void setTokens(HashMap<String, String> tokens) {
        BankInitialize.tokens = tokens;
    }

    public static void setBankAccount(BankAccount bankAccount){
        String path = "BankResource/Accounts/" + bankAccount.getUsername()+".json";
        Gson gson = new GsonBuilder().serializeNulls().create();
        String data = gson.toJson(bankAccount);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(path);
            fileWriter.write(data);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
