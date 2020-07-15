package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {

    public static ArrayList<String> listOfTokens = new ArrayList<>();

    static final int PORT = 8080;


    public static void main(String args[]) {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();

        }
        new ClientHandler(socket).start();

    }
}
