package Server;
import java.io.*;
import java.lang.Thread;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler extends Thread {
    protected Socket socket;
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";


    public ClientHandler(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {
        InputStream inp = null;
        DataInputStream brinp = null;
        DataOutputStream out = null;
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


    public void writeToClient(String dataToWrite,DataOutputStream out){
        try {
            out.writeUTF(dataToWrite);
            out.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readDataFromClient(DataInputStream in) {
        String readData = null;
        try {
            readData = in.readUTF();
            return readData;
        }catch (IOException e){
            e.printStackTrace();
        }

        return readData;
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
}



