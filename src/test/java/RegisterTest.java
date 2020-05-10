/*
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.GetDataFromDatabase;
import controller.RegisterAndLogin;
import model.Account;
import model.Customer;
import model.Seller;
import org.junit.Test;
import controller.ExceptionsLibrary;

import java.util.HashMap;


public class RegisterTest {

    @Test
    public void registerTest() throws ExceptionsLibrary.NoAccountException, ExceptionsLibrary.AdminExist {
        Account account = new Seller("ppp", "def", "Seller","s", "f", "r", "09",null,50,null,null,null,null);
        Gson gson = new GsonBuilder().serializeNulls().create();
        String data = gson.toJson(account);
        RegisterAndLogin.register(data);
    }

    @Test
    public void loginTest() throws ExceptionsLibrary.WrongPasswordException, ExceptionsLibrary.WrongUsernameException {
        HashMap<String,String> data = new HashMap<>();
        data.put("username","abc");
        data.put("password","def");
        RegisterAndLogin.login(data);
        Customer customer = (Customer) GetDataFromDatabase.getAccount("abc");
    }
}
*/
