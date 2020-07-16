package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;



public class Server {

    public static void main(String args[]) throws IOException {

        ArrayList<String> listOfTokens = new ArrayList<>();
        ServerSocket server = null;
        Socket socket = null;
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
}
