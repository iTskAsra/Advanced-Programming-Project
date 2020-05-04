package view;

import controller.CustomerController;
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
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {


            }
        };
    }

    private Menu viewDiscountCodes(){
        return new Menu("View Discount Codes" , this){

            @Override
            public void show(){
                    System.out.println(this.getName() + ":");

            }

            @Override
            public void run(){

            }

        };

    }
    
    public void showCustomerBalance(){
        double balance=CustomerController.showCustomerBalance();
        System.out.println("Your Current Balance is: " + balance);
    }

    public void showDiscountCodes(){
        String discountCodes = CustomerController.showDiscountCodes();
        System.out.println(discountCodes);
    }
}
