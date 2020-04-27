package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.AdminController;
import model.Admin;

import java.util.HashMap;

public class AdminPanel extends Menu {
    public AdminPanel(Menu parentMenu) {
        super("User Panel",parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1,showAdminInfo());
        submenus.put(2,editAdminInfo());
        submenus.put(3,new ManageUsersMenu("Manage Users",this));
        this.setSubmenus(submenus);
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
