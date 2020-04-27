package view;

import controller.AdminController;
import controller.CustomerController;
import controller.GetDataFromDatabase;
import controller.SellerController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static Scanner scanner;
    public static LocalDateTime localDateTime = LocalDateTime.now();
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        Menu.setScanner(Main.scanner);
        Menu mainMenu = new MainMenu();
        GetDataFromDatabase.setResources();
        mainMenu.show();
        mainMenu.run();
    }

    public static String checkLoggedIn(){
        if (CustomerController.getCustomer() != null){
            return "Customer";
        }
        else if (SellerController.getSeller() != null){
            return "Seller";
        }
        else if (AdminController.getAdmin() != null){
            return "Admin";
        }
        else return null;

    }
}
