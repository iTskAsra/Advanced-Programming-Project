package view.Base;

import Client.ClientController.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static Scanner scanner;
    public static LocalDateTime localDateTime = LocalDateTime.now();
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static StringProperty status = new SimpleStringProperty();

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
    

}
