package Client.view.Base;

import controller.AdminController;
import controller.CustomerController;
import controller.SellerController;
import view.Base.*;
import view.Base.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Menu {

    protected String name;
    protected Menu parentMenu;
    protected HashMap<Integer , Menu> submenus;
    public static Scanner scanner;
    public static ArrayList<Menu> allMenus;
    public static void setScanner(Scanner scanner) {
        Menu.scanner = scanner;
    }
    public void setParentMenu(Menu parentMenu){
        this.parentMenu = parentMenu;
    }
    static {
        allMenus = new ArrayList<>();
    }


    public Menu(String name , Menu parentMenu){
        this.name = name;
        this.parentMenu = parentMenu;
        allMenus.add(this);
    }

    public String getName(){
        return this.name;
    }

    public Menu getParentMenu(){
        return this.parentMenu;
    }

    public void setSubmenus(HashMap<Integer , Menu> submenus){
        this.submenus = submenus;
    }

    public HashMap<Integer , Menu> getSubmenus(){
        return this.submenus;
    }

    public void show(){
        System.out.println(this.name + " :");

        for(int i = 1 ; i <= this.submenus.size() ; i++){
            System.out.println(i + ". " + this.submenus.get(i).getName());
        }

        if (view.Base.Main.checkLoggedIn() !=null){
            System.out.println((submenus.size() + 1) + ". Logout");
        }
        else {
            System.out.println((submenus.size() + 1) + ". Login or Register");
        }

        if (this instanceof MainMenu){
            System.out.println((submenus.size() + 2) + ". Exit");
        }
        else {
            System.out.println((submenus.size() + 2) + ". Back");
        }
    }

    public void run(){

        Menu nextMenu = null;
        int chosenNum = Integer.parseInt(view.Base.Main.scanInput("int"));
        if(chosenNum > this.submenus.size() + 2 || chosenNum < 1){
            System.out.println("Please select a valid number:");
            this.run();
        }

        if(chosenNum == this.submenus.size() + 1){
            if (Main.checkLoggedIn() != null){
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
                nextMenu = getMenuAfterLogout();
            }
            else {
                nextMenu = new RegisterAndLoginPanel(this);
            }
        }
        else if(chosenNum == this.submenus.size() + 2){
            if(this.parentMenu == null){
                System.exit(1);
            }
            else
                nextMenu = this.parentMenu;
        }
        else {
            nextMenu = this.submenus.get(chosenNum);
        }
        nextMenu.update();
        nextMenu.show();
        nextMenu.run();
    }

    protected Menu getMenuAfterLogout() {
        if (this instanceof MainMenu){
            MainMenu mainMenu = new MainMenu();
            return mainMenu;

        }
        else if (this instanceof AdminPanel){
            MainMenu mainMenu = new MainMenu();
            return mainMenu;
        }
        else if (this instanceof AllProductsPanel){
            AllProductsPanel allProductsPanel = new AllProductsPanel(this.getParentMenu());
            return allProductsPanel;
        }
        else if (this instanceof CartPanel){
            CartPanel cartPanel = new CartPanel("",this.getParentMenu());
            return cartPanel;
        }
        else if (this instanceof CustomerLogs){
            MainMenu mainMenu = new MainMenu();
            return mainMenu;
        }
        else if (this instanceof CustomerPanel){
            MainMenu mainMenu = new MainMenu();
            return mainMenu;
        }
        else if (this instanceof Digest){
            Digest digest = new Digest("",this.getParentMenu());
            return digest;
        }
        else if (this instanceof FilterPanel){
            FilterPanel filterPanel = new FilterPanel("",this.getParentMenu());
            return filterPanel;
        }
        else if (this instanceof ManageAllProducts){
            MainMenu mainMenu = new MainMenu();
            return mainMenu;
        }
        else if (this instanceof ManageCategoriesMenu){
            MainMenu mainMenu = new MainMenu();
            return mainMenu;
        }
        else if (this instanceof ManageRequestsMenu){
            MainMenu mainMenu = new MainMenu();
            return mainMenu;
        }
        else if (this instanceof ManageSalesMenu){
            MainMenu mainMenu = new MainMenu();
            return mainMenu;
        }
        else if (this instanceof ManageSellerOffs){
            MainMenu mainMenu = new MainMenu();
            return mainMenu;
        }
        else if (this instanceof ManageSellerProducts){
            MainMenu mainMenu = new MainMenu();
            return mainMenu;
        }
        else if (this instanceof ManageUsersMenu){
            MainMenu mainMenu = new MainMenu();
            return mainMenu;
        }
        else if (this instanceof OffPanel){
            OffPanel offPanel = new OffPanel(this.getParentMenu());
            return offPanel;
        }
        else if (this instanceof ProductCommentPanel){
            ProductPage productPage = new ProductPage("",this.getParentMenu());
            return productPage;
        }
        else if (this instanceof ProductPage){
            ProductPage productPage = new ProductPage("",this.getParentMenu());
            return productPage;
        }
        else if (this instanceof SellerPanel){
            MainMenu mainMenu = new MainMenu();
            return mainMenu;
        }
        else if (this instanceof SortPanel){
            SortPanel sortPanel = new SortPanel("",this.getParentMenu());
            return sortPanel;
        }
        return this;
    }

    public void update(){
        if (this instanceof MainMenu){
            MainMenu mainMenu = new MainMenu();
            this.setSubmenus(mainMenu.getSubmenus());
        }
        else if (this instanceof AdminPanel){
            AdminPanel adminPanel = new AdminPanel(getParentMenu());
            this.setSubmenus(adminPanel.getSubmenus());
        }
    }

}
