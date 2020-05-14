package view;

import controller.CustomerController;
import controller.ExceptionsLibrary;
import model.BuyLog;
import model.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerLogs extends Menu {
    public CustomerLogs(String name, Menu parentMenu) {
        super("Customer Logs", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, showCustomerLogs());
        submenus.put(2, showLogDetail());
        submenus.put(3, rateProduct());
        submenus.put(submenus.size()+1,help());

        this.setSubmenus(submenus);
    }

    protected Menu help() {
        return new Menu("Help",this) {
            @Override
            public void show() {
                System.out.println("------------------------------");
                System.out.printf("Customer Orders\nHere is the Customer Orders history, you can see each order and rate each bought product on the scale of 1 to 5.\n");
                System.out.println("------------------------------");

            }

            @Override
            public void run() {
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu showCustomerLogs() {
        return new Menu("Customer Logs :",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                ArrayList<BuyLog> logs = CustomerController.showCustomerLogs();
                for (BuyLog i : logs){
                    System.out.printf("Log ID : %d     Log Date : %s     Log Value : %.2f\n",i.getLogId(),i.getLogDate(),i.getValue());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    public Menu showLogDetail() {
        return new Menu("Log Detail", this) {

            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter Log ID:");
            }

            @Override
            public void run() {
                int logID = Integer.parseInt(Main.scanInput("int"));
                BuyLog buyLog = null;
                try {
                    buyLog = CustomerController.showCustomerLogDetail(logID);
                    System.out.println("-".repeat(40));
                    System.out.printf("Log ID : %d\nLog Date : %s\nLog Value : %.2f\nDiscount Applied : %.2f\nProducts :\n",buyLog.getLogId(),buyLog.getLogDate(),buyLog.getValue(),buyLog.getDiscountApplied());
                    for (Product i :buyLog.getLogProducts().keySet()){
                        System.out.printf("Product ID : %d     Product Name : %s     Product Seller : %s     Product Price : %.2f   Quantity : %d\n",i.getProductId(),i.getName(),i.getSeller().getUsername(),i.getPriceWithOff(),buyLog.getLogProducts().get(i));
                    }
                    System.out.println("-".repeat(40));
                } catch (ExceptionsLibrary.NoLogException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    public Menu rateProduct() {
        return new Menu("Rate Product", this) {

            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter Product ID:");
            }

            @Override
            public void run() {
                int productID = Integer.parseInt(Main.scanInput("int"));
                System.out.println("Enter the Rate in the scale of 1-5");
                double productRate = Integer.parseInt(Main.scanInput("double"));
                try {
                    CustomerController.rateProduct(productID, productRate);
                } catch (ExceptionsLibrary.NoProductException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

}
