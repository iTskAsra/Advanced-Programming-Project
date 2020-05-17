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
        submenus.put(2, new FilterPanel("Filter Panel", this));
        submenus.put(3, new SortPanel("Sort Panel",this));
        submenus.put(4, showProducts());
        submenus.put(5,goToProductPage());
        submenus.put(submenus.size()+1,help());

        this.setSubmenus(submenus);
    }

    public Menu showProducts(){
        return new Menu("Show Products",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                ArrayList<Product> products = null;
                try {
                    products = AllProductsPanelController.showProducts();
                    System.out.printf("Results :\n%s\n","-".repeat(60));
                    if (products.size() != 0) {
                        for (Product i : products) {
                            System.out.printf("%-20s%s%d%s%s%s%d\n", i.getName(), " ".repeat(10), i.getAvailability(), " ".repeat(10), String.valueOf(i.getPrice()), " ".repeat(10), i.getProductId());
                        }
                    }
                    else {
                        System.out.println("No product found!");
                    }
                    System.out.printf("%s\n","-".repeat(60));
                    getParentMenu().show();
                    getParentMenu().run();
                } catch (ExceptionsLibrary.NoProductException e) {
                    System.out.println(e.getMessage());
                } catch (ExceptionsLibrary.NoAccountException e) {
                    System.out.println(e.getMessage());
                } catch (ExceptionsLibrary.NoFilterWithThisName noFilterWithThisName) {
                    System.out.println(noFilterWithThisName.getMessage());
                }
            }
        };
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
                        System.out.println("Do you want to sort? (yes/no each time you (want/don't want) to sort)");
                        while (Main.scanInput("String").trim().equalsIgnoreCase("yes")) {
                            SortHandler.sortOffs();
                            SortController.sortOffs(offsList);
                            for (Product j : i.getOffProducts()){
                                System.out.printf("Product ID : %d  -  Product Name : %s  -  Product Original Price : %.2f  -  Product Price With Off : %.2f\n",j.getProductId(),j.getName(),j.getPrice(),j.getPriceWithOff());
                            }
                            System.out.println("Sort again? (yes/no)");
                        }
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
                    product = OffPageController.goToProductPage(productId);
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
