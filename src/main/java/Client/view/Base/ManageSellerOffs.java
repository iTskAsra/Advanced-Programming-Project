package Client.view.Base;

import controller.ExceptionsLibrary;
import controller.GetDataFromDatabase;
import controller.SellerController;
import controller.SortController;
import model.*;
import view.Base.Main;
import view.Base.Menu;
import view.Base.SortHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageSellerOffs extends view.Base.Menu {
    public ManageSellerOffs(String name, view.Base.Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, view.Base.Menu> submenus = new HashMap<>();
        submenus.put(1, showSellerOffs());
        submenus.put(2, showSellerOffDetails());
        submenus.put(3, addOffRequest());
        submenus.put(4, editOffRequest());
        submenus.put(submenus.size()+1,help());

        this.setSubmenus(submenus);
    }

    protected view.Base.Menu help() {
        return new view.Base.Menu("Help",this) {
            @Override
            public void show() {
                System.out.println("------------------------------");
                System.out.printf("Seller Offs Panel\nHere are all the Offs the seller has you can view each one of them, edit it or add a new off in this menu.\n");
                System.out.println("------------------------------");
            }

            @Override
            public void run() {
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private view.Base.Menu editOffRequest() {
        return new view.Base.Menu("Edit Off Info Request",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter off ID :");
            }

            @Override
            public void run() {
                int offId = Integer.parseInt(view.Base.Main.scanInput("int"));
                System.out.println("Enter fields to edit : (startDate, endDate, offAmount) (separate by comma)");
                String fields = view.Base.Main.scanInput("String");
                String[] splitFields = fields.split("\\s*,\\s*");
                HashMap<String,String> editedData = new HashMap<>();
                for (String i : splitFields){
                    System.out.printf("Enter new %s\n",i.substring(0, 1).toUpperCase() + i.substring(1));
                    String newValue = view.Base.Main.scanInput("String");
                    editedData.put(i,newValue);
                }
                try {
                    SellerController.editOffRequest(offId,editedData);
                    System.out.println("Request sent!");
                } catch (ExceptionsLibrary.NoFeatureWithThisName noFeatureWithThisName) {
                    System.out.println(noFeatureWithThisName.getMessage());
                } catch (ExceptionsLibrary.NoOffException e) {
                    System.out.println(e.getMessage());
                } catch (ExceptionsLibrary.CannotChangeThisFeature cannotChangeThisFeature) {
                    System.out.println(cannotChangeThisFeature.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private view.Base.Menu addOffRequest() {
        return new view.Base.Menu("Add Off Request",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                System.out.println("Enter off start date : (in yyyy-MM-dd HH:mm)");
                String startDate = view.Base.Main.scanInput("String");
                System.out.println("Enter off end date : (in yyyy-MM-dd HH:mm)");
                String endDate = view.Base.Main.scanInput("String");
                System.out.println("Enter off amount:");
                double amount = Double.parseDouble(view.Base.Main.scanInput("double"));
                System.out.println("Enter off product's ID : (separate by comma)");
                String offProducts = view.Base.Main.scanInput("String");
                Off off = new Off(null,ProductOrOffCondition.PENDING_TO_CREATE,startDate,endDate,amount);
                try {
                    SellerController.addOffRequest(off, offProducts);
                    System.out.println("Request sent!");
                } catch (ExceptionsLibrary.NoProductException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private view.Base.Menu showSellerOffDetails() {
        return new view.Base.Menu("Off Details", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter off ID :");
            }

            @Override
            public void run() {
                int offId = Integer.parseInt(view.Base.Main.scanInput("int"));
                try {
                    Off off = SellerController.showOffDetails(offId);
                    System.out.println("-".repeat(50));
                    System.out.printf("Off ID : %d\nStart Date : %s\nEnd Date : %s\nAmount : %.2f\nStatus : %s\nProducts : \n",off.getOffId(),off.getStartDate(),off.getEndDate(),off.getOffAmount(),off.getOffCondition());
                    for (String k : off.getOffProducts()){
                        Product j = GetDataFromDatabase.getProduct(Integer.parseInt(k));
                        System.out.printf("Product ID : %d   Name : %s   Price : %.2f\n",j.getProductId(),j.getName(),j.getPrice());
                    }
                    System.out.println("-".repeat(50));
                    getParentMenu().show();
                    getParentMenu().run();
                } catch (ExceptionsLibrary.NoOffException | ExceptionsLibrary.NoProductException noOffException) {
                    System.out.println(noOffException.getMessage());
                }
            }
        };
    }

    private view.Base.Menu showSellerOffs() {
        return new Menu("Seller's Offs", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                try {
                    ArrayList<Off> sellerOffs = SellerController.showOffs();
                    for (Off i : sellerOffs) {
                        System.out.printf("Off ID : %d   Start Date : %s   End Date : %s   Amount : %.2f   Status : %s\n", i.getOffId(), i.getStartDate(), i.getEndDate(), i.getOffAmount(), i.getOffCondition());
                    }
                    System.out.println("Do you want to sort? (yes/no each time you (want/don't want) to sort)");
                    while (Main.scanInput("String").trim().equalsIgnoreCase("yes")) {
                        SortHandler.sortOffs();
                        SortController.sortOffs(sellerOffs);
                        for (Off i : sellerOffs) {
                            System.out.printf("Off ID : %d   Start Date : %s   End Date : %s   Amount : %.2f   Status : %s\n", i.getOffId(), i.getStartDate(), i.getEndDate(), i.getOffAmount(), i.getOffCondition());
                        }
                        System.out.println("Sort again? (yes/no)");
                    }
                    getParentMenu().show();
                    getParentMenu().run();
                } catch (ExceptionsLibrary.NoAccountException e) {
                    System.out.println(e.getMessage());
                }
            }
        };
    }
}
