package view.Base;

import controller.AdminController;
import controller.ExceptionsLibrary;

import java.util.HashMap;

public class ManageAllProducts extends Menu {
    public ManageAllProducts(String name, Menu parentMenu) {
        super(name,parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, removeProduct());
        submenus.put(submenus.size()+1,help());

        this.setSubmenus(submenus);
    }

    protected Menu help() {
        return new Menu("Help",this) {
            @Override
            public void show() {
                System.out.println("------------------------------");
                System.out.printf("Manage All Products \nYou can see all the products here and remove them if you want\n");
                System.out.println("------------------------------");
            }

            @Override
            public void run() {
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu removeProduct() {
        return new Menu("Remove Product",this) {
            @Override
            public void show() {
                System.out.println("Enter product ID :");
            }

            @Override
            public void run() {
                int productId = Integer.parseInt(Main.scanInput("int"));
                try {
                    AdminController.deleteProduct(productId);
                    System.out.println("Removed Product!");
                } catch (ExceptionsLibrary.NoProductException | ExceptionsLibrary.NoAccountException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

}
