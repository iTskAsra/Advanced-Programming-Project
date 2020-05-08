package view;

import controller.CustomerController;

import java.util.HashMap;

public class CustomerLogs extends Menu {
    public CustomerLogs(String name, Menu parentMenu) {
        super("Customer Logs", parentMenu);
        HashMap<Integer , Menu> submenus = new HashMap<>();

        submenus.put(1 , showOrderDetail());
        submenus.put(2 , rateProduct());

        this.setSubmenus(submenus);
    }
    @Override
    public void show(){
        System.out.print("Your Orders: ");
    }
    @Override
    public void run(){
        System.out.println(CustomerController.showCustomerLogs());
    }


    public Menu showOrderDetail(){
        return new Menu("Order Detail" , this){

            @Override
            public void show(){
                System.out.println("Enter Order ID:");
            }

            @Override
            public void run(){
                int LogID = Integer.parseInt(Menu.scanner.nextLine());
                String LogDetail = CustomerController.showCustomerLogDetail(LogID);
                if(LogDetail == null){
                    System.out.println("You Have No Order with such a ID");
                    getParentMenu().show();
                    getParentMenu().run();
                }
                else{
                    System.out.println(LogDetail);
                    getParentMenu().show();
                    getParentMenu().run();
                }
            }
        };
    }

    public Menu rateProduct(){
        return new Menu("Rate Product" , this){

            @Override
            public void show(){
                System.out.println("Enter Product ID:");
            }

            @Override
            public void run(){
                int productID = Integer.parseInt(Menu.scanner.nextLine());
                System.out.println("Enter the Rate in the scale of 1-5");
                int productRate = Integer.parseInt(Menu.scanner.nextLine());
                CustomerController.rateProduct(productID , productRate);
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

}