package Server;
import Client.Client;
import Server.ServerController.*;
import model.Account;

import java.util.HashMap;


public class FunctionController {

    public static HashMap<String, Account> users = new HashMap<>();

    public static void handleFunction (String command) throws ExceptionsLibrary.NoAccountException, ExceptionsLibrary.NoFeatureWithThisName, ExceptionsLibrary.ChangeUsernameException, ExceptionsLibrary.NoRequestException, ExceptionsLibrary.UsernameAlreadyExists, ExceptionsLibrary.NoProductException, ExceptionsLibrary.NoCategoryException, ExceptionsLibrary.CategoryExistsWithThisName, ExceptionsLibrary.NoSaleException, ExceptionsLibrary.NoFilterWithThisName, ExceptionsLibrary.NotEnoughNumberAvailableException, ExceptionsLibrary.NotLoggedInException, ExceptionsLibrary.SaleExpiredException, ExceptionsLibrary.SaleNotStartedYetException, ExceptionsLibrary.UsedAllValidTimesException, ExceptionsLibrary.CreditNotSufficientException, ExceptionsLibrary.NoProductException, ExceptionsLibrary.NoAccountException, ExceptionsLibrary.NoLogException, ExceptionsLibrary.NoOffException, ExceptionsLibrary.SelectASeller, ExceptionsLibrary.CategoriesNotMatch, ExceptionsLibrary.CannotChangeThisFeature, ExceptionsLibrary.WrongPasswordException, ExceptionsLibrary.WrongUsernameException {

        //System.out.println(command);

        switch (command) {
            case "show Admin Info" : {
                AdminController.showAdminInfo();
                break;
            }
            case "Edit Admin Info" : {
                AdminController.editAdminInfo();
                break;
            }
            case "Show Admin Requests" : {
                AdminController.showAdminRequests();
                break;
            }
            case "Show Request" : {
                AdminController.showRequest();
                break;
            }
            case "Show All Users" : {
                AdminController.showAllUsers();
                break;
            }
            case "Show ALl Customers" : {
                AdminController.showAllCustomers();
                break;
            }
            case "Show User Details" : {
                AdminController.showUserDetails();
                break;
            }
            case "Delete User" : {
                AdminController.deleteUser();
                break;
            }
            case "Add Admin Account" : {
                AdminController.addAdminAccount();
                break;
            }
            case "Delete Product" : {
                AdminController.deleteProduct();
                break;
            }
            case "Delete Category" : {
                AdminController.deleteCategory();
                break;
            }
            case "Edit Category" : {
                AdminController.editCategory();
                break;
            }
            case "Add Category" : {
                AdminController.addCategory();
                break;
            }
            case "View Sale Code Details" : {
                AdminController.viewSaleCodeDetails();
                break;
            }
            case "Remove Sale Code" : {
                AdminController.removeSaleCode();
                break;
            }
            case "Check Category Name" : {
                AdminController.checkCategoryName();
                break;
            }
            case "Check Sale Code" : {
                AdminController.checkSaleCode();
                break;
            }
            case "Check If Product Exists" : {
                AdminController.checkIfProductExist();
                break;
            }
            case "Check If Request Exists" : {
                AdminController.checkIfRequestExist();
                break;
            }
            case "Get All Products Admin" : {
                AdminController.getAllProducts();
                break;
            }
            case "View Categories APC" : {
                AllProductsPanelController.viewCategories();
                break;
            }
            case "Show Available Filters APC" : {
                AllProductsPanelController.showAvailableFilters();
                break;
            }
            case "Get Product Removed APC" : {
                AllProductsPanelController.getProductRemoved();
                break;
            }
            case "Seller OF This Product" : {
                AllProductsPanelController.sellersOfThisProduct();
                break;
            }
            case "Get All Products" : {
                AllProductsPanelController.getAllProducts();
                break;
            }
            case "Go To Product Page APC" : {
                AllProductsPanelController.goToProductPage();
                break;
            }
            case "Ia Feature" : {
                AllProductsPanelController.isFeature();
                break;
            }

            case "Increase Product" : {
                CartController.increaseProduct();
                break;
            }
            case "Decrease Product" : {
                CartController.decreaseProduct();
                break;
            }
            case "View Cart Product Details" : {
                CartController.viewCartProductDetails();
                break;
            }
            case "Show Total Price" : {
                CartController.showTotalPrice();
                break;
            }
            case "Receiver Data" : {
                CartController.receiverProcess();
                break;
            }
            case "Discount Apply" : {
                CartController.discountApply();
                break;
            }
            case "Purchase" : {
                CartController.purchase();
                break;
            }
            case "Show Customer Info" : {
                CustomerController.showCustomerInfo();
                break;
            }
            case "Edit Customer Info" : {
                CustomerController.editCustomerInfo();
                break;
            }
            case "Show Customer Balance" : {
                CustomerController.showCustomerBalance();
                break;
            }
            case "Show Customer Discount Codes" : {
                CustomerController.showDiscountCodes();
                break;
            }
            case "Show Customer Logs" : {
                CustomerController.showCustomerLogs();
                break;
            }
            case "Show Customer Log Details" : {
                CustomerController.showCustomerLogDetail();
                break;
            }
            case "Rate Product" : {
                CustomerController.rateProduct();
                break;
            }
            case "View Categories" : {
                OffPageController.viewCategories();
                break;
            }
            case "Show Available Filters" : {
                OffPageController.showAvailableFilters();
                break;
            }
            case "Get Product Removed" : {
                OffPageController.getProductRemoved();
                break;
            }
            case "Seller Of This Product" : {
                OffPageController.sellersOfThisProduct();
                break;
            }
            case "Show Products" : {
                OffPageController.showProducts();
                break;
            }
            case "Go To Product Page" : {
                OffPageController.goToProductPage();
                break;
            }
            case "Is Feature" : {
                OffPageController.isFeature();
                break;
            }
            case "Get Off Products" : {
                OffPageController.getOffProducts();
                break;
            }
            case "Is In Off" : {
                OffPageController.isInOff();
                break;
            }
            case "Off Details" : {
                OffPageController.offDetails();
                break;
            }
            case "Show Comments" : {
                ProductPageController.comments();
                break;
            }
            case "Add Comment" : {
                ProductPageController.addComment();
                break;
            }
            case "Is Bought By Commenter" : {
                ProductPageController.isBoughtByCommenter();
                break;
            }
            case "Digest" : {
                ProductPageController.digest();
                break;
            }
            case "Add To Cart" : {
                ProductPageController.addToCart();
                break;
            }
            case "Select Seller" : {
                ProductPageController.selectSeller();
                break;
            }
            case "Compare" : {
                ProductPageController.compare();
                break;
            }
            case "Show Seller Info" : {
                SellerController.showSellerInfo();
                break;
            }
            case "Edit Seller Info" : {
                SellerController.editSellerInfo();
                break;
            }
            case "Show Seller Company" : {
                SellerController.showSellerCompany();
                break;
            }
            case "Show Seller Products" : {
                SellerController.showSellerProducts();
                break;
            }
            case "Show Seller Logs" : {
                SellerController.showSellerLogs();
                break;
            }
            case "Edit Product Request" : {
                SellerController.editProductRequest();
                break;
            }
            case "Remove Product Request" : {
                SellerController.removeProductRequest();
                break;
            }
            case "Add Product Request" : {
                SellerController.addProductRequest();
                break;
            }
            case "Edit Off Request" : {
                SellerController.editOffRequest();
                break;
            }
            case "Add Off Request" : {
                SellerController.addOffRequest();
                break;
            }
            case "Show Offs" : {
                SellerController.showOffs();
                break;
            }
            case "Show Off Details" : {
                SellerController.showOffDetails();
                break;
            }
            case "Show Product Details" : {
                SellerController.showProductDetails();
                break;
            }
            case "Show Product Buyers" : {
                SellerController.showProductBuyers();
                break;
            }
            case "Show Balance" : {
                SellerController.showBalance();
                break;
            }
            case "Show Categories" : {
                SellerController.showCategories();
                break;
            }
            case "Check If Category Exists" : {
                SellerController.checkIfCategoryExists();
                break;
            }
            case "Show Seller Requests" : {
                SellerController.showSellerRequests();
                break;
            }
            case "Log in" : {
                RegisterAndLogin.login();
                break;
            }
            case "Register":{
                RegisterAndLogin.register();
                break;
            }
            case "Get Account":{
                GetDataFromDatabase.getAccount();
                break;
            }
            case "Get Product":{
                GetDataFromDatabase.getProduct();
                break;
            }
            case "Get Off":{
                GetDataFromDatabase.getOff();
                break;
            }
            case "Get Sale":{
                GetDataFromDatabase.getSale();
                break;
            }
            case "Get Request":{
                GetDataFromDatabase.getRequest();
                break;
            }
            case "Get Category":{
                GetDataFromDatabase.getCategory();
                break;
            }
            case "Check If Any Admin Exists":{
                GetDataFromDatabase.checkIfAnyAdminExists();
                break;
            }
            case "Find Sellers From Product Id":{
                GetDataFromDatabase.findSellersFromProductId();
                break;
            }
            case "offline" : {
                String token = ClientHandler.receiveMessage();
                Server.removeOnlineUser(users.get(token));
                users.remove(token);
                break;
            }
            case "Get Online Users List" : {
                ClientHandler.sendObject(Server.getOnlineUsers());
                break;
            }

        }



    }

}
