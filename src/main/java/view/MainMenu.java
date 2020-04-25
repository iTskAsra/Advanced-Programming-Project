package view;

import controller.AdminController;
import controller.CustomerController;
import controller.SellerController;

import java.util.HashMap;

public class MainMenu extends Menu {
    public MainMenu() {
        super("Main Menu", null);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        if (Main.checkLoggedIn() != null){
            if (Main.checkLoggedIn().equals("Customer")){
                submenus.put(1, new CustomerPanel(this));
            }
            else if (Main.checkLoggedIn().equals("Seller")){
                submenus.put(1, new SellerPanel(this));
            }
            else if (Main.checkLoggedIn().equals("Admin")){
                submenus.put(1, new AdminPanel(this));
            }
        }
        else submenus.put(1, new RegisterAndLoginPanel(this));
        submenus.put(2, new AllProductsPanel(this));
        submenus.put(3, new OffPanel(this));
        if (Main.checkLoggedIn() != null){
            submenus.put(4,logout());
            submenus.put(5,help());
        }
        else {
            submenus.put(4,help());
        }

        this.setSubmenus(submenus);
    }

    protected Menu help() {
        return new Menu("Help",this) {
            @Override
            public void show() {
                System.out.println("------------------------------");
                System.out.printf("Main Menu\nHere you can login and logout,\nview your account details,\nsearch for products and offs\nand see your cart\n");
                System.out.println("------------------------------");

            }

            @Override
            public void run() {
                Menu nextMenu = new MainMenu();
                nextMenu.show();
                nextMenu.run();
            }
        };
    }

    protected Menu logout() {
        return new Menu("Logout",this) {
            @Override
            public void show() {

            }

            @Override
            public void run() {
                if (CustomerController.getCustomer() != null){
                    CustomerController.setCustomer(null);
                }
                else if (SellerController.getSeller() != null){
                    SellerController.setSeller(null);
                }
                else if (AdminController.getAdmin() != null){
                    AdminController.setAdmin(null);
                }
                System.out.println("Logged Out!");
                Menu nextMenu = new MainMenu();
                nextMenu.show();
                nextMenu.run();
            }
        };
    }


}
