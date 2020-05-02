package view;

import controller.AllProductsPanelController;
import model.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class AllProductsPanel extends Menu {
    public AllProductsPanel(Menu parentMenu) {
        super("Products Menu",parentMenu);
    }

    public void viewCategories(){
        ArrayList<String> categories= AllProductsPanelController.viewCategories();
        for (int i=0;i<categories.size();i++)
            System.out.println(categories.get(i));
    }

    public void showProducts(){
        ArrayList<Product> allProducts = AllProductsPanelController.showProducts();
        for (int i=0;i<allProducts.size();i++)
            System.out.println(allProducts.get(i));
    }

    public void goToProductPage(){

    }
}
