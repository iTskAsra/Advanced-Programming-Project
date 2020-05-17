/*
import com.google.gson.Gson;
import controller.CustomerController;
import controller.GetDataFromDatabase;
import controller.RegisterAndLogin;
import org.junit.Assert;
import org.junit.Test;
import controller.ExceptionsLibrary;

import java.util.HashMap;

public class EditTest {
    @Test
    public void showTest() throws ExceptionsLibrary.WrongPasswordException, ExceptionsLibrary.WrongUsernameException {
        HashMap<String,String> data = new HashMap<>();
        data.put("username","abc");
        data.put("password","klm");
        RegisterAndLogin.login(data);
        HashMap<String,String> data1 = new HashMap<>();
        data1.put("password","ok");
        data1.put("firstName","hello");
        data1.put("lastName","hi");
        CustomerController.editCustomerInfo(data1);
        String dataString = CustomerController.showCustomerInfo();
        System.out.println(CustomerController.getCustomer());
        Gson gson = new GsonBuilder().serializeNulls().create();
        String expected = gson.toJson(GetDataFromDatabase.getAccount("abc"));
        System.out.println(expected);
        Assert.assertEquals(expected,dataString);

    }

    @Test
    public void rateTest() throws ExceptionsLibrary.WrongPasswordException, ExceptionsLibrary.WrongUsernameException {
        HashMap<String,String> data = new HashMap<>();
        data.put("username","abc");
        data.put("password","ok");
        RegisterAndLogin.login(data);
        //Product product = new Product(1, ProductOrOffCondition.ACCEPTED,"a","b",2,null,2,null,null,"n",null,null,null);
        CustomerController.rateProduct(3,5);
    }
}
*/
