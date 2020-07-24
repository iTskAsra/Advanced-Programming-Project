package Server;

import Bank.BankClient;
import Server.FunctionController;
import Server.ServerController.ExceptionsLibrary;
import Server.ServerController.SetPeriodicSales;
import model.Account;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;


public class Server {

    private static ArrayList<Account> onlineUsers = new ArrayList<>();
    public static BankClient.ClientImplementation clientImplementation;

    public static void main(String args[]) throws IOException {

        ArrayList<String> listOfTokens = new ArrayList<>();
        ServerSocket server = null;
        Socket socket = null;
        try {
            SetPeriodicSales.setPeriodicSales();
            SetPeriodicSales.removeExpiredOff();
        } catch (ExceptionsLibrary.NoAccountException | ExceptionsLibrary.NoProductException | ParseException | ExceptionsLibrary.NoOffException e) {
            e.printStackTrace();
        }

        BankClient bankClient = new BankClient();
        clientImplementation = new BankClient.ClientImplementation();

        try {
            server = new ServerSocket(4445);
            System.out.println("Socket Created");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                socket = server.accept();
                System.out.println("connection Established");
                ClientHandler ch = new ClientHandler(socket);
                System.out.println("Starting Thread:");
                ch.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static BankClient.ClientImplementation getClientImplementation() {
        return clientImplementation;
    }

    public static void setClientImplementation(BankClient.ClientImplementation clientImplementation) {
        Server.clientImplementation = clientImplementation;
    }


    public static void addOnlineUser (Account account,String token){
        FunctionController.users.put(token,account);
        onlineUsers.add(account);
    }

    public static void removeOnlineUser (Account account){
        onlineUsers.remove(account);
        //FunctionController.users.remove(account);
    }

    public static ArrayList<Account> getOnlineUsers (){
        return onlineUsers;
    }
}
