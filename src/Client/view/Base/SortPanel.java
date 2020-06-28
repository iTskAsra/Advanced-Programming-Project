package view.Base;

import controller.AllProductsPanelController;
import controller.ExceptionsLibrary;

import java.util.ArrayList;
import java.util.HashMap;

public class SortPanel extends Menu {
    public SortPanel(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, showAvailableSorts());
        submenus.put(2, selectSort());
        submenus.put(3, showCurrentSort());
        submenus.put(4, disableSort());
        submenus.put(submenus.size()+1,help());

        this.setSubmenus(submenus);
    }

    protected Menu help() {
        return new Menu("Help",this) {
            @Override
            public void show() {
                System.out.println("------------------------------");
                System.out.printf("Sort Panel\nHere you can see available sorts, choose or disable one and check the current applied sort.");
                System.out.println("------------------------------");
            }

            @Override
            public void run() {
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu disableSort() {
        return new Menu("Disable Sort",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                AllProductsPanelController.disableSort();
                System.out.println("Sort set to default");
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu showCurrentSort() {
        return new Menu("Current Sort",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                ArrayList<String> currentSort = AllProductsPanelController.currentSort();
                if (currentSort.size() == 0){
                    System.out.println("Sort is set to default!");
                }
                else {
                    for (String i : currentSort){
                        System.out.println(i.substring(0,1).toUpperCase()+i.substring(1));
                    }
                }

                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu selectSort() {
        return new Menu("Select Sort",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter sort name :");
            }

            @Override
            public void run() {
                String sortName = Main.scanInput("String");
                try {
                    AllProductsPanelController.sortAnAvailableSort(sortName);
                    System.out.println("Sort selected!");
                } catch (ExceptionsLibrary.NoSortWithThisName noSortWithThisName) {
                    System.out.println(noSortWithThisName.getMessage());
                }

                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu showAvailableSorts() {
        return new Menu("Available Sorts",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                ArrayList<String> availableSorts = AllProductsPanelController.showAvailableSorts();
                for (String i : availableSorts){
                    System.out.println(i.substring(0,1).toUpperCase()+i.substring(1));
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }
}
