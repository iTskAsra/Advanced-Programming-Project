package view;

import controller.ProductPageController;
import model.Comment;

import java.util.HashMap;

public class ProductCommentPanel extends Menu {
    public ProductCommentPanel(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, showAllComments());
        submenus.put(2, addComment());

        this.setSubmenus(submenus);
    }

    public Menu addComment() {
        return new Menu("Add Comment",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                System.out.println("Enter title :");
                String title =Menu.scanner.nextLine();
                System.out.println("Enter text :");
                String text =Menu.scanner.nextLine();
                ProductPageController.addComment(title,text);
            }
        };
    }

    public Menu showAllComments() {
        return new Menu("All Product Comments",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                for (Comment i : ProductPageController.getProduct().getProductComments()){
                    System.out.println("-".repeat(50));
                    System.out.printf("Title : %s\n",i.getCommentTitle());
                    System.out.printf("Sender : %s    Bought : %s\n",i.getCommenterAccount().getUsername(),i.isBoughtByCommenter() ? "Yes":"No");
                    System.out.printf("Comment : %s\n",i.getCommentText());
                    System.out.println("-".repeat(50));
                }
            }

            @Override
            public void run() {
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }
}
