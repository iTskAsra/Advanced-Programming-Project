package Client;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;

public class Client {

    public static Socket clientSocket;
    public static String token;
    public static Gson message;
    public static DataInputStream dataInputStream;
    public static DataOutputStream dataOutputStream;
    public static ObjectOutputStream os;
    public static ObjectInputStream is;


    public static void run() throws IOException {
        clientSocket = new Socket("localhost", 8080);
        dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
        token = dataInputStream.readUTF();
        dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
        dataOutputStream = new DataOutputStream(new DataOutputStream(clientSocket.getOutputStream()));
        os = new ObjectOutputStream(clientSocket.getOutputStream());
        is = new ObjectInputStream(clientSocket.getInputStream());
    }

    public static void sendMessage(String string){

        try {
            dataOutputStream.writeUTF(string);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String receiveMessage(){

        try {
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void sendObject(Object sent){

        try {
            os.writeObject(sent);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object receiveObject(){

        try {
            return is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
