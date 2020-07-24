package Bank;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class BankClient {

    public static void main(String[] args) {
        new ClientImplementation().run();
    }

    public static class ClientImplementation {
        public final int PORT = 8090;
        public final String IP = "127.0.0.1";
        public final int SHOP_SERVER_PORT = 9000;

        private DataOutputStream outputStream;
        private DataInputStream inputStream;

        private String token;
        private static String response;

        public static String getResponse() {
            return response;
        }

        public static void setResponse(String response) {
            ClientImplementation.response = response;
        }

        public void connectToBankServer() throws IOException {
            try {
                Socket socket = new Socket(IP, PORT);
                outputStream = new DataOutputStream(socket.getOutputStream());
                inputStream = new DataInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new IOException("Exception while initiating connection:");
            }
        }


        public void sendMessage(String msg) throws IOException {
            try {
                outputStream.writeUTF(msg);
                String response = inputStream.readUTF();
                System.out.println(response);
                setResponse(response);
            } catch (IOException e) {
                throw new IOException("Exception while sending message:");
            }
        }

        public void run() {
            try {
                response = "";
                connectToBankServer();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

        }
    }
}
