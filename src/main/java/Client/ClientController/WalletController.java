package Client.ClientController;

import Client.Client;
import model.Customer;
import model.Seller;
import model.Wallet;
import LocalExceptions.ExceptionsLibrary;

public class WalletController {
    
    public static Wallet wallet;

    public WalletController(Wallet wallet) {
        this.wallet = wallet;
    }

    public static Wallet getWallet() {
        return wallet;
    }

    public static void setWallet(Wallet wallet) {
        WalletController.wallet = wallet;
    }

    public void customerWalletCharge(Customer customer, double amount){

        String func = "Customer Wallet Charge";
        Client.sendMessage(func);

        Object[] toSend = new Object[2];
        toSend[0] = customer;
        toSend[1] = amount;
        Client.sendObject(toSend);
    }

    public void sellerWalletCharge(Seller seller, double amount){

        String func = "Seller Wallet Charge";
        Client.sendMessage(func);

        Object[] toSend = new Object[2];
        toSend[0] = seller;
        toSend[1] = amount;
        Client.sendObject(toSend);
    }




}
