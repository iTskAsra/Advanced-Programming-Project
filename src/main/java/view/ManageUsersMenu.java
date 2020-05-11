package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.AdminController;
import controller.ExceptionsLibrary;
import controller.RegisterAndLogin;
import model.Account;
import model.Admin;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageUsersMenu extends Menu {

    public ManageUsersMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1,showAllUsers());
        submenus.put(2, viewUsername());
        submenus.put(3, deleteUsername());
        submenus.put(4, createManagerProfile());
        this.setSubmenus(submenus);

    }

    private Menu showAllUsers() {
        return new Menu("Show all users",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                HashMap<String,ArrayList<Account>> allUsers= null;
                try {
                    allUsers = AdminController.showAllUsers();
                    for (String i :allUsers.keySet()){
                        System.out.println(i+" :");
                        for (Account j : allUsers.get(i)){
                            System.out.printf("%-15s%s%20s\n",j.getUsername()," ".repeat(5),j.getFirstName()+" "+j.getLastName());
                        }
                    }
                } catch (ExceptionsLibrary.NoAccountException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }


    private Menu deleteUsername() {
        return new Menu("Delete Username", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter username:");
            }

            @Override
            public void run() {
                String username = Main.scanInput("String");
                try {
                    AdminController.deleteUser(username);
                    System.out.println("Deleted!");
                } catch (ExceptionsLibrary.NoAccountException e) {
                    System.out.println(e.getMessage());
                }
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
                String username = Main.scanInput("String");
                System.out.println("Enter Password:");
                String password = Main.scanInput("String");
                System.out.println("Enter First Name:");
                String firstName = Main.scanInput("String");
                System.out.println("Enter Last Name:");
                String lastName = Main.scanInput("String");
                System.out.println("Enter Email:");
                String email = Main.scanInput("String");
                System.out.println("Enter Phone Number:");
                String phoneNumber = Main.scanInput("String");
                System.out.println("Enter Credit:");
                double credit = Double.parseDouble(Main.scanInput("double"));
                Admin admin = new Admin(username, password, "Admin", firstName, lastName, email, phoneNumber, null, credit, null);
                Gson gson = new GsonBuilder().serializeNulls().create();
                String data = gson.toJson(admin);
                try {
                    AdminController.addAdminAccount(data);
                    System.out.println("Added admin account!");
                } catch (ExceptionsLibrary.UsernameAlreadyExists usernameAlreadyExists) {
                    System.out.println(usernameAlreadyExists.getMessage());
                }
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
                String username = Main.scanInput("String");
                String data = null;
                try {
                    data = AdminController.showUserDetails(username);
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    Account account = gson.fromJson(data, Account.class);
                    System.out.printf("%s\n%-20s%10s%30s\n%-20s%s%30s\n%-20s%s%30s\n%-20s%s%30s\n%-20s%s%30s\n%-20s%s%30s\n%s\n","-".repeat(60), "Username :", " ".repeat(10), account.getUsername(), "First Name :", " ".repeat(10), account.getFirstName(), "Last Name :", " ".repeat(10), account.getLastName(), "Role : ", " ".repeat(10), account.getRole(), "Email", " ".repeat(10), account.getEmail(), "Phone Number", " ".repeat(10), account.getPhoneNumber(),"-".repeat(60));
                } catch (ExceptionsLibrary.NoAccountException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }
}
