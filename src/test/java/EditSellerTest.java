import com.google.gson.Gson;
import controller.RegisterAndLogin;
import controller.SellerController;
import model.Account;
import model.Seller;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class EditSellerTest {

    @Test
    public void editSellerTest(){
        Account account = new Seller("a","b","Seller","m","n","l","7",null,50,"apple",null,null,null);
        Gson gson = new Gson();
        String registerData = gson.toJson(account);
        //RegisterAndLogin.register(registerData);
        HashMap<String,String> data = new HashMap<>();
        data.put("username","a");
        data.put("password","b");
        //RegisterAndLogin.login(data);
        System.out.println(SellerController.getSeller().getFirstName());
        HashMap<String,String> editedData = new HashMap<>();
        editedData.put("firstName","Yo");
        editedData.put("lastName","Soy");
        editedData.put("companyName","Google");
        SellerController.editSellerInfo(editedData);
        System.out.println(SellerController.getSeller().getFirstName());
    }
}
