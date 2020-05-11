package view;

import controller.*;
import model.Category;
import model.Feature;
import model.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class AllProductsPanel extends Menu {
    public AllProductsPanel(Menu parentMenu) {
        super("Products Menu",parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, viewCategories());
        submenus.put(2, new FilterPanel("Filter",this));
        submenus.put(3, new SortPanel("Sort",this));
        submenus.put(4, showProducts());
        submenus.put(5,showProductDetails());

        this.setSubmenus(submenus);
    }

    public Menu viewCategories(){
        return new Menu("View Categories",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                ArrayList<Category> allCategories = null;
                try {
                    allCategories = AdminController.showCategories();
                    for (Category i : allCategories){
                        System.out.printf("%s\n","-".repeat(30));
                        System.out.printf("Category name : %s\nFeatures:\n",i.getName());
                        int featureCount = 1;
                        for (Feature j : i.getFeatures()){
                            System.out.printf("%d. %s\n",featureCount,j.getParameter());
                            featureCount++;
                        }
                        System.out.printf("%s\n","-".repeat(30));
                    }
                } catch (ExceptionsLibrary.NoCategoryException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
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
                    for (Product i : products){
                        System.out.printf("%-20s%s%d%s%s%s%d\n",i.getName()," ".repeat(10),i.getAvailability()," ".repeat(10),String.valueOf(i.getPrice())," ".repeat(10),i.getProductId());
                    }
                    System.out.printf("%s\n","-".repeat(60));
                    getParentMenu().show();
                    getParentMenu().run();
                } catch (ExceptionsLibrary.NoProductException e) {
                    System.out.println(e.getMessage());
                }
            }
        };
    }

    public Menu showProductDetails(){
        return new Menu("Show Product Details",this) {
            @Override
            public void show() {
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
                    ProductPage productPage =new ProductPage(product.getName(),this.getParentMenu());
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
