package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.GetDataFromDatabase;
import controller.SetDataToDatabase;
import controller.SellerController;
import model.Product;
import model.Seller;
import model.SellLog;

import java.util.ArrayList;
import java.util.HashMap;


public class SellerPanel extends Menu {
    public SellerPanel(Menu parentMenu) {
        super("User Panel", parentMenu);
    }

    public  Menu sell() {
        return new Menu ("sell",this){

        };
    }

    public  Menu showSellerCompany() {
        return new Menu("Seller Company", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                System.out.println(SellerController.showSellerCompany());
            }
        };
    }

    public  Menu showSellerLogs() {
        return new Menu("Seller Logs", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                String sellerLogsGson = controller.SellerController.showSellerLogs();
                Gson gson = new Gson();
                ArrayList<SellLog> sellerLogs = gson.fromJson(sellerLogsGson, ArrayList.class);
                for (int i = 1; i <= sellerLogs.size(); i++) {
                    System.out.println("*".repeat(50));
                    System.out.printf("%d \nLog ID: %s\n");
                    System.out.println("*".repeat(50));
                }
            }
        };
    }

    public  Menu addProductRequest() {
        return new Menu ("Add Product Request",this){

        };
    }

    public  Menu removeProductRequest() {
        return new Menu ("Remove Product Request",this){

        };
    }

    public  Menu showCategories() {
        return new Menu("Categories", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                String categoriesGson = SellerController.showCategories();
            }
        };
    }

    public  Menu showBalance() {
        return new Menu("Balance", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                System.out.println(SellerController.showBalance());
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }


    public Menu viewAndManageProducts() {
        return new Menu("Products", this) {
            String productsGson = controller.SellerController.showSellerProducts();
            Gson gson = new Gson();
            ArrayList<Product> allProducts = gson.fromJson(productsGson, ArrayList.class);

            @Override
            public void show() {
                System.out.println("*".repeat(50));
                for (int i = 0; i < allProducts.size(); i++) {
                    System.out.printf("Product's ID: %d\nProduct's Name: %s\n", i, allProducts.get(i).getProductId(), allProducts.get(i).getName());
                    System.out.println("*".repeat(50));
                }
            }
        };
    }
}