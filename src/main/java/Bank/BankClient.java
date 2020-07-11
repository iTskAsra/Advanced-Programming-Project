package Bank;

import controller.ExceptionsLibrary;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class BankClient {

    public static void main(String[] args) {
        new ClientImplementation().run();
    }

    static class ClientImplementation {
        public final int PORT = 8090;
        public final String IP = "127.0.0.1";

        private DataOutputStream outputStream;
        private DataInputStream inputStream;

        private String token;

        public void ConnectToBankServer() throws IOException {
            try {
                Socket socket = new Socket(IP, PORT);
                outputStream = new DataOutputStream(socket.getOutputStream());
                inputStream = new DataInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new IOException("Exception while initiating connection:");
            }
        }

        public void StartListeningOnInput() {
            new Thread(() -> {
                while (true) {
                    try {
                        System.out.println(inputStream.readUTF());
                    } catch (IOException e) {
                        System.out.println("disconnected");
                        System.exit(0);
                    }
                }
            }).start();
        }

        public void SendMessage(String msg) throws IOException {
            try {
                outputStream.writeUTF(msg);
            } catch (IOException e) {
                throw new IOException("Exception while sending message:");
            }
        }

        public void run() {
            try {
                ConnectToBankServer();
                StartListeningOnInput();
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    SendMessage(scanner.nextLine());
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

        }
    }
}
