package view;

import com.google.gson.Gson;
import controller.CartController;
import controller.CustomerController;
import jdk.jshell.execution.JdiExecutionControl;
import model.Product;

import java.util.HashMap;

public class Cart extends Menu {
    public Cart(String name, Menu parentMenu) {
        super("Cart", parentMenu);
        HashMap<Integer , Menu> submenus = new HashMap<>();

        submenus.put(1 , showProducts());
        submenus.put(2 , viewProduct());
        submenus.put(3 , increaseProduct());
        submenus.put(4 , decreaseProduct());
        submenus.put(5 , showTotalPrice());
        submenus.put(6 , purchace());


    }

    private Menu showProducts() {
    return new Menu("Products" , this){

        @Override
        public void show(){
          System.out.println(this.getName() + ":");
        }

        @Override
        public void run(){
            HashMap<Product , Integer> products = CartController.getCartProducts();
            for(int i = 0 ; i < products.size() ; i++){
                System.out.println( i + products.get(i));
            }
            getParentMenu().show();
            getParentMenu().run();
        }
    };
    }

    private Menu viewProduct(){
        return new Menu("View Product" , this){

            @Override
            public void show(){
                System.out.println("Enter the Product ID:");
            }

            @Override
            public void run(){
                int productID = Integer.parseInt(Menu.scanner.nextLine());
                String productDetails = CartController.viewCartProductDetails(productID);
                System.out.println(productDetails);
                parentMenu.show();
                parentMenu.run();
            }
        };
    }

    private Menu increaseProduct(){
        return new Menu("Increase Product" , this){

            @Override
            public void show(){
                System.out.println("Enter the Product ID:");
            }

            @Override
            public void run(){
                int productID = Integer.parseInt(Menu.scanner.nextLine());
                CartController.increaseProduct(productID);
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu decreaseProduct(){
        return new Menu("Decrease Product" , this){

        @Override
        public void show(){
            System.out.println("Enter the Product ID:");
        }

        @Override
        public void run(){
            int productID = Integer.parseInt(Menu.scanner.nextLine());
            CartController.decreaseProduct(productID);
            getParentMenu().show();
            getParentMenu().run();
            }
         };
    }

    private Menu showTotalPrice(){
        return new Menu("Total Price" , this){

            @Override
            public void show(){
                System.out.print("Total Price: ");
            }

            @Override
            public void run(){
                double totalPrice = CartController.getTotalPrice();
                System.out.println(totalPrice);
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu purchace(){
        return new Menu("Purcahce Cart" , this){

            @Override
            public void show(){

            }

            @Override
            public void run(){

            }
        };
    }


}


