package view;

import controller.CustomerController;

import java.util.HashMap;

public class MainMenu extends Menu {

    private Object RegisterAndLoginPanel = null;
    private HashMap<Integer , Menu> MainMenuSubs = new HashMap<>();


    public boolean isLoggedin(){
        if(CustomerController.getCustomer().getRole() == null){
            return false;
        }
        return true;
    }

    public MainMenu(Object registerPanel, Object registerAndLoginPanel) {
        super("Main Menu", null);
        RegisterAndLoginPanel = registerAndLoginPanel;


        if(!isLoggedin()) {
            MainMenuSubs.put(1, (Menu) RegisterAndLoginPanel);
            MainMenuSubs.put(2, AllProductsPanel);
            MainMenuSubs.put(3, Offs);
            MainMenuSubs.put(4, Help);
        }

        if(isLoggedin()) {
            if (CustomerController.getCustomer().getRole().endsWith("mer")) {
                MainMenuSubs.put(1, CustomerPanel);
                MainMenuSubs.put(2, AllProductsPanel);
                MainMenuSubs.put(3, Offs);
                MainMenuSubs.put(4, Help);
                MainMenuSubs.put(5, logout);
            }
            if(CustomerController.getCustomer().getRole().endsWith("min"))){
                MainMenuSubs.put(1, AdminPanel);
                MainMenuSubs.put(2, AllProductsPanel);
                MainMenuSubs.put(3, Offs);
                MainMenuSubs.put(4, Help);
                MainMenuSubs.put(5, logout);
            }
            if(CustomerController.getCustomer().getRole().endsWith("ler")){
                MainMenuSubs.put(1, SellerPanel);
                MainMenuSubs.put(2, AllProductsPanel);
                MainMenuSubs.put(3, Offs);
                MainMenuSubs.put(4, Help);
                MainMenuSubs.put(5, logout);
            }
        }
    }

}
