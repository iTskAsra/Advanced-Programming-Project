package client.view.Base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.CustomerController;
import controller.ExceptionsLibrary;
import controller.SortController;
import model.Customer;
import model.Sale;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerPanel extends Menu {
    public CustomerPanel(Menu parentMenu) {
        super("User Panel", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();

        submenus.put(1, showCustomerInfo());
        submenus.put(2, editCustomerInfo());
        submenus.put(3, new CartPanel("CartPanel", this));
        submenus.put(4, new CustomerLogs("Customer Logs", this));
        submenus.put(5, viewBalance());
        submenus.put(6, viewDiscountCodes());
        submenus.put(submenus.size()+1,help());

        this.setSubmenus(submenus);
    }

    protected Menu help() {
        return new Menu("Help",this) {
            @Override
            public void show() {
                System.out.println("------------------------------");
                System.out.printf("Customer Panel\\nHere is the Menu for Customers\nYou can see the personal info, cart, orders, balance and discount codes and also you can purchase in this menu.\n");
                System.out.println("------------------------------");

            }

            @Override
            public void run() {
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu showCustomerInfo() {
        return new Menu("Show Customer Info", this) {

            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                String customerData = null;
                try {
                    customerData = CustomerController.showCustomerInfo();
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    Customer customer = gson.fromJson(customerData, Customer.class);
                    System.out.printf("Username : %s\nFirst Name : %s\nLast name : %s\nE-Mail : %s\nPhone Number : %s\n", customer.getUsername(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhoneNumber());
                } catch (ExceptionsLibrary.NoAccountException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }

        };
    }

    private Menu editCustomerInfo() {
        return new Menu("Edit Customer Info", this) {

            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter fields you want to change (password, firstName, lastName, email, phoneNumber): (separate by comma)");
            }

            @Override
            public void run() {
                String fields = Main.scanInput("String");
                String[] splitFields = fields.split("\\s*,\\s*");
                HashMap<String, String> editedData = new HashMap<>();
                for (String i : splitFields) {
                    if (!i.equalsIgnoreCase("username")) {
                        System.out.printf("Enter new %s\n", i.substring(0, 1).toUpperCase() + i.substring(1));
                        String newValue = Main.scanInput("String");
                        editedData.put(i, newValue);
                    }
                    else {
                        System.out.println("You can't change your username!");
                    }
                }
                try {
                    CustomerController.editCustomerInfo(editedData);
                    System.out.println("Edited info!");
                } catch (ExceptionsLibrary.NoAccountException e) {
                    System.out.println(e.getMessage());
                } catch (ExceptionsLibrary.NoFeatureWithThisName noFeatureWithThisName) {
                    System.out.println(noFeatureWithThisName.getMessage());
                } catch (ExceptionsLibrary.ChangeUsernameException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu viewBalance() {
        return new Menu("View Balance", this) {

            @Override
            public void show() {
                double balance = CustomerController.showCustomerBalance();
                System.out.println("Your Current Balance is: " + balance);
            }

            @Override
            public void run() {
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu viewDiscountCodes() {
        return new Menu("View Discount Codes", this) {

            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                ArrayList<Sale> discountCodes = CustomerController.showDiscountCodes();
                for (Sale i : discountCodes){
                    System.out.printf("Sale Code : %s     Sales Start Date : %s     Sale End Date : %s   Sale Percent : %.2f     Sale Max Amount : %.2f     Sale Remaining Valid Times : %d\n",i.getSaleCode(),i.getStartDate(),i.getEndDate(),i.getSalePercent(),i.getSaleMaxAmount(),i.getValidTimes());
                }
                System.out.println("Do you want to sort? (yes/no each time you (want/don't want) to sort)");
                while (Main.scanInput("String").trim().equalsIgnoreCase("yes")) {
                    SortHandler.sortSale();
                    SortController.sortSales(discountCodes);
                    for (Sale i : discountCodes){
                        System.out.printf("Sale Code : %s     Sales Start Date : %s     Sale End Date : %s   Sale Percent : %.2f     Sale Max Amount : %.2f     Sale Remaining Valid Times : %d\n",i.getSaleCode(),i.getStartDate(),i.getEndDate(),i.getSalePercent(),i.getSaleMaxAmount(),i.getValidTimes());
                    }
                    System.out.println("Sort again? (yes/no)");
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }
}
