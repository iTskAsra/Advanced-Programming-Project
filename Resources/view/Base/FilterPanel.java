package view.Base;

import controller.AllProductsPanelController;
import controller.ExceptionsLibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FilterPanel extends Menu {
    public FilterPanel(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, showAvailableFilters());
        submenus.put(2, filterAnAvailableFilter());
        submenus.put(3, showCurrentFilter());
        submenus.put(4, disableFilter());
        submenus.put(submenus.size() + 1, help());

        this.setSubmenus(submenus);
    }

    protected Menu help() {
        return new Menu("Help", this) {
            @Override
            public void show() {
                System.out.println("------------------------------");
                System.out.printf("Filter Panel\nHere you can see available filters, choose or disable some and check the current applied filters.\n");
                System.out.println("------------------------------");
            }

            @Override
            public void run() {
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu disableFilter() {
        return new Menu("Disable A Filter", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                int count = 1;
                for (String i : AllProductsPanelController.showCurrentFilters()) {
                    String[] splitFilters = i.split("--");
                    if (splitFilters.length == 2) {
                        System.out.printf("%d. %s : %s\n", count, splitFilters[0], splitFilters[1]);
                    } else {
                        System.out.printf("%d. %s : %s - %s\n", count, splitFilters[0], splitFilters[1], splitFilters[2]);
                    }
                    count++;
                }
                System.out.println("Enter filter number :");
                int filterNumber = Integer.parseInt(Main.scanInput("int"));
                while (filterNumber > AllProductsPanelController.getCurrentFilters().size() || filterNumber < 1) {
                    System.out.println("Invalid number, try again:");
                    filterNumber = Integer.parseInt(Main.scanInput("int"));
                }
                AllProductsPanelController.disableFilter(filterNumber);
                System.out.println("Filter disabled!");
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu showCurrentFilter() {
        return new Menu("Current Filters", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                int count = 1;
                if (AllProductsPanelController.showCurrentFilters().size() != 0) {
                    for (String i : AllProductsPanelController.showCurrentFilters()) {
                        String[] splitFilters = i.split("--");
                        if (splitFilters.length == 2) {
                            System.out.printf("%d. %s : %s\n", count, splitFilters[0], splitFilters[1]);
                        } else if (splitFilters.length == 3) {
                            System.out.printf("%d. %s : %s - %s\n", count, splitFilters[0], splitFilters[1], splitFilters[2]);
                        } else {
                            System.out.printf("%d. %s : %s -> %s -> %s\n", count, splitFilters[0], splitFilters[1], splitFilters[2],splitFilters[3]);
                        }
                        count++;
                    }
                } else {
                    System.out.println("No current filter!");
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        }

                ;
    }

    private Menu filterAnAvailableFilter() {
        return new Menu("Filter A Filter", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");

            }

            @Override
            public void run() {
                try {
                    HashMap<Integer, String> filters = new HashMap<>();
                    filters.put(1, "Name (Search)");
                    filters.put(2, "Category");
                    filters.put(3, "Price");
                    filters.put(4, "Brand");
                    filters.put(5, "Seller");
                    filters.put(6, "Availability");
                    filters.put(7, "Feature");
                    filters.put(8, "Back");
                    for (int i : filters.keySet()) {
                        System.out.println(i + ". " + filters.get(i));
                    }
                    System.out.println("Enter the number of filter:");
                    int chosenNumber = Integer.parseInt(Main.scanInput("int"));
                    while (chosenNumber > 8 || chosenNumber < 1) {
                        System.out.println("Invalid number, try again");
                        chosenNumber = Integer.parseInt(Main.scanInput("int"));
                    }
                    switch (chosenNumber) {
                        case 1:
                            System.out.println("Enter name of the product:");
                            String name = Main.scanInput("String");
                            Iterator iterator = AllProductsPanelController.getCurrentFilters().iterator();
                            while (iterator.hasNext()) {
                                String temp = (String) iterator.next();
                                if (temp.startsWith("Search--")) {
                                    iterator.remove();
                                }
                            }
                            AllProductsPanelController.getCurrentFilters().add("Search--" + name);
                            System.out.println("Filter applied!");
                            break;
                        case 2:
                            ArrayList<String> availableCategories = AllProductsPanelController.showAvailableFilters();
                            int count = 1;
                            for (String i : availableCategories) {
                                System.out.println(count + ". " + i);
                                count++;
                            }
                            System.out.println("Enter number of the category:");
                            int chosenNumberCategory = Integer.parseInt(Main.scanInput("int"));
                            AllProductsPanelController.getCurrentFilters().add("Category--" + availableCategories.get(chosenNumberCategory - 1));
                            System.out.println("Filter applied!");
                            break;
                        case 3:
                            System.out.println("Enter min price:");
                            String minPrice = Main.scanInput("double");
                            System.out.println("Enter max price:");
                            String maxPrice = Main.scanInput("double");
                            AllProductsPanelController.getCurrentFilters().add("Price--" + minPrice + "--" + maxPrice);
                            System.out.println("Filter applied!");
                            break;
                        case 4:
                            System.out.println("Enter name of the brand:");
                            String brandName = Main.scanInput("String");
                            AllProductsPanelController.getCurrentFilters().add("Brand--" + brandName);
                            System.out.println("Filter applied!");
                            break;
                        case 5:
                            System.out.println("Enter name of the Seller:");
                            String sellerName = Main.scanInput("String");
                            if (GetDataFromDatabase.getAccount(sellerName) != null) {
                                AllProductsPanelController.getCurrentFilters().add("Seller--" + sellerName);
                                System.out.println("Filter applied!");
                            } else {
                                System.out.println("No seller with this name!");
                            }
                            break;
                        case 6:
                            System.out.println("Only available? (yes/no)");
                            String input = Main.scanInput("String");
                            while (!input.equalsIgnoreCase("yes") && !input.equalsIgnoreCase("no")) {
                                System.out.println("Invalid input, try again: (yes/no)");
                                input = Main.scanInput("String");
                            }
                            if (input.equalsIgnoreCase("yes")) {
                                iterator = AllProductsPanelController.getCurrentFilters().iterator();
                                while (iterator.hasNext()) {
                                    String temp = (String) iterator.next();
                                    if (temp.startsWith("Availability--")) {
                                        iterator.remove();
                                    }
                                }
                                AllProductsPanelController.getCurrentFilters().add("Availability--" + input.toLowerCase());
                            } else {
                                if (AllProductsPanelController.getCurrentFilters().contains("Availability--yes")) {
                                    AllProductsPanelController.getCurrentFilters().remove("Availability--yes");
                                }
                            }
                            System.out.println("Filter applied!");
                            break;
                        case 7:
                            System.out.println("Enter name of the category:");
                            String categoryName = Main.scanInput("String");
                            System.out.println("Enter name of the feature:");
                            String featureName = Main.scanInput("String");
                            System.out.println("Enter value:");
                            String value = Main.scanInput("String");
                            iterator = AllProductsPanelController.getCurrentFilters().iterator();
                            while (iterator.hasNext()) {
                                String temp = (String) iterator.next();
                                if (temp.startsWith("Feature--")) {
                                    iterator.remove();
                                }
                            }
                            AllProductsPanelController.getCurrentFilters().add("Feature--" + categoryName + "--" + featureName + "--" + value);
                            break;
                    }
                    AllProductsPanelController.filterAnAvailableFilter();

                } catch (ExceptionsLibrary.NoFilterWithThisName | ExceptionsLibrary.NoCategoryException | ExceptionsLibrary.NoProductException | ExceptionsLibrary.NoAccountException | ExceptionsLibrary.NoFeatureWithThisName e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu showAvailableFilters() {
        return new Menu("Available Filters", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {

                HashMap<Integer, String> filters = new HashMap<>();
                filters.put(1, "Name (Search)");
                filters.put(2, "Category");
                filters.put(3, "Price");
                filters.put(4, "Brand");
                filters.put(5, "Seller");
                filters.put(6, "Availability");
                filters.put(7, "Feature");
                System.out.println("-".repeat(30));
                for (int i : filters.keySet()) {
                    System.out.println(i + ". " + filters.get(i));
                }
                System.out.println("-".repeat(30));
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }


}
