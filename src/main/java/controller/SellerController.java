package controller;

import model.Seller;

public class SellerController {
    private  Seller seller;

    public SellerController(Seller seller) {
        this.seller = seller;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
