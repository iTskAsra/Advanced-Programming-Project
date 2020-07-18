package Server;

import Bank.BankClient;

import Server.ServerController.ExceptionsLibrary;
import Server.ServerController.SetPeriodicSales;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;


public class Server {

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
}
