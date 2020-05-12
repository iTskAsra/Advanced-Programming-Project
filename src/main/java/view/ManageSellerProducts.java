package view;

import controller.AllProductsPanelController;
import controller.ExceptionsLibrary;
import controller.ProductPageController;
import controller.SellerController;
import model.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ManageSellerProducts extends Menu {
    public ManageSellerProducts(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1,showSellerProducts());
        submenus.put(2,showProductDetails());
        submenus.put(3,showProductBuyers());
        submenus.put(4,editProductRequest());
        submenus.put(5,addProductRequest());
        submenus.put(6,removeProduct());

        this.setSubmenus(submenus);
    }

    private Menu removeProduct() {
        return new Menu("Remove Product",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter product ID :");
            }

            @Override
            public void run() {
                int productId = Integer.parseInt(Main.scanInput("int"));
                try {
                    SellerController.removeProduct(productId);
                    System.out.println("Product removed!");
                } catch (ExceptionsLibrary.NoProductException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu addProductRequest() {
        return new Menu("Add Product Request",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                System.out.println("Enter product name:");
                String name = Main.scanInput("String");
                System.out.println("Enter product company:");
                String company = Main.scanInput("String");
                System.out.println("Enter product price:");
                double price = Double.parseDouble(Main.scanInput("double"));
                System.out.println("Enter product quantity:");
                int quantity = Integer.parseInt(Main.scanInput("int"));
                System.out.println("Enter product category:");
                String category = Main.scanInput("String");
                System.out.println("Enter product description:");
                String description = Main.scanInput("String");
                Product product = new Product(ProductOrOffCondition.PENDING_TO_CREATE,name,company,price,null,quantity,null,new ArrayList<Feature>(),description,new ArrayList<Rate>(),new ArrayList<Comment>(),Main.localDateTime.format(Main.dateTimeFormatter),price);
                try {
                    SellerController.addProductRequest(product,category);
                    System.out.println("Request sent!");
                } catch (ExceptionsLibrary.NoCategoryException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu editProductRequest() {
        return new Menu("Edit Product Info Request",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter product ID :");
            }

            @Override
            public void run() {
                int productId = Integer.parseInt(Main.scanInput("int"));
                String fields = Main.scanInput("String");
                String[] splitFields = fields.split("\\s*,\\s*");
                HashMap<String,String> editedData = new HashMap<>();
                for (String i : splitFields){
                    System.out.printf("Enter new %s\n",i.substring(0, 1).toUpperCase() + i.substring(1));
                    String newValue = Main.scanInput("String");
                    editedData.put(i,newValue);
                }
                try {
                    SellerController.editProductRequest(productId,editedData);
                    System.out.println("Request sent!");
                } catch (ExceptionsLibrary.NoFeatureWithThisName noFeatureWithThisName) {
                    System.out.println(noFeatureWithThisName.getMessage());
                } catch (ExceptionsLibrary.NoProductException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu showProductBuyers() {
        return new Menu("Show Product Buyers",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter product ID :");
            }

            @Override
            public void run() {
                int productId = Integer.parseInt(Main.scanInput("int"));
                try {
                    ArrayList<SellLog> buyersLogs = SellerController.showProductBuyers(productId);
                    for (SellLog i : buyersLogs){
                        System.out.printf("Log ID : %d\nCustomer : %s\nDate : %s\nTotal log price : %.2f",i.getLogId(),i.getBuyer().getUsername(),i.getLogDate(),i.getValue());
                    }

                } catch (ExceptionsLibrary.NoProductException | ExceptionsLibrary.NoAccountException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu showProductDetails() {
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
                    product = SellerController.showProductDetails(productId);
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

    private Menu showSellerProducts() {
        return new Menu("Seller's Products",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                try {
                    ArrayList<Product> sellerProducts = SellerController.showSellerProducts();
                    for (Product i : sellerProducts){
                        System.out.printf("Product ID : %d\nName : %s\nPrice : %.2f\n",i.getProductId(),i.getName(),i.getPrice());
                    }
                } catch (ExceptionsLibrary.NoAccountException e) {
                    System.out.println(e.getMessage());
                }
            }
        };
    }
}
