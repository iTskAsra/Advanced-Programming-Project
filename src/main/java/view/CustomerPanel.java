package view;

import controller.CustomerController;

public class CustomerPanel extends Menu {
    public CustomerPanel(Menu parentMenu) {
        super("User Panel",parentMenu);
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
