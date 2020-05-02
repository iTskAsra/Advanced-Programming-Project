package view;

import controller.CartController;
import controller.GetDataFromDatabase;
import controller.ProductPageController;
import model.Seller;

import java.util.HashMap;

public class Digest extends Menu {
    public Digest(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, addToCart());
        submenus.put(2,selectSeller());
        this.setSubmenus(submenus);
    }

    @Override
    public void show(){
        System.out.println(this.name + " :");
        System.out.println("-".repeat(50));
        System.out.printf("ProductID : %d\n", ProductPageController.getProduct().getProductId());
        System.out.printf("Name : %s\n",ProductPageController.getProduct().getName());
        System.out.printf("Availability : %d\n",ProductPageController.getProduct().getAvailability());
        System.out.printf("Sellers :\n");
        int count = 1;
        for (Seller i : GetDataFromDatabase.findSellersFromProductId(ProductPageController.getProduct().getProductId())){
            System.out.printf("%d . %s\n",count,i.getUsername());
            count++;
        }
        System.out.printf("Price : %.2f\n",ProductPageController.getProduct().getPrice());
        System.out.printf("Category : %s\n",ProductPageController.getProduct().getCategory().getName());
        System.out.println("-".repeat(50));
        for(int i = 1 ; i <= this.submenus.size() ; i++){
            System.out.println(i + ". " + this.submenus.get(i).getName());
        }

        if (this.parentMenu != null)
            System.out.println((submenus.size() + 1) + ". Back");
        else
            System.out.println((submenus.size() + 1) + ". Exit");
    }

    private Menu selectSeller() {
        return new Menu("Select Seller",this) {
            HashMap<Integer,Seller> sellerList = new HashMap<>();
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.printf("Sellers :\n");
                int count = 1;
                for (Seller i : GetDataFromDatabase.findSellersFromProductId(ProductPageController.getProduct().getProductId())){
                    System.out.printf("%d . %s\n",count,i.getUsername());
                    sellerList.put(count,i);
                    count++;
                }
                System.out.println("Select a seller :");

            }

            @Override
            public void run() {
                int number = Integer.parseInt(Menu.scanner.nextLine());
                if (number>sellerList.size()+1||number<0){
                    //TODO error
                }
                else {
                    ProductPageController.selectSeller(sellerList.get(number).getUsername());
                    System.out.println("Seller Selected!");
                    getParentMenu().show();
                    getParentMenu().run();
                }
            }
        };
    }

    private Menu addToCart() {
        return new Menu("Add To Cart",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                ProductPageController.addToCart();
                System.out.println("Added to cart!");
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }
}
