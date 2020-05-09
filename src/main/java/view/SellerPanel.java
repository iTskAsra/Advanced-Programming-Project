package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.SellerController;
import model.Product;
import model.SellLog;

import java.util.ArrayList;


public class SellerPanel extends Menu {
    public SellerPanel(Menu parentMenu) {
        super("User Panel", parentMenu);
    }

    private Menu showBalance() {
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

    private Menu sell() {
        return new Menu("Sell", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void run() {
                super.run();
            }
        };
    }

    private Menu showSellerCompany() {
        return new Menu("Seller Company", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                System.out.println(SellerController.showSellerCompany());
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu showSellerLogs() {
        return new Menu("Seller Logs", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                String sellerLogsGson = controller.SellerController.showSellerLogs();
                Gson gson = new GsonBuilder().serializeNulls().create();
                ArrayList<SellLog> sellerLogs = gson.fromJson(sellerLogsGson, ArrayList.class);
                for (int i = 1; i <= sellerLogs.size(); i++) {
                    System.out.println("*".repeat(50));
                    System.out.printf("%d \nLog ID: %s\nDate: %s\nValue: %lf\nDiscount Applied: %d%\nBuyer's Name: %s\nDelivery Condition: \n", sellerLogs.get(i).getLogId(), sellerLogs.get(i).getLogDate(), sellerLogs.get(i).getValue(), sellerLogs.get(i).getDiscountApplied(), (sellerLogs.get(i).getBuyerName().getFirstName() + sellerLogs.get(i).getBuyerName().getLastName()), sellerLogs.get(i).getDeliveryCondition());
                    System.out.printf("List Of Products:\n%d: %s\n", i, sellerLogs.get(i).getLogProducts().get(i).getName());
                    System.out.println("*".repeat(50));
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu addProductRequest() {
        return new Menu("Add Product Request", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void run() {
                super.run();
            }
        };
    }


    private Menu removeProductRequest() {
        return new Menu("Remove Product Request", this) {

        };
    }

    private Menu showCategories() {
        return new Menu("Categories", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                //String categoriesGson = SellerController.showCategories();
                AllProductsPanel allProductPanel = new AllProductsPanel(this);
                allProductPanel.viewCategories().show();
                allProductPanel.viewCategories().run();
            }
        };
    }

    private Menu viewAndManageProducts() {
        return new Menu("Products", this) {
            String productsGson = controller.SellerController.showSellerProducts();
            Gson gson = new GsonBuilder().serializeNulls().create();
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
