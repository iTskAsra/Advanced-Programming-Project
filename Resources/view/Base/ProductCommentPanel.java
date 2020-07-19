package view.Base;

import controller.ExceptionsLibrary;
import controller.ProductPageController;
import model.Comment;
import model.Rate;

import java.util.HashMap;

public class ProductCommentPanel extends Menu {
    public ProductCommentPanel(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, showAllComments());
        submenus.put(2,showRate());
        submenus.put(3, addComment());
        submenus.put(submenus.size()+1,help());

        this.setSubmenus(submenus);
    }

    private Menu showRate() {
        return new Menu("Rate",this) {
            @Override
            public void show() {
                Double productRate = 0.00;
                for (Rate i : ProductPageController.attributes().getRates()){
                    productRate+=i.getRateScore();
                }
                productRate = productRate /ProductPageController.attributes().getRates().size();
                System.out.printf("%s : %.2f\n",this.getName(),productRate);
            }

            @Override
            public void run() {
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    protected Menu help() {
        return new Menu("Help",this) {
            @Override
            public void show() {
                System.out.println("------------------------------");
                System.out.printf("Comment Panel\nHere you can Leave a comment separated in title and content section.\n");
                System.out.println("------------------------------");
            }

            @Override
            public void run() {
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }


    private Menu addComment() {
        return new Menu("Add Comment",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                System.out.println("Enter title :");
                String title = Main.scanInput("String");
                System.out.println("Enter text :");
                String text = Main.scanInput("String");
                try {
                    ProductPageController.addComment(title,text);
                    System.out.println("Added comment!");
                } catch (ExceptionsLibrary.NotLoggedInException | ExceptionsLibrary.NoAccountException | ExceptionsLibrary.NoProductException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu showAllComments() {
        return new Menu("All Product Comments",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                for (Comment i : ProductPageController.getProduct().getProductComments()){
                    System.out.println("-".repeat(50));
                    System.out.printf("Title : %s\n",i.getCommentTitle());
                    System.out.printf("Sender : %s    Bought : %s\n",i.getCommenterAccount(),i.isBoughtByCommenter() ? "Yes":"No");
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
