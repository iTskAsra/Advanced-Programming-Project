package view;

import controller.AllProductsPanelController;

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
                //TODO Exception Or Message
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
                ArrayList<String> currentSort = AllProductsPanelController.showAvailableSorts();
                for (String i : currentSort){
                    System.out.println(i.substring(0,1).toUpperCase()+i.substring(1));
                }
                //TODO size=0 -> error
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu selectSort() {
        return new Menu("Sort Results",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter sort name :");
            }

            @Override
            public void run() {
                String sortName = Menu.scanner.nextLine();
                AllProductsPanelController.sortAnAvailableSort(sortName);
                //TODO Exception
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
