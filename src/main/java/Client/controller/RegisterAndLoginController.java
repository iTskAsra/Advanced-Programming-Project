package Client.controller;

import Client.Client;
import controller.ExceptionsLibrary;
import model.Account;

public class RegisterAndLoginController {

    public static void register(String dataToRegister) throws ExceptionsLibrary.AdminExist, ExceptionsLibrary.UsernameAlreadyExists {

        Client.sendMessage(dataToRegister);

        String response = Client.receiveMessage();

    }

}
