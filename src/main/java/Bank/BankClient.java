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

        /**
         * This method is used to add initiating socket and IN/OUT data stream .
         *
         * @throws IOException when IP/PORT hasn't been set up properly.
         */
        public void ConnectToBankServer() throws IOException {
            try {
                Socket socket = new Socket(IP, PORT);
                outputStream = new DataOutputStream(socket.getOutputStream());
                inputStream = new DataInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new IOException("Exception while initiating connection:");
            }
        }

        /**
         * This method is used to start a Thread ,listening on IN data stream.
         */
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

        /**
         * This method is used to send message with value
         *
         * @param msg to Bank server.
         * @throws IOException when OUT data stream been interrupted.
         */
        public void SendMessage(String msg) throws IOException {
            try {
                outputStream.writeUTF(msg);
            } catch (IOException e) {
                throw new IOException("Exception while sending message:");
            }
        }

        /**
         * This method is used to illustrate an example of using methods of this class.
         */
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
