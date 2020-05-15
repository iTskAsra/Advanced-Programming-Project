package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.AdminController;
import controller.ExceptionsLibrary;
import controller.GetDataFromDatabase;
import controller.SortController;
import model.Sale;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageSalesMenu extends Menu {

    public ManageSalesMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, showAllSales());
        submenus.put(2, showSaleDetails());
        submenus.put(3, editSaleInfo());
        submenus.put(4, removeSaleCode());
        submenus.put(submenus.size()+1,help());

        this.setSubmenus(submenus);
    }

    protected Menu help() {
        return new Menu("Help",this) {
            @Override
            public void show() {
                System.out.println("------------------------------");
                System.out.printf("Sales Panel\nHere we have all the existing discount codes and you can see their details, edit or remove them.\n");
                System.out.println("------------------------------");
            }

            @Override
            public void run() {
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu removeSaleCode() {
        return new Menu("Delete Sale", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter sale code :");
            }

            @Override
            public void run() {
                String saleCode = Main.scanInput("String");
                try {
                    AdminController.removeSaleCode(saleCode);
                    System.out.println("Removed!");
                } catch (ExceptionsLibrary.NoSaleException noSaleException) {
                    noSaleException.printStackTrace();
                }

                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu editSaleInfo() {
        return new Menu("Edit Sale Info",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter sale code :");
            }
            //TODO unable to change productID , offID, requestID, saleID
            @Override
            public void run() {
                String saleCode = Main.scanInput("String");
                try {
                    Sale sale = GetDataFromDatabase.getSale(saleCode);
                    System.out.println("Enter fields to edit : (separate by comma) (startDate, endDate, salePercent, saleMaxAmount, validTimes) :");
                    String fields = Main.scanInput("String");
                    String[] splitFields = fields.split("\\s*,\\s*");
                    HashMap<String, String> editedData = new HashMap<>();
                    for (String i : splitFields) {
                        System.out.printf("Enter new %s\n", i.substring(0, 1).toUpperCase() + i.substring(1));
                        String newValue = Main.scanInput("String");
                        editedData.put(i, newValue);
                    }
                    try {
                        AdminController.editSaleInfo(saleCode, editedData);
                        System.out.println("Edited sale details!");
                    } catch (ExceptionsLibrary.NoSaleException | ExceptionsLibrary.NoFeatureWithThisName e) {
                        System.out.println(e.getMessage());
                    }
                }
                catch (ExceptionsLibrary.NoSaleException e){
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu showSaleDetails() {
        return new Menu("Sale Details",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter sale code :");
            }

            @Override
            public void run() {
                String saleCode = Main.scanInput("String");
                String data = null;
                try {
                    data = AdminController.viewSaleCodeDetails(saleCode);
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    Sale sale = gson.fromJson(data, Sale.class);
                    System.out.printf("%s\n%-20s%10s%30s\n%-20s%s%30s\n%-20s%s%30s\n%-20s%s%30s\n%-20s%s%30s\n%-20s%s%27s\n%s\n","-".repeat(60), "Sale code :", " ".repeat(10), sale.getSaleCode(), "Start date :", " ".repeat(10), sale.getStartDate(), "End date :", " ".repeat(10), sale.getEndDate(), "Percent :", " ".repeat(10), String.valueOf(sale.getSalePercent()), "Max amount :", " ".repeat(10), String.valueOf(sale.getSaleMaxAmount()), "Number of valid times :", " ".repeat(10), String.valueOf(sale.getValidTimes()),"-".repeat(60));
                } catch (ExceptionsLibrary.NoSaleException noSaleException) {
                    noSaleException.printStackTrace();
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu showAllSales() {
        return new Menu("Show All Sales", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                ArrayList<Sale> allSales = null;
                try {
                    allSales = AdminController.showSales();
                    for (Sale i : allSales) {
                        System.out.printf("%-10s%s%20s%s%20s\n", i.getSaleCode(), " ".repeat(5), i.getStartDate() , " ".repeat(5) , i.getEndDate());
                    }

                    System.out.println("Do you want to sort? (yes/no each time you (want/don't want) to sort)");
                    while (Main.scanInput("String").trim().equalsIgnoreCase("yes")) {
                        SortHandler.sortSale();
                        SortController.sortSales(allSales);
                        for (Sale i : allSales) {
                            System.out.printf("%-10s%s%20s%s%20s\n", i.getSaleCode(), " ".repeat(5), i.getStartDate() , " ".repeat(5) , i.getEndDate());
                        }
                        System.out.println("Sort again? (yes/no)");
                    }
                } catch (ExceptionsLibrary.NoSaleException noSaleException) {
                    System.out.println(noSaleException.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

}
