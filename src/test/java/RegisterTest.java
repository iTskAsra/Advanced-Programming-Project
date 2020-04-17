import com.google.gson.Gson;
import controller.CustomerController;
import controller.GetDataFromDatabase;
import controller.RegisterAndLogin;
import model.Account;
import model.Customer;
import model.Seller;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;


public class RegisterTest {

    @Test
    public void registerTest() {
        Account account = new Customer("abc", "def", "Customer","s", "f", "r", "09", null, 34.5,null);
        Gson gson = new Gson();
        String data = gson.toJson(account);
        RegisterAndLogin.register(data);
        Account account1 =GetDataFromDatabase.getAccount("abc");
        String received = gson.toJson(account1);
        Assert.assertEquals(received,data);
    }

    @Test
    public void loginTest() {
        HashMap<String,String> data = new HashMap<>();
        data.put("username","abc");
        data.put("password","def");
        RegisterAndLogin.login(data);
        Customer customer = (Customer) GetDataFromDatabase.getAccount("abc");
    }
}
