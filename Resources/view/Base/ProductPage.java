package view.Base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.ExceptionsLibrary;
import controller.ProductPageController;
import model.Feature;
import model.Product;
import model.Rate;

import java.util.HashMap;

public class ProductPage extends Menu {

    public ProductPage(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, new Digest("Digest", this));
        submenus.put(2, attributes());
        submenus.put(3, compareProducts());
        submenus.put(4, new ProductCommentPanel("Comments",this));
        submenus.put(submenus.size()+1,help());

        this.setSubmenus(submenus);
    }

    protected Menu help() {
        return new Menu("Help",this) {
            @Override
            public void show() {
                System.out.println("------------------------------");
                System.out.printf("Product Page\nHere is the Product Page you can see its digest, attributes and comments left for or compare it with other products.\n");
                System.out.println("------------------------------");
            }

            @Override
            public void run() {
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu compareProducts() {
        return new Menu("Compare Products",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter Product Id to compare :");
            }

            @Override
            public void run() {
                int productId = Integer.parseInt(Main.scanInput("int"));
                Product[] products = new Product[2];
                try {
                    products = ProductPageController.compare(productId);
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    Product product1 = products[0];
                    Product product2 = products[1];

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
                    System.out.printf("ProductID : %d  -  %d\n",product1.getProductId(),product2.getProductId());
                    System.out.printf("Name : %s  -  %s\n",product1.getName(),product2.getName());
                    System.out.printf("Rates : %.2f  -  %.2f\n",product1Rate,product2Rate);
                    System.out.printf("Company : %s  -  %s\n",product1.getCompany(),product2.getCompany());
                    for (Feature i : product1.getCategoryFeatures()){
                        System.out.printf("%s : %s  -  %s\n",i.getParameter(),i.getParameterValue(),getFeatureOfProduct2(product2,i));
                    }
                    System.out.printf("Description : %s  -  %s\n",product1.getDescription(),product2.getDescription());
                    System.out.println("-".repeat(80));
                } catch (ExceptionsLibrary.NoProductException | ExceptionsLibrary.CategoriesNotMatch e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private String getFeatureOfProduct2(Product product,Feature i) {
        for (Feature j : product.getCategoryFeatures()){
            if (j.getParameter().equals(i.getParameter())){
                return j.getParameterValue();
            }
        }
        return null;
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
                    System.out.printf("%s : %s\n",i.getParameter(),i.getParameterValue());
                }
                System.out.printf("Description : %s\n",ProductPageController.attributes().getDescription());
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
