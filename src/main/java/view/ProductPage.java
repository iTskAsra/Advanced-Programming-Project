package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.ExceptionsLibrary;
import controller.ProductPageController;
import model.Feature;
import model.Product;
import model.Rate;

import java.lang.reflect.Field;
import java.util.HashMap;

public class ProductPage extends Menu {

    public ProductPage(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, new Digest("Digest", this));
        submenus.put(2, attributes());
        submenus.put(3, compareProducts());
        submenus.put(4, new ProductCommentPanel("Comments",this));

        this.setSubmenus(submenus);
    }

    private Menu compareProducts() {
        return new Menu("Compare Products",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter ProductId to compare :");
            }

            @Override
            public void run() {
                int productId = Integer.parseInt(Menu.scanner.nextLine());
                String[] products = new String[0];
                try {
                    products = ProductPageController.compare(productId);
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    Product product1 = gson.fromJson(products[0],Product.class);
                    Product product2 = gson.fromJson(products[1],Product.class);

                    Double product1Rate = 0.00;
                    for (Rate i : product1.getRates()){
                        product1Rate+=i.getRateScore();
                    }

                    product1Rate = product1Rate /product1.getRates().size();

                    Double product2Rate = 0.00;
                    for (Rate i : product2.getRates()){
                        product2Rate+=i.getRateScore();
                    }

                    product2Rate = product2Rate /product2.getRates().size();

                    System.out.println("-".repeat(80));
                    System.out.printf("ProductID : %-6d   %6d\n",product1.getProductId(),product2.getProductId());
                    System.out.printf("Name : %-20s%s%20s\n",product1.getName()," ".repeat(10),product2.getName());
                    System.out.printf("Rates : %.2f%s%.2f\n",product1Rate," ".repeat(5),product2Rate);
                    System.out.printf("Company : %-20s%s%20s\n",product1.getCompany()," ".repeat(10),product2.getCompany());
                    for (Feature i : product1.getCategory().getFeatures()){
                        System.out.printf("%s : %s%s%s\n",i.getParameter(),product1.getCategoryFeatures().get(product1.getCategoryFeatures().indexOf(i)).getParameterValue()," ".repeat(5),product2.getCategoryFeatures().get(product2.getCategoryFeatures().indexOf(i)).getParameterValue());
                    }
                    System.out.printf("Description : %-20s%s%20s\n",product1.getDescription()," ".repeat(10),product2.getDescription());
                    System.out.println("-".repeat(80));
                } catch (ExceptionsLibrary.NoProductException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu attributes() {
        return new Menu("Attributes",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                Product product = ProductPageController.attributes();
                System.out.println("-".repeat(50));
                System.out.printf("ProductID : %d\n",ProductPageController.attributes().getProductId());
                System.out.printf("Name : %s\n",ProductPageController.attributes().getName());
                System.out.printf("Condition : %s\n",ProductPageController.attributes().getProductCondition());
                System.out.printf("Availability : %d\n",ProductPageController.attributes().getAvailability());
                System.out.printf("Price : %.2f\n",ProductPageController.attributes().getPrice());
                System.out.printf("Category : %s\n",ProductPageController.attributes().getCategory().getName());
                for (Feature i : ProductPageController.attributes().getCategoryFeatures()){
                    System.out.printf("%s : %s",i.getParameter(),i.getParameterValue());
                }
                System.out.printf("Description : %s\n",ProductPageController.attributes().getDescription());
                System.out.printf("ProductID : %d\n",ProductPageController.attributes().getProductId());
                Double productRate = 0.00;
                for (Rate i : ProductPageController.attributes().getRates()){
                    productRate+=i.getRateScore();
                }
                productRate = productRate /ProductPageController.attributes().getRates().size();
                System.out.printf("Rate : %.2f\n",productRate);
                System.out.println("-".repeat(50));
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }
}
