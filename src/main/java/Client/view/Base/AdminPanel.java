package Client.view.Base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.*;
import model.*;
import view.Base.*;
import view.Base.Main;
import view.Base.Menu;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminPanel extends view.Base.Menu {
    public AdminPanel(view.Base.Menu parentMenu) {
        super("User Panel",parentMenu);
        HashMap<Integer, view.Base.Menu> submenus = new HashMap<>();
        submenus.put(1,showAdminInfo());
        submenus.put(2,editAdminInfo());
        submenus.put(3,new ManageUsersMenu("Manage Users",this));
        submenus.put(4,new ManageAllProducts("Manage All Products",this));
        submenus.put(5,createDiscountCode());
        submenus.put(6,new ManageSalesMenu("Sales Menu",this));
        submenus.put(7, new ManageRequestsMenu("Requests Menu",this));
        submenus.put(8,new ManageCategoriesMenu("Categories Menu",this));
        submenus.put(submenus.size()+1,help());

        this.setSubmenus(submenus);
    }

    protected view.Base.Menu help() {
        return new view.Base.Menu("Help",this) {
            @Override
            public void show() {
                System.out.println("------------------------------");
                System.out.printf("You can manage users, products and categories,\nhandle requests and sales and edit your info.\n");
                System.out.println("------------------------------");

            }

            @Override
            public void run() {
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private view.Base.Menu createDiscountCode() {
        return new view.Base.Menu("Create Discount Code",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                System.out.println("Enter start date : (in yyyy-MM-dd HH:mm format)");
                String startDate = view.Base.Main.scanInput("String");
                System.out.println("Enter end date : (in yyyy-MM-dd HH:mm format)");
                String endDate = view.Base.Main.scanInput("String");
                System.out.println("Enter discount percent :");
                String percentString = view.Base.Main.scanInput("double");
                double percent = Double.parseDouble(percentString);
                System.out.println("Enter maximum discount amount :");
                String maximumDiscountString = view.Base.Main.scanInput("double");
                double maximumDiscount = Double.parseDouble(maximumDiscountString);
                System.out.println("Enter valid times :");
                String validTimesString = view.Base.Main.scanInput("int");
                int validTimes = Integer.parseInt(validTimesString);
                ArrayList<Account> saleAccounts = new ArrayList<>();
                System.out.println("Enter usernames : (separate by ,)");
                String users = view.Base.Main.scanInput("String");
                String[] userList = users.split("\\s*,\\s*");
                for (String i : userList){
                    Account account = null;
                    try {
                        account = GetDataFromDatabase.getAccount(i);
                        saleAccounts.add(account);
                    } catch (ExceptionsLibrary.NoAccountException e) {
                        System.out.println(e.getMessage());
                    }
                }
                Sale sale = new Sale(startDate,endDate,percent,maximumDiscount,validTimes,saleAccounts);
                AdminController.addSale(sale);
                System.out.println("Sale added!");
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    protected view.Base.Menu showAdminInfo(){
        return new view.Base.Menu("Admin Info",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                String adminData = null;
                try {
                    adminData = AdminController.showAdminInfo();
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    Admin admin = gson.fromJson(adminData,Admin.class);
                    System.out.printf("Username : %s\nFirst Name : %s\nLast name : %s\nE-Mail : %s\nPhone Number : %s\n",admin.getUsername(),admin.getFirstName(),admin.getLastName(),admin.getEmail(),admin.getPhoneNumber());
                } catch (ExceptionsLibrary.NoAccountException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    protected view.Base.Menu editAdminInfo(){
        return new Menu("Edit Info",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter fields you want to change (password, firstName, lastName, email, phoneNumber): (separate by comma)");
            }

            @Override
            public void run() {
                String fields = view.Base.Main.scanInput("String");
                String[] splitFields = fields.split("\\s*,\\s*");
                HashMap<String,String> editedData = new HashMap<>();
                for (String i : splitFields){
                    System.out.printf("Enter new %s\n",i.substring(0, 1).toUpperCase() + i.substring(1));
                    String newValue = Main.scanInput("String");
                    editedData.put(i,newValue);
                }
                try {
                    AdminController.editAdminInfo(editedData);
                    System.out.println("Edited info!");
                } catch (ExceptionsLibrary.NoFeatureWithThisName noFeatureWithThisName) {
                    System.out.println(noFeatureWithThisName.getMessage());
                } catch (ExceptionsLibrary.NoAccountException e) {
                    System.out.println(e.getMessage());
                } catch (ExceptionsLibrary.ChangeUsernameException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }
}
