package view;

import java.util.HashMap;

public class ProductCommentPanel extends Menu {
    public ProductCommentPanel(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, showAllComments());
        submenus.put(2, addComment());

        this.setSubmenus(submenus);
    }

    private Menu addComment() {
        return null;
    }

    private Menu showAllComments() {
        return null;
    }
}
