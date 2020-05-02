package view;

import controller.AdminController;
import model.Account;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageAllProducts extends Menu {
    public ManageAllProducts(String name, Menu parentMenu) {
        super(name,parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, removeProduct());
        this.setSubmenus(submenus);
    }

    private Menu removeProduct() {
        return new Menu("Remove Product",this) {
            @Override
            public void show() {
                System.out.println("Enter product ID :");
            }

            @Override
            public void run() {
                int productId = Integer.parseInt(Menu.scanner.nextLine());
                AdminController.deleteProduct(productId);
                System.out.println("Removed Product!");
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

}