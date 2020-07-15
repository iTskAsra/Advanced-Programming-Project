package Server;
import java.io.*;
import java.lang.Thread;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler extends Thread {
    protected Socket socket;
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static ObjectOutputStream os;
    public static ObjectInputStream is;
    InputStream inp = null;
    DataInputStream brinp = null;
    DataOutputStream out = null;


    public ClientHandler(Socket clientSocket) {
        this.socket = clientSocket;
    }



    public void run() {

        int freePort  = findFreePort();



        try {
            os = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            inp = socket.getInputStream();
            brinp = new DataInputStream(new BufferedInputStream(inp));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        String line;
        String generatedToken = generateToken();
        try {
            out.writeUTF(generatedToken);
            System.out.println(generatedToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                line = brinp.readUTF();
                handleClientRequest(line);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }


    public void writeToClient(String dataToWrite){
        try {
            out.writeUTF(dataToWrite);
            out.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readDataFromClient() {
        String readData = null;
        try {
            readData = brinp.readUTF();
            return readData;
        }catch (IOException e){
            e.printStackTrace();
        }

        return readData;
    }

    public Object readObjectFromClient() {
        Object object = null;
        try {
            object = is.readObject();
            return object;
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        return null;
    }

    public void writeObjectToClient(Object object){
        try {
            os.writeObject(object);
            os.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void handleClientRequest(String receivedMessage) {
        String[] splitMessage = receivedMessage.split(" ",5);
    }


    public static String generateToken() {
        StringBuilder builder = new StringBuilder();
        int count = 25;
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }


    private int findFreePort() {
        ServerSocket tmp;
        int freePort = -1;
        for (int i = 1; i < 9999; ++i) {
            try {
                tmp = new ServerSocket(i);
                freePort = i;
                tmp.close();
                break;
            } catch (IOException e) { /* This port is inuse */}
        }
        return freePort;
    }
}



