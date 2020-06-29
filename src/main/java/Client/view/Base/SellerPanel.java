package Client.view.Base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.ExceptionsLibrary;
import controller.GetDataFromDatabase;
import controller.SellerController;
import controller.SortController;
import model.*;
import view.Base.Main;
import view.Base.ManageSellerOffs;
import view.Base.ManageSellerProducts;
import view.Base.Menu;
import view.Base.SortHandler;

import java.util.ArrayList;
import java.util.HashMap;


public class SellerPanel extends view.Base.Menu {
    public SellerPanel(view.Base.Menu parentMenu) {
        super("User Panel", parentMenu);
        HashMap<Integer, view.Base.Menu> submenus = new HashMap<>();
        submenus.put(1,showSellerInfo());
        submenus.put(2,editSellerInfo());
        submenus.put(3,showSellerCompany());
        submenus.put(4,showSellerLogs());
        submenus.put(5,new ManageSellerProducts("Manage Products",this));
        submenus.put(6,showCategories());
        submenus.put(7,new ManageSellerOffs("Manage Offs", this));
        submenus.put(8,showBalance());
        submenus.put(submenus.size()+1,help());

        this.setSubmenus(submenus);
    }

    protected view.Base.Menu help() {
        return new view.Base.Menu("Help",this) {
            @Override
            public void show() {
                System.out.println("------------------------------");
                System.out.printf("Seller Panel\nHere is the panel for Sellers.\nYou can see the personal or company information, view sales history, manage, add or remove products and also you can see categories, offs or balances.\n");
                System.out.println("------------------------------");
            }

            @Override
            public void run() {
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private view.Base.Menu editSellerInfo() {
        return new view.Base.Menu("Edit Info",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter fields you want to change (password, firstName, lastName, email, phoneNumber): (separate by comma)");
            }

            @Override
            public void run() {
                String fields = view.Base.Main.scanInput("String");
                String[] splitFields = fields.split("\\s*,\\s*");
                HashMap<String,String> editedData = new HashMap<>();
                for (String i : splitFields){
                    System.out.printf("Enter new %s:\n",i.substring(0, 1).toUpperCase() + i.substring(1));
                    String newValue = view.Base.Main.scanInput("String");
                    editedData.put(i,newValue);
                }
                try {
                    SellerController.editSellerInfo(editedData);
                    System.out.println("Edited info!");
                } catch (ExceptionsLibrary.NoFeatureWithThisName noFeatureWithThisName) {
                    System.out.println(noFeatureWithThisName.getMessage());
                } catch (ExceptionsLibrary.NoAccountException e) {
                    System.out.println(e.getMessage());
                } catch (ExceptionsLibrary.ChangeUsernameException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private view.Base.Menu showSellerInfo() {
        return new view.Base.Menu("Seller Info",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                String sellerData = null;
                try {
                    sellerData = SellerController.showSellerInfo();
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    Seller seller = gson.fromJson(sellerData, Seller.class);
                    System.out.printf("Username : %s\nFirst Name : %s\nLast name : %s\nE-Mail : %s\nPhone Number : %s\n",seller.getUsername(),seller.getFirstName(),seller.getLastName(),seller.getEmail(),seller.getPhoneNumber());
                } catch (ExceptionsLibrary.NoAccountException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private view.Base.Menu showBalance() {
        return new view.Base.Menu("Balance", this) {
            @Override
            public void show() {
                System.out.printf("Balance : %.2f\n",SellerController.showBalance());
            }

            @Override
            public void run() {
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private view.Base.Menu showSellerCompany() {
        return new view.Base.Menu("Seller Company", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                String company = SellerController.showSellerCompany();
                System.out.printf("Company : %s\n",company);
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private view.Base.Menu showSellerLogs() {
        return new view.Base.Menu("Seller Logs", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                ArrayList<SellLog> sellerLogs = SellerController.showSellerLogs();
                for (SellLog i : sellerLogs){
                    System.out.println("-".repeat(50));
                    try {
                        Customer customer = (Customer) GetDataFromDatabase.getAccount(i.getBuyer());
                        /*System.out.printf("Log ID : %d\nDate : %s\nValue: %.2f\nDiscount Applied: %.2f\nBuyer's Name: %s %s\nDelivery Condition: %s\n", i.getLogId(), i.getLogDate(), i.getValue(), i.getDiscountApplied(), customer.getFirstName() , customer.getLastName(), i.getDeliveryCondition());
                        for (Product j : i.getLogProducts().keySet()){
                            System.out.println("-".repeat(40));
                            System.out.printf("Product name : %s\nProduct ID : %d\nQuantity : %d\nPrice for each : %.2f\nTotal price for this product : %.2f\nMoney received : %.2f\n", j.getName(),j.getProductId(),i.getLogProducts().get(j),j.getPrice(),j.getPrice()*i.getLogProducts().get(j),j.getPrice()*i.getLogProducts().get(j));
                            System.out.println("-".repeat(40));
                        }
                        System.out.println("-".repeat(50));*/
                    } catch (ExceptionsLibrary.NoAccountException e) {
                        System.out.println(e.getMessage());
                    }
                }
                System.out.println("Do you want to sort? (yes/no each time you (want/don't want) to sort)");
                while (view.Base.Main.scanInput("String").trim().equalsIgnoreCase("yes")) {
                    SortHandler.sortSellLogs();
                    SortController.sortSellLogs(sellerLogs);
                    for (SellLog i : sellerLogs){
                        System.out.println("-".repeat(50));
                        try {
                            Customer customer = (Customer) GetDataFromDatabase.getAccount(i.getBuyer());
                            System.out.printf("Log ID : %d\nDate : %s\nValue: %.2f\nDiscount Applied: %.2f\nBuyer's Name: %s %s\nDelivery Condition: %s\n", i.getLogId(), i.getLogDate(), i.getValue(), i.getDiscountApplied(), customer.getFirstName() , customer.getLastName(), i.getDeliveryCondition());
                            /*for (Product j : i.getLogProducts().keySet()){
                                System.out.println("-".repeat(40));
                                System.out.printf("Product name : %s\nProduct ID : %d\nQuantity : %d\nPrice for each : %.2f\nTotal price for this product : %.2f\nMoney received : %.2f\n", j.getName(),j.getProductId(),i.getLogProducts().get(j),j.getPrice(),j.getPrice()*i.getLogProducts().get(j),j.getPriceWithOff()*i.getLogProducts().get(j));
                                System.out.println("-".repeat(40));
                            }*/
                            System.out.println("-".repeat(50));
                        } catch (ExceptionsLibrary.NoAccountException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    System.out.println("Sort again? (yes/no)");
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }





    private view.Base.Menu showCategories() {
        return new Menu("Show all categories",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                ArrayList<Category> allCategories = null;
                try {
                    allCategories = SellerController.showCategories();
                    for (Category i : allCategories){
                        System.out.printf("%s\n","-".repeat(30));
                        System.out.printf("Category name : %s\nFeatures:\n",i.getName());
                        int featureCount = 1;
                        for (Feature j : i.getFeatures()){
                            System.out.printf("%d. %s\n",featureCount,j.getParameter());
                            featureCount++;
                        }
                        System.out.printf("%s\n","-".repeat(30));
                    }

                    System.out.println("Do you want to sort? (yes/no each time you (want/don't want) to sort)");
                    while (Main.scanInput("String").trim().equalsIgnoreCase("yes")) {
                        SortHandler.sortCategories();
                        SortController.sortCategories(allCategories);
                        for (Category i : allCategories){
                            System.out.printf("%s\n","-".repeat(30));
                            System.out.printf("Category name : %s\nFeatures:\n",i.getName());
                            int featureCount = 1;
                            for (Feature j : i.getFeatures()){
                                System.out.printf("%d. %s\n",featureCount,j.getParameter());
                                featureCount++;
                            }
                            System.out.printf("%s\n","-".repeat(30));
                        }
                        System.out.println("Sort again? (yes/no)");
                    }
                } catch (ExceptionsLibrary.NoCategoryException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

}
