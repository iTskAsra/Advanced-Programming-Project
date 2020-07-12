package Server;
import Client.Client;
import Server.controller.*;


public class functionController {

    public static void handleFunction (String command) throws ExceptionsLibrary.NoAccountException {

        switch (command) {
            case "show Admin Info" :
                AdminController.showAdminInfo();
        }



    }

}
