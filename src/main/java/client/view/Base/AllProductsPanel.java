package client.view.Base;

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
        submenus.put(submenus.size()+1,help());

        this.setSubmenus(submenus);
    }

    protected Menu help() {
        return new Menu("Help",this) {
            @Override
            public void show() {
                System.out.println("------------------------------");
                System.out.printf("All Products Panel\nHere are All the Products\nYou can see all the existing categories, sort or filter the products, and see each or all the products.\n");
                System.out.println("------------------------------");

            }

            @Override
            public void run() {
                getParentMenu().show();
                getParentMenu().run();
            }
        };
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
                    if (products.size() != 0) {
                        for (Product i : products) {
                            System.out.printf("Name : %-20s %s Quantity : %-5d %s Price : %-10s %s Product ID : %d\n", i.getName(), " ".repeat(10), i.getAvailability(), " ".repeat(10), String.valueOf(i.getPrice()), " ".repeat(10), i.getProductId());
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
                } catch (ExceptionsLibrary.NoCategoryException e) {
                    System.out.println(e.getMessage());
                } catch (ExceptionsLibrary.NoFeatureWithThisName noFeatureWithThisName) {
                    noFeatureWithThisName.printStackTrace();
                }
                getParentMenu().show();
                getParentMenu().run();
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
