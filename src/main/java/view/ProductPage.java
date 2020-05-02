package view;

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
        return null;
    }

    private Menu attributes() {
        return null;
    }
}
