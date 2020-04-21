import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.AdminController;
import controller.RegisterAndLogin;
import controller.SellerController;
import model.*;
import org.junit.Test;
import view.Main;

import java.util.HashMap;

public class RequestTest {

    @Test
    public void showRequests(){
        HashMap<String,String> data = new HashMap<>();
        data.put("username","ooo");
        data.put("password","def");
        RegisterAndLogin.login(data);
        AdminController.showAdminRequests();
    }

    @Test
    public void processRequests(){
        HashMap<String,String> data = new HashMap<>();
        data.put("username","ooo");
        data.put("password","def");
        RegisterAndLogin.login(data);
        /*Off off = new Off(null, ProductOrOffCondition.ACCEPTED, Main.localDateTime.format(Main.dateTimeFormatter),"2020-12-12 01:00",50);
        Gson gson = new GsonBuilder().serializeNulls().create();
        String offDetails = gson.toJson(off);
        Request request= new Request(offDetails, RequestType.EDIT_OFF, RequestOrCommentCondition.PENDING_TO_ACCEPT,SellerController.getSeller());
        String requestDetails = gson.toJson(request);
        System.out.println(offDetails);
        System.out.println(requestDetails);*/
        AdminController.processRequest(8690,true);
    }
}
