package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.AdminController;
import controller.CustomerController;
import model.Admin;
import model.Customer;

import java.util.HashMap;

public class CustomerPanel extends Menu {
    public CustomerPanel(Menu parentMenu) {
        super("User Panel",parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();

        submenus.put(1 , new CustomerInfo("Customer Info" , this));
        submenus.put(2 , new Cart("Cart" , this));
        submenus.put(3 , new PurchasePanel("Purchase Panel" , this));
        submenus.put(4 , new CustomerLogs("Customer Logs" , this));
        submenus.put(5 , viewBalance());
        submenus.put(6 , viewDiscountCodes());

        this.setSubmenus(submenus);

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

    private Menu viewDiscountCodes(){
        return new Menu("View Discount Codes" , this){

            @Override
            public void show(){
                String discountCodes = CustomerController.showDiscountCodes();
                System.out.println(discountCodes);
            }
            @Override
            public void run(){
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }
}
