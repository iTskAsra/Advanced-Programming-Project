/*
import com.google.gson.Gson;
import controller.GetDataFromDatabase;
import controller.RegisterAndLogin;
import controller.SellerController;
import model.Off;
import model.Product;
import model.ProductOrOffCondition;
import model.Seller;
import org.junit.Assert;
import org.junit.Test;
import Client.ClientView.MainMenuStage.Main;

import java.util.Date;
import java.util.HashMap;

public class OffSellerController {

    @Test
    public void editOffRequestTest(){
        HashMap<String,String> data = new HashMap<>();
        data.put("username","a");
        data.put("password","b");
        RegisterAndLogin.login(data);
        Off off = new Off(null,ProductOrOffCondition.ACCEPTED, Client.ClientView.MainMenuStage.Main.localDateTime.format(Client.ClientView.MainMenuStage.Main.dateTimeFormatter),"2020-10-10 23:59",50);
        HashMap<String,String> dataToEdit = new HashMap<>();
        dataToEdit.put("endDate","2020-12-12 01:00");
        SellerController.editOffRequest(5048,dataToEdit);
    }

    @Test
    public void addOffRequestTest(){
        HashMap<String,String> data = new HashMap<>();
        data.put("username","a");
        data.put("password","b");
        RegisterAndLogin.login(data);
        Off off = new Off(null,ProductOrOffCondition.ACCEPTED, Client.ClientView.MainMenuStage.Main.localDateTime.format(Client.ClientView.MainMenuStage.Main.dateTimeFormatter),"2020-10-10 23:59",50);
        Gson gson = new GsonBuilder().serializeNulls().create();
        String offData = gson.toJson(off);
        SellerController.addOffRequest(offData);
    }

    @Test
    public void showProductDetailsTest(){
        HashMap<String,String> data = new HashMap<>();
        data.put("username","a");
        data.put("password","b");
        RegisterAndLogin.login(data);
        Product product = GetDataFromDatabase.getProduct(1);
        Gson gson = new GsonBuilder().serializeNulls().create();
        String productData = gson.toJson(product);
        Assert.assertEquals(SellerController.showProductDetails(1),productData);
    }

    @Test
    public void showProductBuyersTest(){
        HashMap<String,String> data = new HashMap<>();
        data.put("username","a");
        data.put("password","b");
        RegisterAndLogin.login(data);
        System.out.println(SellerController.showProductBuyers(1));

    }

    @Test
    public void showOffsTest(){
        HashMap<String,String> data = new HashMap<>();
        data.put("username","v");
        data.put("password","m");
        RegisterAndLogin.login(data);
        System.out.println(SellerController.showOffs());
    }

    @Test
    public void showOffDetailsTest(){
        HashMap<String,String> data = new HashMap<>();
        data.put("username","v");
        data.put("password","m");
        RegisterAndLogin.login(data);
        System.out.println(SellerController.showOffDetails(5084));
    }

    @Test
    public void showBalance(){
        HashMap<String,String> data = new HashMap<>();
        data.put("username","v");
        data.put("password","m");
        RegisterAndLogin.login(data);
        System.out.println(SellerController.showBalance());
    }


}
*/
