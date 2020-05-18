package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.AdminController;
import controller.ExceptionsLibrary;
import controller.SortController;
import model.Category;
import model.Feature;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageCategoriesMenu extends Menu {
    public ManageCategoriesMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, showAllCategories());
        submenus.put(2, editCategory());
        submenus.put(3, addCategory());
        submenus.put(4, removeCategory());
        submenus.put(submenus.size()+1,help());

        this.setSubmenus(submenus);
    }

    protected Menu help() {
        return new Menu("Help",this) {
            @Override
            public void show() {
                System.out.println("------------------------------");
                System.out.printf("Manage Categories\nYou can see all the categories right here and edit or remove them or even add a new category.\n");
                System.out.println("------------------------------");
            }

            @Override
            public void run() {
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu removeCategory() {
        return new Menu("Remove Category",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter category name :");
            }

            @Override
            public void run() {
                String categoryName = Main.scanInput("String");
                try {
                    AdminController.deleteCategory(categoryName);
                    System.out.println("Removed Category!");
                } catch (ExceptionsLibrary.NoCategoryException | ExceptionsLibrary.NoProductException e) {
                    System.out.println(e.getMessage());
                }

                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu addCategory() {
        return new Menu("Add Category",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                System.out.println("Enter category name :");
                String name = Main.scanInput("String");
                System.out.println("Enter category feature : (separate by comma)");
                String features =  Main.scanInput("String");
                String[] featuresList = features.split("\\s*,\\s*");
                ArrayList<Feature> categoryFeatures = new ArrayList<>();
                for (String i : featuresList){
                    Feature feature = new Feature(i,null);
                    categoryFeatures.add(feature);
                }
                Category category = new Category(name,categoryFeatures,null);
                Gson gson = new GsonBuilder().serializeNulls().create();
                String data = gson.toJson(category);
                try {
                    AdminController.addCategory(data);
                    System.out.println("Category Added!");
                } catch (ExceptionsLibrary.CategoryExistsWithThisName categoryExistsWithThisName) {
                    System.out.println(categoryExistsWithThisName.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu editCategory() {
        return new Menu("Edit category info", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                System.out.println("Enter Category name :");
                String categoryName = Main.scanInput("String");
                System.out.println("Enter Category fields to edit : (name,features) (separate by comma)");
                String fields = Main.scanInput("String");
                String[] splitFields = fields.split("\\s*,\\s*");
                HashMap<String, String> editedData = new HashMap<>();
                for (String i : splitFields) {
                    System.out.printf("Enter new %s:\n", i.substring(0, 1).toUpperCase() + i.substring(1));
                    if (i.equalsIgnoreCase("features")) {
                        System.out.println("(Separate by comma)");
                    }
                    String newValue = Main.scanInput("String");
                    editedData.put(i, newValue);
                }
                try {
                    AdminController.editCategory(categoryName, editedData);
                    System.out.println("Edited category!");
                } catch (ExceptionsLibrary.CategoryExistsWithThisName categoryExistsWithThisName) {
                    System.out.println(categoryExistsWithThisName.getMessage());
                } catch (ExceptionsLibrary.NoCategoryException e) {
                    System.out.println(e.getMessage());
                } catch (ExceptionsLibrary.NoFeatureWithThisName noFeatureWithThisName) {
                    System.out.println(noFeatureWithThisName.getMessage());
                } catch (ExceptionsLibrary.NoAccountException e) {
                    System.out.println(e.getMessage());
                } catch (ExceptionsLibrary.NoProductException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu showAllCategories() {
        return new Menu("Show all categories",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                ArrayList<Category> allCategories = null;
                try {
                    allCategories = AdminController.showCategories();
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
