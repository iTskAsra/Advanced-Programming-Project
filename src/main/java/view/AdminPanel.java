package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.AdminController;
import controller.GetDataFromDatabase;
import controller.SetDataToDatabase;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminPanel extends Menu {
    public AdminPanel(Menu parentMenu) {
        super("User Panel",parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1,showAdminInfo());
        submenus.put(2,editAdminInfo());
        submenus.put(3,new ManageUsersMenu("Manage Users",this));
        submenus.put(4,new ManageAllProducts("Manage All Products",this));
        submenus.put(5,createDiscountCode());
        submenus.put(6,new ManageSalesMenu("Sales Menu",this));
        submenus.put(7, new ManageRequestsMenu("Requests Menu",this));
        submenus.put(8,new ManageCategoriesMenu("Categories Menu",this));

        this.setSubmenus(submenus);
    }

    private Menu createDiscountCode() {
        return new Menu("Create Discount Code",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                System.out.println("Enter start date : (in yyyy-MM-dd HH:mm format)");
                String startDate = Menu.scanner.nextLine();
                System.out.println("Enter end date : (in yyyy-MM-dd HH:mm format)");
                String endDate = Menu.scanner.nextLine();
                System.out.println("Enter discount percent :");
                String percentString = Menu.scanner.nextLine();
                double percent = Double.parseDouble(percentString);
                System.out.println("Enter maximum discount amount :");
                String maximumDiscountString = Menu.scanner.nextLine();
                double maximumDiscount = Double.parseDouble(maximumDiscountString);
                System.out.println("Enter valid times :");
                String validTimesString = Menu.scanner.nextLine();
                int validTimes = Integer.parseInt(validTimesString);
                ArrayList<Account> saleAccounts = new ArrayList<>();
                System.out.println("Enter usernames : (separate by ,)");
                String users = Menu.scanner.nextLine();
                String[] userList = users.split("\\s*,\\s*");
                for (String i : userList){
                    Account account = GetDataFromDatabase.getAccount(i);
                    saleAccounts.add(account);
                }
                Sale sale = new Sale(startDate,endDate,percent,maximumDiscount,validTimes,saleAccounts);
                while (AdminController.checkSaleCode(sale.getSaleCode())){
                    sale.setSaleCode(Sale.getRandomSaleCode());
                }
                for (Account i : saleAccounts){
                    i.getSaleCodes().add(sale);
                    SetDataToDatabase.setAccount(i);
                }
                SetDataToDatabase.setSale(sale);
            }
        };
    }

    protected Menu showAdminInfo(){
        return new Menu("Admin Info",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                String adminData = AdminController.showAdminInfo();
                Gson gson = new GsonBuilder().serializeNulls().create();
                Admin admin = gson.fromJson(adminData,Admin.class);
                System.out.printf("Username : %s\nFirst Name : %s\nLast name : %s\nE-Mail : %s\nPhone Number : %s\n",admin.getUsername(),admin.getFirstName(),admin.getLastName(),admin.getEmail(),admin.getPhoneNumber());
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    protected Menu editAdminInfo(){
        return new Menu("Edit Info",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                String fields = Menu.scanner.nextLine();
                String[] splitFields = fields.split("\\s*,\\s*");
                HashMap<String,String> editedData = new HashMap<>();
                for (String i : splitFields){
                    System.out.printf("Enter new %s\n",i.substring(0, 1).toUpperCase() + i.substring(1));
                    String newValue = Menu.scanner.nextLine();
                    editedData.put(i,newValue);
                }
                AdminController.editAdminInfo(editedData);
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }
}
