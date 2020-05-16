package view;

import controller.*;
import model.Sale;

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
        //TODO check off dates (remove if expired) apply to their products
        GetDataFromDatabase.setResources();
        try {
            SetPeriodicSales.set();
        } catch (ExceptionsLibrary.NoAccountException e) {
            e.printStackTrace();
        }
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

    public static String scanInput(String type){
        switch (type) {
            case "String":
                String temp = Menu.scanner.nextLine();
                return temp;
            case "int":
                try {
                    String tempIntString = Menu.scanner.nextLine();
                    int tempInt = Integer.parseInt(tempIntString);
                    return tempIntString;
                }
                catch (Exception e){
                    System.out.println("Please enter a valid integer:");
                    return scanInput("int");
                }
            case "double":
                try {
                    String tempDoubleString = Menu.scanner.nextLine();
                    double tempDouble = Double.parseDouble(tempDoubleString);
                    return tempDoubleString;
                }
                catch (Exception e){
                    System.out.println("Please enter a valid double:");
                    return scanInput("double");
                }
            default:
                System.out.println("Enter again:");
                return scanInput("String");
        }
    }

}
