package Server;
import java.io.*;
import java.lang.Thread;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler extends Thread {
    protected Socket socket;

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
        while (true) {
            try {
                line = brinp.readUTF();
                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                    socket.close();
                    return;
                } else {
                    out.writeBytes(line + "\n\r");
                    out.flush();
                }
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
}

