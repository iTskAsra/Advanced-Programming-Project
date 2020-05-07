package view;

import controller.ProductPageController;
import model.Off;
import controller.ProductPageController;
import java.util.ArrayList;
import controller.OffPageController;
import com.google.gson.*;
import model.Product;
import java.lang.String;
import view.ProductPage;


public class OffPanel extends Menu {
    public OffPanel(Menu parentMenu) {
        super("Off Menu",parentMenu);
    }

    public Menu listOffs(){
        return new Menu("List of Offs",this){
            @Override
            public void show(){
                System.out.println("a List of The Offerings");
            }
            @Override
            public void run(){
                ArrayList<Off> offsList = controller.OffPageController.listOffs();
                for (int i=0;i<offsList.size();i++)
                    System.out.printf("%d. %s\n",i,offsList.get(i));
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }



    public Menu goToProductPage(){
        return new Menu("Product Page",this){
            @Override
            public void show(){
                System.out.println("Please Enter the Product's ID:");
            }
            @Override
            public void run(){
                System.out.println("You Are In The Product Page Now, How Do You Want To Proceed?");
                int productID = scanner.nextInt();
                //TODO check product availability
                String productGson = OffPageController.goToProductPage(productID);
                Gson gson = new Gson();
                Product theProduct = gson.fromJson(productGson, model.Product.class);
                //TODO check the entered command
                String buffer = scanner.nextLine();
                //Digest digest = new Digest("Digest",this);
                //ProductPageController productPageController = new ProductPageController(theProduct);
                ProductPage productPage = new ProductPage("Product's Page",this.parentMenu);
                if (buffer.equals("add to cart")){
                    productPage.submenus.get(1).run(1);
                }
                else if(buffer.equals("digest")){
                    productPage.submenus.get(1).show();
                }
                else if (buffer.startsWith("select seller")){
                    //String[] tempArray = buffer.split("\\s");
                    //String sellerName = tempArray[2];
                    //digest.run(0);
                    productPage.submenus.get(1).run(0);
                }
                else if(buffer.equals("Comments")){
                    productPage.submenus.get(4).showAllComments();
                }
                else if(buffer.equals("attributes")){
                    productPage.submenus.get(2).show();
                    productPage.submenus.get(2).run();
                }
                else if(buffer.startsWith(("compare"))){
                    //TODO check if the statement after compare is a number(handle number format exception)
                    //String[] tempArray = buffer.split("\\s");
                    //String secondaryProductIdInString = tempArray[1];
                    //int secondaryProductId = Integer.parseInt(secondaryProductIdInString);
                    productPage.submenus.get(3).show();
                    productPage.submenus.get(3).run();
                }
                else if (buffer.equals"add comment"){
                    productPage.submenus.get(4).addComment().show();
                    productPage.submenus.get(4).addComment().run();
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }
}
