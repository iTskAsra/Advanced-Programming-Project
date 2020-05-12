package view;

import controller.CartController;
import controller.ExceptionsLibrary;
import controller.GetDataFromDatabase;
import controller.ProductPageController;
import model.Product;
import model.Seller;

import java.util.HashMap;

public class CartPanel extends Menu {
    public CartPanel(String name, Menu parentMenu) {
        super("CartPanel", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, showProducts());
        submenus.put(2, viewProduct());
        submenus.put(3, increaseProduct());
        submenus.put(4, decreaseProduct());
        submenus.put(5, showTotalPrice());
        submenus.put(6, purchase());

        this.setSubmenus(submenus);
    }

    private Menu showProducts() {
        return new Menu("Products", this) {

            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                HashMap<Product, Integer> products = CartController.getCartProducts();
                for (Product i : products.keySet()) {
                    System.out.printf("Name : %s   Product ID : %d   Quantity : %d   Price for each : %.2f   Price with off : %.2f   Total price to pay : %.2f\n", i.getName(), i.getProductId(), products.get(i), i.getPrice(), i.getPriceWithOff(), i.getPriceWithOff() * products.get(i));
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu viewProduct() {
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
                    product = CartController.viewCartProductDetails(productId);
                    ProductPageController productPageController = new ProductPageController(product);
                    ProductPage productPage = new ProductPage(product.getName(), this.getParentMenu());
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

    private Menu increaseProduct() {
        return new Menu("Increase Product", this) {

            @Override
            public void show() {
                System.out.println("Enter the Product ID:");
            }

            @Override
            public void run() {
                Product product = null;
                try {
                    Menu menu = new Menu("Select Seller", this) {
                        HashMap<Integer, Seller> sellerList = new HashMap<>();
                        @Override
                        public void show() {
                            try {
                                int productID = Integer.parseInt(Main.scanInput("int"));
                                ProductPageController.setProduct(GetDataFromDatabase.getProduct(productID));
                                System.out.println(this.getName() + ":");
                                System.out.printf("Sellers :\n");
                                int count = 1;
                                for (Seller i : GetDataFromDatabase.findSellersFromProductId(ProductPageController.getProduct().getProductId())) {
                                    System.out.printf("%d . %s\n", count, i.getUsername());
                                    sellerList.put(count, i);
                                    count++;
                                }
                                System.out.println("Select a seller :");
                            } catch (ExceptionsLibrary.NoProductException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void run() {
                            int number = Integer.parseInt(Main.scanInput("int"));
                            if (number > sellerList.size() + 1 || number < 0) {
                                System.out.println("Enter a valid number");
                            } else {
                                try {
                                    ProductPageController.selectSeller(sellerList.get(number).getUsername());
                                } catch (ExceptionsLibrary.NoAccountException e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        }

                    };
                    menu.show();
                    menu.run();
                    product = ProductPageController.getProduct();
                    CartController.increaseProduct(product);
                } catch (ExceptionsLibrary.NoProductException e) {
                    System.out.println(e.getMessage());
                } catch (ExceptionsLibrary.NotEnoughNumberAvailableException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu decreaseProduct() {
        return new Menu("Decrease Product", this) {

            @Override
            public void show() {
                System.out.println("Enter the Product ID:");
            }

            @Override
            public void run() {
                int productID = Integer.parseInt(Main.scanInput("int"));
                try {
                    CartController.decreaseProduct(productID);
                } catch (ExceptionsLibrary.NoProductException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu showTotalPrice() {
        return new Menu("Total Price", this) {

            @Override
            public void show() {
            }

            @Override
            public void run() {
                double totalPrice = CartController.getTotalPriceWithoutSale();
                System.out.println("Total Price: " + totalPrice);
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu purchase() {
        return new Menu("Purchase", this) {
            @Override
            public void show() {
            }

            @Override
            public void run() {
                HashMap<String, String> receiverInfo = new HashMap<>();

                System.out.println("Receiver Info :");
                System.out.println("Enter receiver name :");
                String name = Main.scanInput("String");
                System.out.println("Enter receiver address :");
                String address = Main.scanInput("String");
                System.out.println("Enter receiver phone number :");
                String phoneNumber = Main.scanInput("String");
                receiverInfo.put("name", name);
                receiverInfo.put("address", address);
                receiverInfo.put("phone", phoneNumber);
                CartController.receiverProcess(receiverInfo);
                System.out.println("Receiver Info Set!");
                System.out.println("Enter sale code to apply : (If you don't have or don't want to use it enter \"skip\")");
                String saleCode = Main.scanInput("String");
                if (saleCode.trim().equalsIgnoreCase("skip")) {
                    saleCode = null;
                }
                try {
                    CartController.discountApply(saleCode);
                    System.out.println("Payment :");
                    System.out.println("Enter \"pay\" to proceed payment:");
                    String input = Main.scanInput("String");
                    if (input.trim().equalsIgnoreCase("pay")) {
                        try {
                            CartController.purchase();
                            System.out.println("Cart bought!");
                        } catch (ExceptionsLibrary.CreditNotSufficientException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Payment cancelled!");
                    }
                    getParentMenu().show();
                    getParentMenu().run();
                } catch (ExceptionsLibrary.NoSaleException e) {
                    System.out.println(e.getMessage());
                } catch (ExceptionsLibrary.UsedAllValidTimesException e) {
                    System.out.println(e.getMessage());
                } catch (ExceptionsLibrary.SaleExpiredException e) {
                    System.out.println(e.getMessage());
                } catch (ExceptionsLibrary.SaleNotStartedYetException e) {
                    System.out.println(e.getMessage());
                }


            }
        };
    }


}


