package view.Base;

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
        submenus.put(submenus.size()+1,new CartPanel("Cart",this));

        this.setSubmenus(submenus);
    }

    @Override
    public void show(){
        System.out.println(this.name + " :");

        for(int i = 1 ; i <= this.submenus.size() ; i++){
            System.out.println(i + ". " + this.submenus.get(i).getName());
        }

        if (this.parentMenu != null)
            System.out.println((submenus.size() + 1) + ". Back");
        else
            System.out.println((submenus.size() + 1) + ". Exit");
    }

    @Override
    public void run(){

        Menu nextMenu = null;
        int chosenNum = Integer.parseInt(Main.scanInput("int"));
        if(chosenNum > this.submenus.size() + 1 || chosenNum < 1){
            System.out.println("Please select a valid number:");
            this.run();
        }

        if(chosenNum == this.submenus.size() + 1){
            if(this.parentMenu == null){
                System.exit(1);
            }
            else
                nextMenu = this.parentMenu;
        }
        else
            nextMenu = this.submenus.get(chosenNum);
        nextMenu.update();
        nextMenu.show();
        nextMenu.run();
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
                getParentMenu().show();
                getParentMenu().run();
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
