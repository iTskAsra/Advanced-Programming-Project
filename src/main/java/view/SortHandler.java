package view;

import controller.SortController;
import model.Product;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class SortHandler {

    public SortHandler(String typeToSort) {
    }

    public static void sortAccount() {
        System.out.println("Sort by :");
        HashMap<Integer, String> fields = new HashMap<>();
        fields.put(1, "Username");
        fields.put(2, "Role");
        fields.put(3, "First Name");
        fields.put(4, "Last Name");
        fields.put(5, "Email");
        fields.put(6, "Phone Number");
        fields.put(7, "Credit");
        for (int i : fields.keySet()) {
            System.out.println(i + ". " + fields.get(i));
        }
        int chosenNumber = Integer.parseInt(Main.scanInput("int"));
        while (chosenNumber > fields.size() || chosenNumber < 1) {
            System.out.println("Invalid number, try again");
            chosenNumber = Integer.parseInt(Main.scanInput("int"));
        }
        SortController.setSortElement(fields.get(chosenNumber));
    }

    public static void sortSale() {
        System.out.println("Sort by :");
        HashMap<Integer, String> fields = new HashMap<>();
        fields.put(1, "Sale Code");
        fields.put(2, "Start Date");
        fields.put(3, "End Date");
        fields.put(4, "Sale Percent");
        fields.put(5, "Sale Max Amount");
        fields.put(6, "Valid Times");
        for (int i : fields.keySet()) {
            System.out.println(i + ". " + fields.get(i));
        }
        int chosenNumber = Integer.parseInt(Main.scanInput("int"));
        while (chosenNumber > fields.size() || chosenNumber < 1) {
            System.out.println("Invalid number, try again");
            chosenNumber = Integer.parseInt(Main.scanInput("int"));
        }
        SortController.setSortElement(fields.get(chosenNumber));
    }

    public static void sortRequest() {
        System.out.println("Sort by :");
        HashMap<Integer, String> fields = new HashMap<>();
        fields.put(1, "Request ID");
        fields.put(2, "Request Description");
        fields.put(3, "Request Type");
        fields.put(4, "Request Condition");
        fields.put(5, "Request Seller");
        for (int i : fields.keySet()) {
            System.out.println(i + ". " + fields.get(i));
        }
        int chosenNumber = Integer.parseInt(Main.scanInput("int"));
        while (chosenNumber > fields.size() || chosenNumber < 1) {
            System.out.println("Invalid number, try again");
            chosenNumber = Integer.parseInt(Main.scanInput("int"));
        }
        SortController.setSortElement(fields.get(chosenNumber));
    }

    public static void sortCategories() {
        System.out.println("Sort by :");
        HashMap<Integer, String> fields = new HashMap<>();
        fields.put(1, "Name");
        for (int i : fields.keySet()) {
            System.out.println(i + ". " + fields.get(i));
        }
        int chosenNumber = Integer.parseInt(Main.scanInput("int"));
        while (chosenNumber > fields.size() || chosenNumber < 1) {
            System.out.println("Invalid number, try again");
            chosenNumber = Integer.parseInt(Main.scanInput("int"));
        }
        SortController.setSortElement(fields.get(chosenNumber));
    }

    public static void sortSellLogs() {
        System.out.println("Sort by :");
        HashMap<Integer, String> fields = new HashMap<>();
        fields.put(1, "Log ID");
        fields.put(2, "Log Date");
        fields.put(3, "Value");
        fields.put(4, "Discount Applied");
        fields.put(5, "Buyer");
        for (int i : fields.keySet()) {
            System.out.println(i + ". " + fields.get(i));
        }
        int chosenNumber = Integer.parseInt(Main.scanInput("int"));
        while (chosenNumber > fields.size() || chosenNumber < 1) {
            System.out.println("Invalid number, try again");
            chosenNumber = Integer.parseInt(Main.scanInput("int"));
        }
        SortController.setSortElement(fields.get(chosenNumber));
    }

    public static void sortBuyLogs() {
        System.out.println("Sort by :");
        HashMap<Integer, String> fields = new HashMap<>();
        fields.put(1, "Log ID");
        fields.put(2, "Log Date");
        fields.put(3, "Value");
        fields.put(4, "Discount Applied");
        for (int i : fields.keySet()) {
            System.out.println(i + ". " + fields.get(i));
        }
        int chosenNumber = Integer.parseInt(Main.scanInput("int"));
        while (chosenNumber > fields.size() || chosenNumber < 1) {
            System.out.println("Invalid number, try again");
            chosenNumber = Integer.parseInt(Main.scanInput("int"));
        }
        SortController.setSortElement(fields.get(chosenNumber));
    }

    public static void sortProducts() {
        System.out.println("Sort by :");
        HashMap<Integer, String> fields = new HashMap<>();
        fields.put(1, "Product ID");
        fields.put(2, "Name");
        fields.put(3, "Company");
        fields.put(4, "Price");
        fields.put(5, "Date");
        fields.put(6, "Availability");
        for (int i : fields.keySet()) {
            System.out.println(i + ". " + fields.get(i));
        }
        int chosenNumber = Integer.parseInt(Main.scanInput("int"));
        while (chosenNumber > fields.size() || chosenNumber < 1) {
            System.out.println("Invalid number, try again");
            chosenNumber = Integer.parseInt(Main.scanInput("int"));
        }
        SortController.setSortElement(fields.get(chosenNumber));
    }

    public static void sortOffs() {
        System.out.println("Sort by :");
        HashMap<Integer, String> fields = new HashMap<>();
        fields.put(1, "Off ID");
        fields.put(2, "Off Condition");
        fields.put(3, "Start Date");
        fields.put(4, "End Date");
        fields.put(5, "Off Amount");
        for (int i : fields.keySet()) {
            System.out.println(i + ". " + fields.get(i));
        }
        int chosenNumber = Integer.parseInt(Main.scanInput("int"));
        while (chosenNumber > fields.size() || chosenNumber < 1) {
            System.out.println("Invalid number, try again");
            chosenNumber = Integer.parseInt(Main.scanInput("int"));
        }
        SortController.setSortElement(fields.get(chosenNumber));
    }




}
