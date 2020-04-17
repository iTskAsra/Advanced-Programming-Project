package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.CustomerController;
import model.Account;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static view.Main.scanner;

public class RegisterAndLoginPanel extends Menu {
    private static final Object MainMenu = null;
    String input;
    Pattern reg = Pattern.compile("^create account (?i)(seller||customer||admin) (\\s)$");
    Matcher matcherReg = reg.matcher(scanner);
    Pattern login = Pattern.compile("^login (\\s)$");
    Matcher matcherLogin = reg.matcher(scanner);
    Gson gsonSend = new Gson();


    public RegisterAndLoginPanel() {
        super("Register And Login Panel", (Menu) MainMenu);
    }

    public void registerAndLogin(){
        input = scanner.nextLine();

        if(matcherReg.find()){


        }

        if(matcherLogin.find()){

        }
    }

}
