package Client.ClientController;

import Client.Client;
import controller.ExceptionsLibrary;

import java.util.HashMap;

public class RegisterAndLoginController {

    public static void register(HashMap dataToRegister) throws ExceptionsLibrary.AdminExist, ExceptionsLibrary.UsernameAlreadyExists {

        String convertedDataToRegister = new String();

        convertedDataToRegister += dataToRegister.get("username");
        convertedDataToRegister += " ";

        convertedDataToRegister += dataToRegister.get("password");
        convertedDataToRegister += " ";


        Client.sendMessage(convertedDataToRegister);

        String response = Client.receiveMessage();

    }

}
