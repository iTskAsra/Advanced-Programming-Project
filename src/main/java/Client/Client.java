package Client;

import com.google.gson.Gson;
import com.sun.javafx.css.parser.Token;

import java.io.*;
import java.net.Socket;

public class Client {

    private static Socket clientSocket;
    private String token;
    private Gson message;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public Client() throws IOException {


        clientSocket = new Socket("localhost", 8080);
        dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
        this.token = dataInputStream.readUTF();
        dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
        dataOutputStream = new DataOutputStream(new DataOutputStream(clientSocket.getOutputStream()));
    }

    public String sendMessage(String string){

        try {
            dataOutputStream.writeUTF(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
