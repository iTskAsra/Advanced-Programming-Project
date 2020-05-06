package view;

import controller.ProductPageController;
import model.Off;
import controller.ProductPageController;
import java.util.ArrayList;
import controller.OffPageController;


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
                int productID = scanner.nextInt();
                //TODO check product availability
                String productGson = OffPageController.goToProductPage(productID);
                Gson gson = new Gson();
                Product theProduct = gson.fromJson(productGson, Product.class);

            }
        };
    }
}
