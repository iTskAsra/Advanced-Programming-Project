import controller.AdminController;
import controller.RegisterAndLogin;
import org.junit.Test;
import controller.ExceptionsLibrary;

import java.util.HashMap;

public class RequestTest {

    @Test
    public void showRequests() throws ExceptionsLibrary.WrongPasswordException, ExceptionsLibrary.WrongUsernameException {
        HashMap<String,String> data = new HashMap<>();
        data.put("username","ooo");
        data.put("password","def");
        RegisterAndLogin.login(data);
        AdminController.showAdminRequests();
    }

    @Test
    public void processRequests() throws ExceptionsLibrary.WrongPasswordException, ExceptionsLibrary.WrongUsernameException {
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
