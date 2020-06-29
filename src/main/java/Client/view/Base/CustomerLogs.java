package Client.view.Base;

import controller.CustomerController;
import controller.ExceptionsLibrary;
import controller.SortController;
import model.BuyLog;
import model.Product;
import view.Base.Main;
import view.Base.Menu;
import view.Base.SortHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerLogs extends view.Base.Menu {
    public CustomerLogs(String name, view.Base.Menu parentMenu) {
        super("Customer Logs", parentMenu);
        HashMap<Integer, view.Base.Menu> submenus = new HashMap<>();
        submenus.put(1, showCustomerLogs());
        submenus.put(2, showLogDetail());
        submenus.put(3, rateProduct());
        submenus.put(submenus.size()+1,help());

        this.setSubmenus(submenus);
    }

    protected view.Base.Menu help() {
        return new view.Base.Menu("Help",this) {
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

    private view.Base.Menu showCustomerLogs() {
        return new view.Base.Menu("Customer Logs",this) {
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
                System.out.println("Do you want to sort? (yes/no each time you (want/don't want) to sort)");
                while (view.Base.Main.scanInput("String").trim().equalsIgnoreCase("yes")) {
                    SortHandler.sortBuyLogs();
                    SortController.sortBuyLogs(logs);
                    for (BuyLog i : logs){
                        System.out.printf("Log ID : %d     Log Date : %s     Log Value : %.2f\n",i.getLogId(),i.getLogDate(),i.getValue());
                    }
                    System.out.println("Sort again? (yes/no)");
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    public view.Base.Menu showLogDetail() {
        return new view.Base.Menu("Log Detail", this) {

            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter Log ID:");
            }

            @Override
            public void run() {
                int logID = Integer.parseInt(view.Base.Main.scanInput("int"));
                BuyLog buyLog = null;
                try {
                    buyLog = CustomerController.showCustomerLogDetail(logID);
                    System.out.println("-".repeat(40));
                    System.out.printf("Log ID : %d\nLog Date : %s\nLog Value : %.2f\nDiscount Applied : %.2f\nProducts :\n",buyLog.getLogId(),buyLog.getLogDate(),buyLog.getValue(),buyLog.getDiscountApplied());
                    for (String[] i :buyLog.getLogProducts()){
                        System.out.printf("Product ID : %d     Product Name : %s     Product Price : %.2f   Quantity : %d\n",Integer.parseInt(i[0]),i[1],Double.parseDouble(i[2]),Integer.parseInt(i[3]));
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

    public view.Base.Menu rateProduct() {
        return new Menu("Rate Product", this) {

            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter Product ID:");
            }

            @Override
            public void run() {
                int productID = Integer.parseInt(view.Base.Main.scanInput("int"));
                System.out.println("Enter the Rate in the scale of 1-5");
                double productRate = Integer.parseInt(Main.scanInput("double"));
                try {
                    CustomerController.rateProduct(productID, productRate);
                    System.out.println("Rated!");
                } catch (ExceptionsLibrary.NoProductException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

}
