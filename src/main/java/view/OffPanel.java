package view;

import controller.*;
import model.Off;
import controller.ProductPageController;
import java.util.ArrayList;

import model.Product;

import java.util.HashMap;


public class OffPanel extends Menu {
    public OffPanel(Menu parentMenu) {
        super("Off Menu",parentMenu);
        HashMap <Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, showOffs());
        submenus.put(2,goToProductPage());
        submenus.put(submenus.size()+1,help());

        this.setSubmenus(submenus);
    }

    protected Menu help() {
        return new Menu("Help",this) {
            @Override
            public void show() {
                System.out.println("------------------------------");
                System.out.printf("Off Panel\nHere you see all the products that is in an off and you can see their previous price and their price with off and you can go to their product page.\n");
                System.out.println("------------------------------");
            }

            @Override
            public void run() {
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    public Menu showOffs(){
        return new Menu("Show Offs",this){
            @Override
            public void show(){
                System.out.println("Offs :");
            }
            @Override
            public void run(){
                ArrayList<Off> offsList = null;
                try {
                    offsList = OffPageController.listOffs();
                    for (Off i : offsList){
                        System.out.println("-".repeat(50));
                        System.out.printf("Off ID : %d\nOff Start Date : %s     Off End Date : %s\nProducts :\n",i.getOffId(),i.getStartDate(),i.getEndDate());
                        for (Product j : i.getOffProducts()){
                            System.out.printf("Product ID : %d  -  Product Name : %s  -  Product Original Price : %.2f  -  Product Price With Off : %.2f\n",j.getProductId(),j.getName(),j.getPrice(),j.getPriceWithOff());
                        }
                        System.out.println("-".repeat(50));
                    }
                } catch (ExceptionsLibrary.NoOffException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }



    public Menu goToProductPage(){
        return new Menu("Go To Product Page",this){
            @Override
            public void show(){
                System.out.println(this.getName() + ":");
                System.out.println("Enter product ID :");
            }

            @Override
            public void run() {
                int productId = Integer.parseInt(Main.scanInput("int"));
                Product product = null;
                try {
                    product = AllProductsPanelController.goToProductPage(productId);
                    ProductPageController productPageController = new ProductPageController(product);
                    ProductPage productPage =new ProductPage(product.getName(),this);
                    productPage.show();
                    productPage.run();
                } catch (ExceptionsLibrary.NoProductException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }
}
