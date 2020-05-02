package view;

import java.util.HashMap;

public class Digest extends Menu {
    public Digest(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, addToCart());
        submenus.put(2,selectSeller());
        this.setSubmenus(submenus);
    }

    private Menu selectSeller() {
        return null;
    }

    private Menu addToCart() {
        return null;
    }
}
