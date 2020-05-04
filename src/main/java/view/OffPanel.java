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
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }



    public Menu goToProductPage(){
        return new Menu("Product Page",this){

        };
    }
}
