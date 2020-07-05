package Client.controller;

import controller.ExceptionsLibrary;
import controller.GetDataFromDatabase;
import model.Category;
import model.Feature;
import model.Product;
import model.Seller;

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
    private static ArrayList<Product> result;

    static {
        result = new ArrayList<>();
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
    }

    public static ArrayList<Product> getResult() {
        return result;
    }

    public static void setResult(ArrayList<Product> result) {
        Client.controller.AllProductsPanelController.result = result;
    }

    public static ArrayList<String> getCurrentSort() {
        return currentSort;
    }

    public static void setCurrentSort(ArrayList<String> currentSort) {
        Client.controller.AllProductsPanelController.currentSort = currentSort;
    }

    public static ArrayList<String> getAvailableSorts() {
        return availableSorts;
    }

    public static void setAvailableSorts(ArrayList<String> availableSorts) {
        Client.controller.AllProductsPanelController.availableSorts = availableSorts;
    }

    public static ArrayList<String> getAvailableFilters() {
        return availableFilters;
    }

    public static void setAvailableFilters(ArrayList<String> availableFilters) {
        Client.controller.AllProductsPanelController.availableFilters = availableFilters;
    }

    public static ArrayList<String> getCurrentFilters() {
        return currentFilters;
    }

    public static void setCurrentFilters(ArrayList<String> currentFilters) {
        Client.controller.AllProductsPanelController.currentFilters = currentFilters;
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
        }
        setAvailableFilters(allAvailableFilters);
        return allAvailableFilters;
    }

    public static void filterAnAvailableFilter() throws ExceptionsLibrary.NoFilterWithThisName, ExceptionsLibrary.NoProductException, ExceptionsLibrary.NoAccountException, ExceptionsLibrary.NoFeatureWithThisName, ExceptionsLibrary.NoCategoryException {
        getResult().clear();
        ArrayList<Product> products = getAllProducts();
        for (int count = 0; count < getCurrentFilters().size(); count++) {
            String i = getCurrentFilters().get(count);
            String[] splitFilters = i.split("--");
            switch (splitFilters[0]) {
                case "Search":
                    for (Product j : products) {
                        if (j.getName().equalsIgnoreCase(splitFilters[1])) {
                            if (!getResult().contains(j)) {
                                getResult().add(j);
                            }
                        }
                    }
                    Iterator<Product> iterator = getResult().iterator();
                    while (iterator.hasNext()) {
                        Product tempProduct = iterator.next();
                        if (!(tempProduct.getName().equalsIgnoreCase(splitFilters[1]))) {
                            iterator.remove();
                        }
                    }
                    checkPreviousFilters(count);
                    break;
                case "Category":
                    for (Product j : products) {
                        if (j.getCategory().getName().equalsIgnoreCase(splitFilters[1])) {
                            if (!getResult().contains(j)) {
                                getResult().add(j);
                            }
                        }
                    }
                    iterator = getResult().iterator();
                    while (iterator.hasNext()) {
                        Product tempProduct = iterator.next();
                        if (!(tempProduct.getCategory().getName().equalsIgnoreCase(splitFilters[1]))) {
                            if (!getCurrentFilters().contains("Category--" + tempProduct.getCategory().getName())) {
                                iterator.remove();
                            }
                        }
                    }
                    checkPreviousFilters(count);
                    break;
                case "Price":
                    for (Product j : products) {
                        boolean isInRange = j.getPriceWithOff() >= Double.parseDouble(splitFilters[1]) && j.getPriceWithOff() <= Double.parseDouble(splitFilters[2]);
                        if (isInRange) {
                            if (!getResult().contains(j)) {
                                getResult().add(j);
                            }
                        }
                    }
                    iterator = getResult().iterator();
                    while (iterator.hasNext()) {
                        Product tempProduct = iterator.next();
                        boolean isInRange = tempProduct.getPriceWithOff() >= Double.parseDouble(splitFilters[1]) && tempProduct.getPriceWithOff() <= Double.parseDouble(splitFilters[2]);
                        if (!isInRange) {
                            iterator.remove();
                        }
                    }
                    checkPreviousFilters(count);
                    break;
                case "Brand":
                    for (Product j : products) {
                        if (j.getCompany().equalsIgnoreCase(splitFilters[1])) {
                            if (!getResult().contains(j)) {
                                getResult().add(j);
                            }
                        }
                    }
                    iterator = getResult().iterator();
                    while (iterator.hasNext()) {
                        Product tempProduct = iterator.next();
                        if (!(tempProduct.getCompany().equalsIgnoreCase(splitFilters[1]))) {
                            if (!getCurrentFilters().contains("Brand--" + tempProduct.getCompany())) {
                                iterator.remove();
                            }
                        }
                    }
                    checkPreviousFilters(count);
                    break;
                case "Seller":
                    Seller seller = (Seller) GetDataFromDatabase.getAccount(splitFilters[1]);
                    for (Product j : seller.getSellerProducts()) {
                        if (!getResult().contains(j)) {
                            getResult().add(j);
                        }
                    }
                    iterator = getResult().iterator();
                    while (iterator.hasNext()) {
                        Product tempProduct = iterator.next();
                        for (Seller k : sellersOfThisProduct(tempProduct)) {
                            if (!getCurrentFilters().contains("Seller--" + k.getUsername())) {
                                iterator.remove();
                            }
                        }
                    }
                    checkPreviousFilters(count);
                    break;
                case "Availability":
                    if (splitFilters[1].equalsIgnoreCase("yes")) {
                        for (Product j : products) {
                            if (j.getAvailability() > 0) {
                                if (!getResult().contains(j)) {
                                    getResult().add(j);
                                }
                            }
                        }
                    }
                    iterator = getResult().iterator();
                    while (iterator.hasNext()) {
                        Product tempProduct = iterator.next();
                        if (tempProduct.getAvailability() == 0) {
                            iterator.remove();
                        }
                    }
                    checkPreviousFilters(count);
                    break;
                case "Feature":
                    Category category = null;
                    category = GetDataFromDatabase.getCategory(splitFilters[1]);
                    Feature feature = new Feature(splitFilters[2], splitFilters[3]);
                    Feature featureCategory = new Feature(splitFilters[2], null);
                    String featureToString = feature.toString();
                    String featureCategoryToString = featureCategory.toString();
                    ArrayList<String> featuresToString = new ArrayList<>();
                    for (Feature j : category.getFeatures()){
                        featuresToString.add(j.toString());
                    }
                    if (!featuresToString.contains(featureCategoryToString)) {
                        throw new ExceptionsLibrary.NoFeatureWithThisName();
                    }
                    for (Product j : products) {
                        if (j.getCategory().getName().equalsIgnoreCase(category.getName())) {
                            if (!getProductRemoved(j,featureToString)) {
                                if (!getResult().contains(j)) {
                                    getResult().add(j);
                                }
                            }
                        }
                    }
                    iterator = getResult().iterator();
                    while (iterator.hasNext()) {
                        Product tempProduct = iterator.next();
                        if (getProductRemoved(tempProduct, featureToString)) {
                            iterator.remove();
                        }
                    }
                    checkPreviousFilters(count);
                    break;
            }
        }

    }

    private static boolean getProductRemoved(Product product, String feature) {
        ArrayList<String> featuresToString = new ArrayList<>();
        for (Feature j : product.getCategoryFeatures()){
            featuresToString.add(j.toString());
        }
        if (featuresToString.contains(feature)) {
            return false;
        } else {
            return true;
        }
    }

    private static void checkPreviousFilters(int count) throws ExceptionsLibrary.NoAccountException {
        for (int l = 0; l < count; l++) {
            String i = getCurrentFilters().get(l);
            String[] splitFilters = i.split("--");
            switch (splitFilters[0]) {
                case "Search":
                    Iterator<Product> iterator = getResult().iterator();
                    while (iterator.hasNext()) {
                        Product tempProduct = iterator.next();
                        if (!(tempProduct.getName().equalsIgnoreCase(splitFilters[1]))) {
                            iterator.remove();
                        }
                    }
                    break;
                case "Category":
                    iterator = getResult().iterator();
                    while (iterator.hasNext()) {
                        Product tempProduct = iterator.next();
                        if (!(tempProduct.getCategory().getName().equalsIgnoreCase(splitFilters[1]))) {
                            if (!getCurrentFilters().contains("Category--" + tempProduct.getCategory().getName())) {
                                iterator.remove();
                            }
                        }
                    }
                    break;
                case "Price":
                    iterator = getResult().iterator();
                    while (iterator.hasNext()) {
                        Product tempProduct = iterator.next();
                        boolean isInRange = tempProduct.getPriceWithOff() >= Double.parseDouble(splitFilters[1]) && tempProduct.getPriceWithOff() <= Double.parseDouble(splitFilters[2]);
                        if (!isInRange) {
                            iterator.remove();
                        }
                    }
                    break;
                case "Brand":
                    iterator = getResult().iterator();
                    while (iterator.hasNext()) {
                        Product tempProduct = iterator.next();
                        if (!(tempProduct.getCompany().equalsIgnoreCase(splitFilters[1]))) {
                            if (!getCurrentFilters().contains("Brand--" + tempProduct.getCompany())) {
                                iterator.remove();
                            }
                        }
                    }
                    break;
                case "Seller":
                    iterator = getResult().iterator();
                    while (iterator.hasNext()) {
                        Product tempProduct = iterator.next();
                        for (Seller k : sellersOfThisProduct(tempProduct)) {
                            if (!getCurrentFilters().contains("Seller--" + k.getUsername())) {
                                iterator.remove();
                            }
                        }
                    }
                    break;
                case "Availability":
                    iterator = getResult().iterator();
                    while (iterator.hasNext()) {
                        Product tempProduct = iterator.next();
                        if (tempProduct.getAvailability() == 0) {
                            iterator.remove();
                        }
                    }
                    break;
                case "Feature":
                    String feature = new Feature(splitFilters[2],splitFilters[3]).toString();
                    iterator = getResult().iterator();
                    while (iterator.hasNext()) {
                        Product tempProduct = iterator.next();
                        if (getProductRemoved(tempProduct, feature)) {
                            iterator.remove();
                        }
                    }
                    break;
            }
        }
    }

    private static ArrayList<Seller> sellersOfThisProduct(Product product) throws ExceptionsLibrary.NoAccountException {
        ArrayList<Seller> sellers = new ArrayList<>();
        String path = "Resources/Accounts/Seller";
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
            String username = fileName.replace(".json", "");
            Seller seller = (Seller) GetDataFromDatabase.getAccount(username);
            for (Product j : seller.getSellerProducts()) {
                if (j.getProductId() == product.getProductId()) {
                    sellers.add(seller);
                }
            }
        }
        return sellers;
    }

    public static ArrayList<Product> getAllProducts() throws ExceptionsLibrary.NoProductException {
        ArrayList<Product> allProducts = new ArrayList<>();
        String path = "Resources/Products";
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
            int productId = Integer.parseInt(fileName.replace(".json", ""));
            allProducts.add(GetDataFromDatabase.getProduct(productId));
        }
        return allProducts;
    }

    public static ArrayList<String> showCurrentFilters() {
        return getCurrentFilters();
    }

    public static void disableFilter(int filter) {
        getCurrentFilters().remove(filter - 1);
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

    public static ArrayList<Product> showProducts() throws ExceptionsLibrary.NoProductException, ExceptionsLibrary.NoFilterWithThisName, ExceptionsLibrary.NoAccountException, ExceptionsLibrary.NoFeatureWithThisName, ExceptionsLibrary.NoCategoryException {
        if (getCurrentFilters().size() == 0) {
            setResult(getAllProducts());
        } else {
            filterAnAvailableFilter();
        }
        Collections.sort(getResult(), new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                try {
                    Field field = Product.class.getDeclaredField(getCurrentSort().get(0));
                    field.setAccessible(true);
                    if (getCurrentSort().get(0).equalsIgnoreCase("name") || getCurrentSort().get(0).equalsIgnoreCase("company")) {
                        String o1Name = (String) field.get(o1);
                        String o2Name = (String) field.get(o2);
                        return o1Name.compareTo(o2Name);
                    } else if (getCurrentSort().get(0).equalsIgnoreCase("date")) {
                        Date o1Date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse((String) field.get(o1));
                        Date o2Date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse((String) field.get(o2));
                        return o1Date.compareTo(o2Date);
                    } else if (getCurrentSort().get(0).equalsIgnoreCase("priceWithOff")) {
                        Double o1Price = (Double) field.get(o1);
                        Double o2Price = (Double) field.get(o2);
                        return o1Price.compareTo(o2Price);
                    } else if (getCurrentSort().get(0).equalsIgnoreCase("availability") || getCurrentSort().get(0).equalsIgnoreCase("productId")) {
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
        return getResult();
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
        for (Feature k : i.getCategoryFeatures()) {
            if (k.getParameter().equals(j)) {
                return true;
            }
        }
        return false;
    }


    public static void setSort(String value) {
        switch (value){
            case "Product ID":
                getCurrentSort().add("productId");
                break;
            case "Name":
                getCurrentSort().add("name");
                break;
            case "Company":
                getCurrentSort().add("company");
                break;
            case "Price":
                getCurrentSort().add("priceWithOff");
                break;
            case "Date":
                getCurrentSort().add("date");
                break;
            case "Availability":
                getCurrentSort().add("availability");
                break;
        }
    }
}
