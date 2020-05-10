package view;

import controller.*;
import model.Off;
import controller.ProductPageController;
import java.util.ArrayList;

import com.google.gson.*;
import model.Product;
import java.lang.String;
import view.ProductPage;


public class OffPanel extends Menu {
    public OffPanel(Menu parentMenu) {
        super("Off Menu",parentMenu);
    }

    //TODO show products

    public Menu listOffs(){
        return new Menu("List of Offs",this){
            @Override
            public void show(){
                System.out.println("a List of The Offerings");
            }
            @Override
            public void run(){
                ArrayList<Off> offsList = null;
                try {
                    offsList = OffPageController.listOffs();
                    for (int i=0;i<offsList.size();i++)
                        System.out.printf("%d. %s\n",i,offsList.get(i));
                } catch (ExceptionsLibrary.NoOffException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }



    public Menu goToProductPage(){
        return new Menu("Product Page",this){
            @Override
            public void show(){
                System.out.println(this.getName() + ":");
                System.out.println("Enter product ID :");
            }

            @Override
            public void run() {
                int productId = Integer.parseInt(Menu.scanner.nextLine());
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
