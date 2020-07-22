package Server;
import Server.ServerController.ExceptionsLibrary;

import java.io.*;
import java.lang.Thread;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler extends Thread {
    protected Socket socket;
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static ObjectOutputStream os;
    public static ObjectInputStream is;
    public static InputStream inp = null;
    public static DataInputStream brinp = null;
    public static DataOutputStream out = null;


    public ClientHandler(Socket clientSocket) {
        this.socket = clientSocket;
    }



    public void run() {
        System.out.println("Running Client Thread!");
        try {
            os = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("OS initialized");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("Initializing IS");
            is = new ObjectInputStream(socket.getInputStream());
            System.out.println("IS initialized");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("Getting Input");
            inp = socket.getInputStream();
            brinp = new DataInputStream(new BufferedInputStream(inp));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        String line;
        String generatedToken = generateToken();
        System.out.println("Token Generated!");
        try {
            out.writeUTF(generatedToken);
            System.out.println(generatedToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                line = receiveMessage();
                FunctionController.handleFunction(line);
            } catch (ExceptionsLibrary.ChangeUsernameException e) {
                e.printStackTrace();
            } catch (ExceptionsLibrary.NotLoggedInException e) {
                e.printStackTrace();
            } catch (ExceptionsLibrary.NoLogException e) {
                e.printStackTrace();
            } catch (ExceptionsLibrary.UsernameAlreadyExists usernameAlreadyExists) {
                usernameAlreadyExists.printStackTrace();
            } catch (ExceptionsLibrary.NoRequestException e) {
                e.printStackTrace();
            } catch (ExceptionsLibrary.NotEnoughNumberAvailableException e) {
                e.printStackTrace();
            } catch (ExceptionsLibrary.NoFeatureWithThisName noFeatureWithThisName) {
                noFeatureWithThisName.printStackTrace();
            } catch (ExceptionsLibrary.SaleNotStartedYetException e) {
                e.printStackTrace();
            } catch (ExceptionsLibrary.SelectASeller selectASeller) {
                selectASeller.printStackTrace();
            } catch (ExceptionsLibrary.CategoriesNotMatch categoriesNotMatch) {
                categoriesNotMatch.printStackTrace();
            } catch (ExceptionsLibrary.NoCategoryException e) {
                e.printStackTrace();
            } catch (ExceptionsLibrary.NoProductException e) {
                e.printStackTrace();
            } catch (ExceptionsLibrary.NoOffException e) {
                e.printStackTrace();
            } catch (ExceptionsLibrary.CategoryExistsWithThisName categoryExistsWithThisName) {
                categoryExistsWithThisName.printStackTrace();
            } catch (ExceptionsLibrary.NoAccountException e) {
                e.printStackTrace();
            } catch (ExceptionsLibrary.CannotChangeThisFeature cannotChangeThisFeature) {
                cannotChangeThisFeature.printStackTrace();
            } catch (ExceptionsLibrary.UsedAllValidTimesException e) {
                e.printStackTrace();
            } catch (ExceptionsLibrary.NoSaleException e) {
                e.printStackTrace();
            } catch (ExceptionsLibrary.NoFilterWithThisName noFilterWithThisName) {
                noFilterWithThisName.printStackTrace();
            } catch (ExceptionsLibrary.SaleExpiredException e) {
                e.printStackTrace();
            } catch (ExceptionsLibrary.CreditNotSufficientException e) {
                e.printStackTrace();
            } catch (ExceptionsLibrary.WrongUsernameException e) {
                e.printStackTrace();
            } catch (ExceptionsLibrary.WrongPasswordException e) {
                e.printStackTrace();
            }
        }
    }


    public static void sendMessage(String dataToWrite){
        //System.out.println("Server Said: "+dataToWrite);
        sendObject(dataToWrite);
        //out.writeUTF(dataToWrite);
        //out.flush();
    }

    public static String receiveMessage() {
        String readData = null;
        readData = (String) receiveObject();
        return readData;
    }

    public static Object receiveObject() {
        Object object = null;
        try {
            object = is.readObject();
            System.out.println("Client Said: "+object);
            return object;
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        return null;
    }

    public static void sendObject(Object object){
        try {
            System.out.println("Server Said: "+object);
            os.writeObject(object);
            os.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void handleClientRequest(String receivedMessage) {
        //String[] splitMessage = receivedMessage.split(" ",5);
        //FunctionController.handleFunction(receivedMessage);
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



