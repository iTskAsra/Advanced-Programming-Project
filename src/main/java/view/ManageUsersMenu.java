package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.AdminController;
import controller.RegisterAndLogin;
import model.Account;
import model.Admin;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageUsersMenu extends Menu {

    public ManageUsersMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, viewUsername());
        submenus.put(2, deleteUsername());
        submenus.put(3, createManagerProfile());
        this.setSubmenus(submenus);
    }



    private Menu deleteUsername() {
        return new Menu("Delete Username", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                String username = Menu.scanner.nextLine();
                AdminController.deleteUser(username);
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu createManagerProfile() {
        return new Menu("Add Admin Account", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                System.out.println("Enter Username:");
                String username = scanner.nextLine();
                System.out.println("Enter Password:");
                String password = scanner.nextLine();
                System.out.println("Enter First Name:");
                String firstName = scanner.nextLine();
                System.out.println("Enter Last Name:");
                String lastName = scanner.nextLine();
                System.out.println("Enter Email:");
                String email = scanner.nextLine();
                System.out.println("Enter Phone Number:");
                String phoneNumber = scanner.nextLine();
                System.out.println("Enter Credit:");
                double credit = Double.parseDouble(scanner.nextLine());
                Admin admin = new Admin(username, password, "Admin", firstName, lastName, email, phoneNumber, null, credit, null);
                Gson gson = new GsonBuilder().serializeNulls().create();
                String data = gson.toJson(admin);
                RegisterAndLogin.registerAdmin(data);
                getParentMenu().show();
                getParentMenu().run();

            }
        };
    }

    private Menu viewUsername() {
        return new Menu("View Username", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter username:");
            }

            @Override
            public void run() {
                String username = Menu.scanner.nextLine();
                String data = AdminController.showUserDetails(username);
                Gson gson = new GsonBuilder().serializeNulls().create();
                Account account = gson.fromJson(data, Account.class);
                System.out.printf("%-20s%10s%30s\n%-20s%10s%30s\n%-20s%10s%30s\n%-20s%10s%30s\n%-20s%10s%30s\n%-20s%10s%30s\n"
                        , "Username :", " ", account.getUsername(), "First Name :", " ", account.getFirstName(), "Last Name :", " ", account.getLastName(), "Role : ", " ", account.getRole(), "Email", " ", account.getEmail(), "Phone Number", " ", account.getPhoneNumber());
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }
}
