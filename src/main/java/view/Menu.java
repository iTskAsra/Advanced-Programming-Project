package view;

import controller.AdminController;
import controller.CustomerController;
import controller.SellerController;

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

        if (this.parentMenu != null)
            System.out.println((submenus.size() + 2) + ". Back");
        else {
            System.out.println((submenus.size() + 2) + ". Exit");
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
                nextMenu = new MainMenu();
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

    public void update(){
        if (this instanceof MainMenu){
            MainMenu mainMenu = new MainMenu();
            this.setSubmenus(mainMenu.getSubmenus());
        }
        else if (this instanceof AdminPanel){
            AdminPanel adminPanel = new AdminPanel(null);
            this.setSubmenus(adminPanel.getSubmenus());
        }
        //TODO complete this list
    }

}
