package view;

import controller.CustomerController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Menu {

    private String name;
    private Menu parentMenu;
    private HashMap<Integer , Menu> submenus;
    public static Scanner scanner;
    public static ArrayList<Menu> allMenus;

    public static void setScanner(Scanner scanner) {
        Menu.scanner = scanner;
    }

    public void setParentMenu(Menu parentMenu){
        this.parentMenu = parentMenu;
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
           System.out.println(i + ". " + this.submenus.get(i));
       }

        if (this.parentMenu != null)
            System.out.println((submenus.size() + 1) + ". Back");
        else
            System.out.println((submenus.size() + 1) + ". Exit");
    }

    public void run(){

        Menu nextMenu = null;
        int chosenNum = Integer.parseInt(scanner.nextLine());
        if(chosenNum > this.submenus.size() + 1 || chosenNum < 1){
            System.out.println("Please choose a variable number");
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
        nextMenu.show();
        nextMenu.run();
    }

}
