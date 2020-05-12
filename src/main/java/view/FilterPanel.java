package view;

import controller.AllProductsPanelController;
import controller.ExceptionsLibrary;

import java.util.ArrayList;
import java.util.HashMap;

public class FilterPanel extends Menu {
    public FilterPanel(String name, Menu parentMenu) {
        super(name,parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, showAvailableFilters());
        submenus.put(2, filterAnAvailableFilter());
        submenus.put(3, showCurrentFilter());
        submenus.put(4, disableFilter());

        this.setSubmenus(submenus);
    }

    private Menu disableFilter() {
        return new Menu("Disable A Filter",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter filter name :");
            }

            @Override
            public void run() {
                String filterName = Main.scanInput("String");
                try {
                    AllProductsPanelController.disableFilter(filterName);
                } catch (ExceptionsLibrary.NoFilterWithThisName noFilterWithThisName) {
                    noFilterWithThisName.printStackTrace();
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu showCurrentFilter() {
        return new Menu("Current Filters",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                ArrayList<String> currentFilters = AllProductsPanelController.showCurrentFilters();
                if (currentFilters.size() == 0){
                    System.out.println("No filter selected!");
                }
                else {
                    for (String i : currentFilters) {
                        System.out.println(i.substring(0, 1).toUpperCase() + i.substring(1));
                    }
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu filterAnAvailableFilter() {
        return new Menu("Filter Results",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter filter name :");
            }

            @Override
            public void run() {
                String filterName = Main.scanInput("String");
                try {
                    AllProductsPanelController.filterAnAvailableFilter(filterName);
                } catch (ExceptionsLibrary.NoFilterWithThisName noFilterWithThisName) {
                    System.out.println(noFilterWithThisName.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu showAvailableFilters() {
        return new Menu("Available Filters",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                ArrayList<String> availableFilters = null;
                try {
                    availableFilters = AllProductsPanelController.showAvailableFilters();
                    for (String i : availableFilters){
                        System.out.println(i.substring(0,1).toUpperCase()+i.substring(1));
                    }
                } catch (ExceptionsLibrary.NoFilterWithThisName | ExceptionsLibrary.NoCategoryException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }


}
