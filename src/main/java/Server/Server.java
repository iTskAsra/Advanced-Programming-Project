package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;



public class Server {
    
    public static ArrayList<String> listOfTokens = new ArrayList<>();

    static final int DNS_PORT = 8080;
    private static ServerSocket server;

    static {
        try {
            server = new ServerSocket(DNS_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String args[]) throws IOException {



        while (true){
            Socket socket = server.accept();
        }

    }
}
