//
//import controller.RegisterAndLogin;
//import controller.SellerController;
//import org.junit.Test;
//import controller.ExceptionsLibrary;
//
//import java.util.HashMap;
//
//public class EditProductRequestTest {
//
//    /*@Test
//    public void editProductRequestTest() throws ExceptionsLibrary.WrongPasswordException, ExceptionsLibrary.WrongUsernameException {
//        HashMap<String,String> data = new HashMap<>();
//        data.put("username","a");
//        data.put("password","b");
//        RegisterAndLogin.login(data);
//        Product product = new Product(ProductOrOffCondition.ACCEPTED,"a","b",5,null,2,null,null,"n",null,null,null);
//        HashMap<String,String> dataToEdit = new HashMap<>();
//        dataToEdit.put("company","apple");
//        dataToEdit.put("price","7");
//        dataToEdit.put("description","This is good");
//        SellerController.editProductRequest(1,dataToEdit);
//    }
//    @Test*/
//    public void removeProductRequestTest() throws ExceptionsLibrary.WrongPasswordException, ExceptionsLibrary.WrongUsernameException {
//        HashMap<String,String> data = new HashMap<>();
//        data.put("username","a");
//        data.put("password","b");
//        RegisterAndLogin.login(data);
//        SellerController.removeProduct(1);
//    }
//    /*@Test
//    public void addProductRequestTest() throws ExceptionsLibrary.WrongPasswordException, ExceptionsLibrary.WrongUsernameException {
//        HashMap<String,String> data = new HashMap<>();
//        data.put("username","a");
//        data.put("password","b");
//        RegisterAndLogin.login(data);
//        Product product = new Product(ProductOrOffCondition.PENDING_TO_CREATE,"apple","fruit",5,null,2,null,null,"hello",null,null,null);
//        Gson gson = new GsonBuilder().serializeNulls().create();
//        String productData = gson.toJson(product);
//        SellerController.addProductRequest(productData);
//    }*/
//
//    @Test
//    public void productMaker(){
//        //Product product = new Product(ProductOrOffCondition.PENDING_TO_CREATE,"a","a",33,null,5, GetDataFromDatabase.getCategory("Hi"))
//
//    }
//
//}
//
