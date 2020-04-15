package model;

import java.util.ArrayList;

public class Comment {
    public static ArrayList<Comment> allComments;
    private Account commenterAccount;
    private Product commentProduct;
    private String commentText;
    private RequestOrCommentCondition commentCondition;
    private boolean isBoughtByCommenter;

    public Comment(Account commenterAccount, Product commentProduct, String commentText, RequestOrCommentCondition commentCondition, boolean isBoughtByCommenter) {
        this.commenterAccount = commenterAccount;
        this.commentProduct = commentProduct;
        this.commentText = commentText;
        this.commentCondition = commentCondition;
        this.isBoughtByCommenter = isBoughtByCommenter;
    }

    public Account getCommenterAccount() {
        return commenterAccount;
    }

    public void setCommenterAccount(Account commenterAccount) {
        this.commenterAccount = commenterAccount;
    }

    public Product getCommentProduct() {
        return commentProduct;
    }

    public void setCommentProduct(Product commentProduct) {
        this.commentProduct = commentProduct;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public RequestOrCommentCondition getCommentCondition() {
        return commentCondition;
    }

    public void setCommentCondition(RequestOrCommentCondition commentCondition) {
        this.commentCondition = commentCondition;
    }

    public boolean isBoughtByCommenter() {
        return isBoughtByCommenter;
    }

    public void setBoughtByCommenter(boolean boughtByCommenter) {
        isBoughtByCommenter = boughtByCommenter;
    }
}
