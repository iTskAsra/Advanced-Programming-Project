import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ServerTest2 {

    @Test
    public void serverTest () throws IOException {
        InetAddress address= InetAddress.getLocalHost();
        Socket s1=null;
        String line=null;
        BufferedReader br=null;
        BufferedReader is=null;
        PrintWriter os=null;
        DataInputStream dis = null;

        try {
            s1=new Socket(address, 4445); // You can use static final constant PORT_NUM
            br= new BufferedReader(new InputStreamReader(System.in));
            is=new BufferedReader(new InputStreamReader(s1.getInputStream()));
            os= new PrintWriter(s1.getOutputStream());
            dis = new DataInputStream(s1.getInputStream());
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
            line=br.readLine();
            while(line.compareTo("QUIT")!=0){
                os.println(line);
                os.flush();
                response=is.readLine();
                System.out.println("Server Response : "+response);
                line=br.readLine();

            }



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
