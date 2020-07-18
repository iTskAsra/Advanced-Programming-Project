import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.RegisterAndLogin;
import model.Account;
import model.Customer;
import org.junit.Assert;
import org.junit.Test;
import controller.ExceptionsLibrary;

import java.io.File;


public class RegisterTest {

    @Test
    public void registerTest() throws ExceptionsLibrary.NoAccountException, ExceptionsLibrary.AdminExist, ExceptionsLibrary.UsernameAlreadyExists {
        Account account = new Customer("test", "test", "Customer","test", "test", "test", "09111111111",null,50000,null);
        Gson gson = new GsonBuilder().serializeNulls().create();
        String data = gson.toJson(account);
        RegisterAndLogin.register(data);
        File file = new File("Resources/Accounts/Customer/test.json");
        Assert.assertTrue(file.exists());
    }
}

