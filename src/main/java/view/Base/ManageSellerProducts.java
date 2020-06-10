package view.Base;

import controller.*;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageSellerProducts extends Menu {
    public ManageSellerProducts(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, showSellerProducts());
        submenus.put(2, showProductDetails());
        submenus.put(3, showProductBuyers());
        submenus.put(4, editProductRequest());
        submenus.put(5, addProductRequest());
        submenus.put(6, removeProduct());
        submenus.put(submenus.size() + 1, help());

        this.setSubmenus(submenus);
    }

    protected Menu help() {
        return new Menu("Help", this) {
            @Override
            public void show() {
                System.out.println("------------------------------");
                System.out.printf("Manage Products Panel\nHere you see all the products you have and you can view each one of them or their buyers and also edit the product.\n");
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
        return new Menu("Remove Product", this) {
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
                } catch (ExceptionsLibrary.NoProductException | ExceptionsLibrary.NoAccountException e) {
                    System.out.println(e.getMessage());
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
                while (!SellerController.checkIfCategoryExists(category)) {
                    System.out.println("No category with this name, try again:");
                    category = Main.scanInput("String");
                }
                Category category1 = null;
                try {
                    category1 = GetDataFromDatabase.getCategory(category);
                    ArrayList<Feature> productFeatures = new ArrayList<>();
                    for (Feature i : category1.getFeatures()) {
                        System.out.printf("Enter value of feature %s:\n", i.getParameter());
                        String value = Main.scanInput("String");
                        Feature feature = new Feature(i.getParameter(), value);
                        productFeatures.add(feature);
                    }
                    System.out.println("Enter product description:");
                    String description = Main.scanInput("String");
                    Product product = new Product(ProductOrOffCondition.PENDING_TO_CREATE, name, company, price, null, quantity, category1, productFeatures, description, new ArrayList<Rate>(), new ArrayList<Comment>(), Main.localDateTime.format(Main.dateTimeFormatter), price);
                    SellerController.addProductRequest(product, category);
                    System.out.println("Request sent!");
                } catch (ExceptionsLibrary.NoCategoryException e) {
                    e.printStackTrace();
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }


    private Menu editProductRequest() {
        return new Menu("Edit Product Info Request", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter product ID :");
            }

            @Override
            public void run() {
                int productId = Integer.parseInt(Main.scanInput("int"));
                System.out.println("Enter fields to edit : (name, company, price, availability, description) (separate by comma)");
                String fields = Main.scanInput("String");
                String[] splitFields = fields.split("\\s*,\\s*");
                HashMap<String, String> editedData = new HashMap<>();
                for (String i : splitFields) {
                    System.out.printf("Enter new %s\n", i.substring(0, 1).toUpperCase() + i.substring(1));
                    String newValue = Main.scanInput("String");
                    editedData.put(i, newValue);
                }
                try {
                    SellerController.editProductRequest(productId, editedData);
                    System.out.println("Request sent!");
                } catch (ExceptionsLibrary.NoFeatureWithThisName noFeatureWithThisName) {
                    System.out.println(noFeatureWithThisName.getMessage());
                } catch (ExceptionsLibrary.NoProductException e) {
                    System.out.println(e.getMessage());
                } catch (ExceptionsLibrary.CannotChangeThisFeature cannotChangeThisFeature) {
                    System.out.println(cannotChangeThisFeature.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu showProductBuyers() {
        return new Menu("Show Product Buyers", this) {
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
                    for (SellLog i : buyersLogs) {
                        Customer customer = (Customer) GetDataFromDatabase.getAccount(i.getBuyer());
                        System.out.printf("Log ID : %d\nCustomer : %s\nDate : %s\nTotal log price : %.2f\n", i.getLogId(), customer.getUsername(), i.getLogDate(), i.getValue());
                    }

                    System.out.println("Do you want to sort? (yes/no each time you (want/don't want) to sort)");
                    while (Main.scanInput("String").trim().equalsIgnoreCase("yes")) {
                        SortHandler.sortSellLogs();
                        SortController.sortSellLogs(buyersLogs);
                        for (SellLog i : buyersLogs) {
                            Customer customer = (Customer) GetDataFromDatabase.getAccount(i.getBuyer());
                            System.out.printf("Log ID : %d\nCustomer : %s\nDate : %s\nTotal log price : %.2f", i.getLogId(), customer.getUsername(), i.getLogDate(), i.getValue());
                        }
                        System.out.println("Sort again? (yes/no)");
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
        return new Menu("Show Product Details", this) {
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
                    ProductPage productPage = new ProductPage(product.getName(), this);
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
        return new Menu("Seller's Products", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                try {
                    ArrayList<Product> sellerProducts = SellerController.showSellerProducts();
                    for (Product i : sellerProducts) {
                        System.out.printf("Product ID : %d\nName : %s\nPrice : %.2f\n", i.getProductId(), i.getName(), i.getPrice());
                    }
                    System.out.println("Do you want to sort? (yes/no each time you (want/don't want) to sort)");
                    while (Main.scanInput("String").trim().equalsIgnoreCase("yes")) {
                        SortHandler.sortProducts();
                        SortController.sortProducts(sellerProducts);
                        for (Product i : sellerProducts) {
                            System.out.printf("Product ID : %d\nName : %s\nPrice : %.2f\n", i.getProductId(), i.getName(), i.getPrice());
                        }
                        System.out.println("Sort again? (yes/no)");
                    }
                    getParentMenu().show();
                    getParentMenu().run();
                } catch (ExceptionsLibrary.NoAccountException e) {
                    System.out.println(e.getMessage());
                }
            }
        };
    }
}
