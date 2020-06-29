package Client.view.Base;

import controller.AllProductsPanelController;
import controller.ExceptionsLibrary;
import view.Base.Main;
import view.Base.Menu;

import java.util.ArrayList;
import java.util.HashMap;

public class SortPanel extends view.Base.Menu {
    public SortPanel(String name, view.Base.Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, view.Base.Menu> submenus = new HashMap<>();
        submenus.put(1, showAvailableSorts());
        submenus.put(2, selectSort());
        submenus.put(3, showCurrentSort());
        submenus.put(4, disableSort());
        submenus.put(submenus.size()+1,help());

        this.setSubmenus(submenus);
    }

    protected view.Base.Menu help() {
        return new view.Base.Menu("Help",this) {
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

    private view.Base.Menu disableSort() {
        return new view.Base.Menu("Disable Sort",this) {
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

    private view.Base.Menu showCurrentSort() {
        return new view.Base.Menu("Current Sort",this) {
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

    private view.Base.Menu selectSort() {
        return new view.Base.Menu("Select Sort",this) {
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

    private view.Base.Menu showAvailableSorts() {
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
