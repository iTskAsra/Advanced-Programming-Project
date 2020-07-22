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
        System.out.println("Running Client!");
        clientSocket = new Socket("localhost", 4445);
        dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
        dataOutputStream = new DataOutputStream(new DataOutputStream(clientSocket.getOutputStream()));
        os = new ObjectOutputStream(clientSocket.getOutputStream());
        is = new ObjectInputStream(clientSocket.getInputStream());
        token = dataInputStream.readUTF();
        System.out.println("Streams Initialized and Assigned!");
        System.out.println(token);
    }

    public static void sendMessage(String string){

        //string += " ";
        //string += token;

        //System.out.println("Client Said: "+string);
        sendObject(string);
    }

    public static String receiveMessage(){

        String message = null;
        message = (String) receiveObject();
        //System.out.println("Server Said: "+message);

        //return message;
        return message;
    }

    public static void sendObject(Object sent){

        try {
            System.out.println("Client Said: "+sent);
            os.writeObject(sent);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object receiveObject(){
        Object object = null;
        try {
            object = is.readObject();
            System.out.println("Server Said: "+object);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return object;
    }
}
