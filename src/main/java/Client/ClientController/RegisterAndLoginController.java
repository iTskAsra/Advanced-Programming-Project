package Client.ClientController;

import Client.Client;

import java.util.HashMap;

public class RegisterAndLoginController {

    public static void register(String dataToRegister) throws ExceptionsLibrary.AdminExist, ExceptionsLibrary.UsernameAlreadyExists {

        String func = "Register";
        Client.sendMessage(func);

        Client.sendObject(dataToRegister);

        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.AdminExist)
            throw new ExceptionsLibrary.AdminExist();
        else if (response instanceof ExceptionsLibrary.UsernameAlreadyExists)
            throw new ExceptionsLibrary.UsernameAlreadyExists();
        else if (response.equals("Done"))
            return;
    }

    public static void login(HashMap<String, String> dataToLogin) throws ExceptionsLibrary.WrongUsernameException, ExceptionsLibrary.WrongPasswordException, ExceptionsLibrary.NoAccountException{

        String func = "Log in";
        Client.sendMessage(func);

        Client.sendObject(dataToLogin);

        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.WrongUsernameException)
            throw new ExceptionsLibrary.WrongUsernameException();
        else if (response instanceof ExceptionsLibrary.WrongPasswordException)
            throw new ExceptionsLibrary.WrongPasswordException();
        else if (response instanceof ExceptionsLibrary.NoAccountException)
            throw new ExceptionsLibrary.NoAccountException();
        else
            return;
    }

    public static void registerAdmin (String data){

        String func = "Register Admin";
        Client.sendMessage(func);

        Client.sendMessage(data);
    }

    public static boolean checkUsername(String username){

        String func = "Check Username";
        Client.sendMessage(func);

        Client.sendMessage(username);

        return (boolean) Client.receiveObject();
    }



}