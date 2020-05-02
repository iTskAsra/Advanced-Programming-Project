package view;

import controller.AllProductsPanelController;

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
                String filterName = Menu.scanner.nextLine();
                AllProductsPanelController.disableFilter(filterName);
                //TODO Exception
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
                for (String i : currentFilters){
                    System.out.println(i.substring(0,1).toUpperCase()+i.substring(1));
                }
                //TODO size=0 -> error
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
                String filterName = Menu.scanner.nextLine();
                AllProductsPanelController.filterAnAvailableFilter(filterName);
                //TODO Exception
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
                ArrayList<String> availableFilters = AllProductsPanelController.showAvailableFilters();
                for (String i : availableFilters){
                    System.out.println(i.substring(0,1).toUpperCase()+i.substring(1));
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }


}
