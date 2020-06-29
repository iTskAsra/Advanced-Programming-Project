package view.Base;

import controller.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static Scanner scanner;
    public static LocalDateTime localDateTime = LocalDateTime.now();
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static StringProperty status = new SimpleStringProperty();

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        Menu.setScanner(Main.scanner);
        Menu mainMenu = new MainMenu();
        GetDataFromDatabase.setResources();
        try {
            SetPeriodicSales.setPeriodicSales();
            SetPeriodicSales.removeExpiredOff();
        } catch (ExceptionsLibrary.NoAccountException | ExceptionsLibrary.NoProductException | ParseException | ExceptionsLibrary.NoOffException e) {
            e.printStackTrace();
        }
        mainMenu.show();
        mainMenu.run();
    }

    public static String getStatus() {
        return status.get();
    }

    public static StringProperty statusProperty() {
        checkLoggedIn();
        return status;
    }

    public static void setStatus(String status) {
        Main.status.set(status);
    }

    public static String checkLoggedIn(){
        if (CustomerController.getCustomer() != null){
            status.setValue("Customer");
            return "Customer";
        }
        else if (SellerController.getSeller() != null){
            status.setValue("Seller");
            return "Seller";
        }
        else if (AdminController.getAdmin() != null){
            status.setValue("Admin");
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
