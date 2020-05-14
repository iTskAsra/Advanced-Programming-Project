package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.AdminController;
import controller.ExceptionsLibrary;
import controller.SortController;
import model.Account;
import model.Admin;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageUsersMenu extends Menu {

    public ManageUsersMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, showAllUsers());
        submenus.put(2, viewUsername());
        submenus.put(3, deleteUsername());
        submenus.put(4, createManagerProfile());
        submenus.put(submenus.size()+1,help());

        this.setSubmenus(submenus);
    }

    protected Menu help() {
        return new Menu("Help",this) {
            @Override
            public void show() {
                System.out.println("------------------------------");
                System.out.printf("Manage Users Panel\nHere you can view users info, remove them or create a manager profile.\n");
                System.out.println("------------------------------");
            }

            @Override
            public void run() {
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu showAllUsers() {
        return new Menu("Show all users", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                ArrayList<Account> allUsers = null;
                try {
                    allUsers = AdminController.showAllUsers();
                    for (Account i : allUsers) {
                        System.out.printf("%-15s%s%20s\n", i.getUsername(), " ".repeat(5), i.getFirstName() + " " + i.getLastName());
                    }
                    System.out.println("Do you want to sort? (yes/no each time you (want/don't want) to sort)");
                    while (Main.scanInput("String").trim().equalsIgnoreCase("yes")) {
                        SortHandler.sortAccount();
                        SortController.sortAccounts(allUsers);
                        for (Account i : allUsers) {
                            System.out.printf("%-15s%s%20s\n", i.getUsername(), " ".repeat(5), i.getFirstName() + " " + i.getLastName());
                        }
                        System.out.println("Sort again? (yes/no)");
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
                    System.out.printf("%s\n%-20s%10s%30s\n%-20s%s%30s\n%-20s%s%30s\n%-20s%s%30s\n%-20s%s%30s\n%-20s%s%30s\n%s\n", "-".repeat(60), "Username :", " ".repeat(10), account.getUsername(), "First Name :", " ".repeat(10), account.getFirstName(), "Last Name :", " ".repeat(10), account.getLastName(), "Role : ", " ".repeat(10), account.getRole(), "Email", " ".repeat(10), account.getEmail(), "Phone Number", " ".repeat(10), account.getPhoneNumber(), "-".repeat(60));
                } catch (ExceptionsLibrary.NoAccountException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }
}
