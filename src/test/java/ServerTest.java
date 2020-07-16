import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ServerTest {

    @Test
    public void serverTest () throws IOException {
        InetAddress address= InetAddress.getLocalHost();
        Socket s1=null;
        String line=null;
        BufferedReader br=null;
        BufferedReader is=null;
        PrintWriter os=null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        try {
            s1=new Socket(address, 4445); // You can use static final constant PORT_NUM
            dis = new DataInputStream(s1.getInputStream());
            dos = new DataOutputStream(s1.getOutputStream());
            ois = new ObjectInputStream(s1.getInputStream());
            oos = new ObjectOutputStream(s1.getOutputStream());

        }
        catch (IOException e){
            e.printStackTrace();
            System.err.print("IO Exception");
        }

        System.out.println("Client Address : "+address);
        System.out.println("Enter Data to echo Server ( Enter QUIT to end):");

        String response=null;
        try{
            System.out.println(dis.readUTF());
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Socket read Error");
        }
        finally{

            is.close();os.close();br.close();s1.close();
            System.out.println("Connection Closed");

        }
    }
}
