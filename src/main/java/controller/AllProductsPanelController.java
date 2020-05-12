package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Category;
import model.Feature;
import model.Product;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AllProductsPanelController {
    private static ArrayList<String> currentFilters;
    private static ArrayList<String> availableFilters;
    private static ArrayList<String> availableSorts;
    private static ArrayList<String> currentSort;

    static {
        currentFilters = new ArrayList<>();
        availableFilters = new ArrayList<>();
        availableSorts = new ArrayList<>();
        currentSort = new ArrayList<>();
        currentSort.add("name");
        availableSorts.add("name");
        availableSorts.add("price");
        availableSorts.add("availability");
        availableSorts.add("date");
        availableSorts.add("company");
        availableSorts.add("best selling");
    }

    public static ArrayList<String> getCurrentSort() {
        return currentSort;
    }

    public static void setCurrentSort(ArrayList<String> currentSort) {
        AllProductsPanelController.currentSort = currentSort;
    }

    public static ArrayList<String> getAvailableSorts() {
        return availableSorts;
    }

    public static void setAvailableSorts(ArrayList<String> availableSorts) {
        AllProductsPanelController.availableSorts = availableSorts;
    }

    public static ArrayList<String> getAvailableFilters() {
        return availableFilters;
    }

    public static void setAvailableFilters(ArrayList<String> availableFilters) {
        AllProductsPanelController.availableFilters = availableFilters;
    }

    public static ArrayList<String> getCurrentFilters() {
        return currentFilters;
    }

    public static void setCurrentFilters(ArrayList<String> currentFilters) {
        AllProductsPanelController.currentFilters = currentFilters;
    }

    public static ArrayList<String> viewCategories() {
        String path = "Resources/Category";
        File folder = new File(path);
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File file1) {
                if (file1.getName().endsWith(".json")) {
                    return true;
                }
                return false;
            }
        };
        ArrayList<String> categoriesName = new ArrayList();
        for (File i : folder.listFiles(fileFilter)) {
            String fileName = i.getName();
            categoriesName.add(fileName.replace(".json", ""));
        }
        return categoriesName;
    }

    public static ArrayList<String> showAvailableFilters() throws ExceptionsLibrary.NoFilterWithThisName, ExceptionsLibrary.NoCategoryException {
        ArrayList<String> allAvailableFilters = new ArrayList();
        String path = "Resources/Category";
        File folder = new File(path);
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File file1) {
                if (file1.getName().endsWith(".json")) {
                    return true;
                }
                return false;
            }
        };
        for (File i : folder.listFiles(fileFilter)) {
            String fileName = i.getName();
            String categoryName = fileName.replace(".json", "");
            allAvailableFilters.add(categoryName);
            Category category = GetDataFromDatabase.getCategory(categoryName);
            for (Feature j : category.getFeatures()) {
                allAvailableFilters.add(j.getParameter());
            }
        }
        setAvailableFilters(allAvailableFilters);
        return allAvailableFilters;
    }

    public static void filterAnAvailableFilter(String filter) throws ExceptionsLibrary.NoFilterWithThisName {
        for (String i : getAvailableFilters()) {
            if (i.equals(filter)) {
                currentFilters.add(i);
            }
        }
        throw new ExceptionsLibrary.NoFilterWithThisName();
    }

    public static ArrayList<String> showCurrentFilters() {
        return getCurrentFilters();
    }

    public static void disableFilter(String filter) throws ExceptionsLibrary.NoFilterWithThisName {
        if (currentFilters.contains(filter)) {
            currentFilters.remove(filter);
        } else {
            throw new ExceptionsLibrary.NoFilterWithThisName();
        }
    }

    public static ArrayList<String> showAvailableSorts() {
        return getAvailableSorts();
    }

    public static void sortAnAvailableSort(String sort) throws ExceptionsLibrary.NoSortWithThisName {
        for (String i : getAvailableSorts()) {
            if (i.equalsIgnoreCase(sort)) {
                currentSort.clear();
                currentSort.add(i);
                return;
            }
        }
        throw new ExceptionsLibrary.NoSortWithThisName();
    }

    public static ArrayList<String> currentSort() {
        return getCurrentSort();
    }

    public static void disableSort() {
        getCurrentSort().clear();
        getCurrentSort().add("name");
    }

    public static ArrayList<Product> showProducts() throws ExceptionsLibrary.NoProductException {
        ArrayList<Product> products = new ArrayList<>();
        String path = "Resources/Products";
        File productsFolder = new File(path);
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File file1) {
                if (file1.getName().endsWith(".json")) {
                    return true;
                }
                return false;
            }
        };
        for (File i : productsFolder.listFiles(fileFilter)) {
            String fileName = i.getName();
            String productId = fileName.replace(".json", "");
            Product product = GetDataFromDatabase.getProduct(Integer.parseInt(productId));
            products.add(product);
        }
        Iterator<Product> iterator =products.iterator();
        while (iterator.hasNext()){
            Product tempProduct = iterator.next();
            for (String j : getCurrentFilters()){
                if (!(tempProduct.getCategory().getName().equals(j)||isFeature(tempProduct,j))){
                    iterator.remove();
                }
            }
        }
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                try {
                    Field field = Product.class.getDeclaredField(getCurrentSort().get(0));
                    field.setAccessible(true);
                    if (getCurrentSort().get(0).equals("name")||getCurrentSort().get(0).equals("company")){
                        String o1Name = (String) field.get(o1);
                        String o2Name = (String) field.get(o2);
                        return o1Name.compareTo(o2Name);
                    }
                    else if (getCurrentSort().get(0).equals("date")){
                        Date o1Date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse((String)field.get(o1));
                        Date o2Date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse((String)field.get(o2));
                        return o1Date.compareTo(o2Date);
                    }
                    else if (getCurrentSort().get(0).equals("price")){
                        Double o1Price = (Double) field.get(o1);
                        Double o2Price = (Double) field.get(o2);
                        return o1Price.compareTo(o2Price);
                    }
                    else  if (getCurrentSort().get(0).equals("availability")){
                        Integer o1Availability = (Integer) field.get(o1);
                        Integer o2Availability = (Integer) field.get(o2);
                        return o1Availability.compareTo(o2Availability);
                    }

                } catch (NoSuchFieldException | IllegalAccessException | ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        return products;
    }

    public static Product goToProductPage(int productId) throws ExceptionsLibrary.NoProductException {
        Product product = null;
        try {
            product = GetDataFromDatabase.getProduct(productId);
        } catch (ExceptionsLibrary.NoProductException e) {
            throw new ExceptionsLibrary.NoProductException();
        }
        return product;
    }

    private static boolean isFeature(Product i, String j) {
        for (Feature k : i.getCategoryFeatures()){
            if (k.getParameter().equals(j)){
                return true;
            }
        }
        return false;
    }


}
