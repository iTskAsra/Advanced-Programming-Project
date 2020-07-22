package Server.ServerController;

import Server.ClientHandler;
import model.Customer;
import model.Seller;

public class WalletController {

    public void customerWalletCharge(){

        Object[] toReceive = (Object[]) ClientHandler.receiveObject();
        Customer customer = (Customer) toReceive[0];
        double amount = (double) toReceive[1];

        customer.getWallet().setBalance(customer.getWallet().getBalance() + amount);

    }

    public void sellerWalletCharge(){

        Object[] toReceive = (Object[]) ClientHandler.receiveObject();
        Seller seller = (Seller) toReceive[0];
        double amount = (double) toReceive[1];

        seller.getWallet().setBalance(seller.getWallet().getBalance() + amount);
    }
}
