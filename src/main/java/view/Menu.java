package view;

import controller.AdminController;
import controller.CustomerController;
import controller.SellerController;
import model.Seller;

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

        if (Main.checkLoggedIn() !=null){
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
        int chosenNum = Integer.parseInt(Main.scanInput("int"));
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
            this.setSubmenus(mainMenu.getSubmenus());
        }
        else if (this instanceof AdminPanel){
            MainMenu mainMenu = new MainMenu();
            mainMenu.setSubmenus(mainMenu.getSubmenus());
            return mainMenu;
        }
        else if (this instanceof AllProductsPanel){
            AllProductsPanel allProductsPanel = new AllProductsPanel(null);
            this.setSubmenus(allProductsPanel.getSubmenus());
        }
        else if (this instanceof CartPanel){
            CartPanel cartPanel = new CartPanel("",null);
            this.setSubmenus(cartPanel.getSubmenus());
        }
        else if (this instanceof CustomerLogs){
            MainMenu mainMenu = new MainMenu();
            this.setSubmenus(mainMenu.getSubmenus());
        }
        else if (this instanceof CustomerPanel){
            MainMenu mainMenu = new MainMenu();
            this.setSubmenus(mainMenu.getSubmenus());
        }
        else if (this instanceof Digest){
            Digest digest = new Digest("",null);
            this.setSubmenus(digest.getSubmenus());
        }
        else if (this instanceof FilterPanel){
            FilterPanel filterPanel = new FilterPanel("",null);
            this.setSubmenus(filterPanel.getSubmenus());
        }
        else if (this instanceof ManageAllProducts){
            MainMenu mainMenu = new MainMenu();
            this.setSubmenus(mainMenu.getSubmenus());
        }
        else if (this instanceof ManageCategoriesMenu){
            MainMenu mainMenu = new MainMenu();
            this.setSubmenus(mainMenu.getSubmenus());
        }
        else if (this instanceof ManageRequestsMenu){
            MainMenu mainMenu = new MainMenu();
            this.setSubmenus(mainMenu.getSubmenus());
        }
        else if (this instanceof ManageSalesMenu){
            MainMenu mainMenu = new MainMenu();
            this.setSubmenus(mainMenu.getSubmenus());
        }
        else if (this instanceof ManageSellerOffs){
            MainMenu mainMenu = new MainMenu();
            this.setSubmenus(mainMenu.getSubmenus());
        }
        else if (this instanceof ManageSellerProducts){
            MainMenu mainMenu = new MainMenu();
            this.setSubmenus(mainMenu.getSubmenus());
        }
        else if (this instanceof ManageUsersMenu){
            MainMenu mainMenu = new MainMenu();
            this.setSubmenus(mainMenu.getSubmenus());
        }
        else if (this instanceof OffPanel){
            OffPanel offPanel = new OffPanel(null);
            this.setSubmenus(offPanel.getSubmenus());
        }
        else if (this instanceof ProductCommentPanel){
            ProductPage productPage = new ProductPage("",null);
            this.setSubmenus(productPage.getSubmenus());
        }
        else if (this instanceof ProductPage){
            ProductPage productPage = new ProductPage("",null);
            this.setSubmenus(productPage.getSubmenus());
        }
        else if (this instanceof SellerPanel){
            MainMenu mainMenu = new MainMenu();
            this.setSubmenus(mainMenu.getSubmenus());
        }
        else if (this instanceof SortPanel){
            SortPanel sortPanel = new SortPanel("",null);
            this.setSubmenus(sortPanel.getSubmenus());
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
        /*else if (this instanceof AllProductsPanel){
            AllProductsPanel allProductsPanel = new AllProductsPanel(null);
            this.setSubmenus(allProductsPanel.getSubmenus());
        }
        else if (this instanceof CartPanel){
            CartPanel cartPanel = new CartPanel("",null);
            this.setSubmenus(cartPanel.getSubmenus());
        }
        else if (this instanceof CustomerLogs){
            CustomerLogs customerLogs = new CustomerLogs("",this);
            this.setSubmenus(customerLogs.getSubmenus());
        }
        else if (this instanceof CustomerPanel){
            CustomerPanel customerPanel = new CustomerPanel(null);
            this.setSubmenus(customerPanel.getSubmenus());
        }
        else if (this instanceof Digest){
            Digest digest = new Digest("",null);
            this.setSubmenus(digest.getSubmenus());
        }
        else if (this instanceof FilterPanel){
            FilterPanel filterPanel = new FilterPanel("",null);
            this.setSubmenus(filterPanel.getSubmenus());
        }
        else if (this instanceof ManageAllProducts){
            ManageAllProducts manageAllProducts = new ManageAllProducts("",null);
            this.setSubmenus(manageAllProducts.getSubmenus());
        }
        else if (this instanceof ManageCategoriesMenu){
            ManageCategoriesMenu manageCategoriesMenu = new ManageCategoriesMenu("",null);
            this.setSubmenus(manageCategoriesMenu.getSubmenus());
        }
        else if (this instanceof ManageRequestsMenu){
            ManageRequestsMenu manageRequestsMenu = new ManageRequestsMenu("",null);
            this.setSubmenus(manageRequestsMenu.getSubmenus());
        }
        else if (this instanceof ManageSalesMenu){
            ManageSalesMenu manageSalesMenu = new ManageSalesMenu("",null);
            this.setSubmenus(manageSalesMenu.getSubmenus());
        }
        else if (this instanceof ManageSellerOffs){
            ManageSellerOffs manageSellerOffs = new ManageSellerOffs("",null);
            this.setSubmenus(manageSellerOffs.getSubmenus());
        }
        else if (this instanceof ManageSellerProducts){
            ManageSellerProducts manageSellerProducts = new ManageSellerProducts("",null);
            this.setSubmenus(manageSellerProducts.getSubmenus());
        }
        else if (this instanceof ManageUsersMenu){
            ManageUsersMenu manageUsersMenu = new ManageUsersMenu("",null);
            this.setSubmenus(manageUsersMenu.getSubmenus());
        }
        else if (this instanceof OffPanel){
            OffPanel offPanel = new OffPanel(null);
            this.setSubmenus(offPanel.getSubmenus());
        }
        else if (this instanceof ProductCommentPanel){
            ProductCommentPanel productCommentPanel = new ProductCommentPanel("",null);
            this.setSubmenus(productCommentPanel.getSubmenus());
        }
        else if (this instanceof ProductPage){
            ProductPage productPage = new ProductPage("",null);
            this.setSubmenus(productPage.getSubmenus());
        }
        else if (this instanceof SellerPanel){
            SellerPanel sellerPanel = new SellerPanel(null);
            this.setSubmenus(sellerPanel.getSubmenus());
        }
        else if (this instanceof SortPanel){
            SortPanel sortPanel = new SortPanel("",null);
            this.setSubmenus(sortPanel.getSubmenus());
        }*/
    }

}
