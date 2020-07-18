package Client.ClientController;


import Client.Client;
import controller.ExceptionsLibrary;
import controller.GetDataFromDatabase;
import model.*;

import java.util.*;

public class OffPageController {

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
        OffPageController.result = result;
    }

    public static ArrayList<String> getCurrentSort() {
        return currentSort;
    }

    public static void setCurrentSort(ArrayList<String> currentSort) {
        OffPageController.currentSort = currentSort;
    }

    public static ArrayList<String> getAvailableSorts() {
        return availableSorts;
    }

    public static void setAvailableSorts(ArrayList<String> availableSorts) {
        OffPageController.availableSorts = availableSorts;
    }

    public static ArrayList<String> getAvailableFilters() {
        return availableFilters;
    }

    public static void setAvailableFilters(ArrayList<String> availableFilters) {
        OffPageController.availableFilters = availableFilters;
    }

    public static ArrayList<String> getCurrentFilters() {
        return currentFilters;
    }

    public static void setCurrentFilters(ArrayList<String> currentFilters) {
        OffPageController.currentFilters = currentFilters;
    }

    public static ArrayList<String> viewCategories() {

        String func = "View Categories";
        Client.sendMessage(func);

        return (ArrayList<String>) Client.receiveObject();
//        String path = "Resources/Category";
//        File folder = new File(path);
//        FileFilter fileFilter = new FileFilter() {
//            @Override
//            public boolean accept(File file1) {
//                if (file1.getName().endsWith(".json")) {
//                    return true;
//                }
//                return false;
//            }
//        };
//        ArrayList<String> categoriesName = new ArrayList();
//        for (File i : folder.listFiles(fileFilter)) {
//            String fileName = i.getName();
//            categoriesName.add(fileName.replace(".json", ""));
//        }
//        return categoriesName;
    }

    public static ArrayList<String> showAvailableFilters() throws ExceptionsLibrary.NoFilterWithThisName, ExceptionsLibrary.NoCategoryException {
        ArrayList<String> allAvailableFilters = new ArrayList();

        String func = "Show Available Filters";
        Client.sendMessage(func);

        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoFilterWithThisName)
            throw new ExceptionsLibrary.NoFilterWithThisName();
        else if (response instanceof ExceptionsLibrary.NoCategoryException)
            throw new ExceptionsLibrary.NoCategoryException();
        else
            allAvailableFilters = (ArrayList<String>) response;
//        String path = "Resources/Category";
//        File folder = new File(path);
//        FileFilter fileFilter = new FileFilter() {
//            @Override
//            public boolean accept(File file1) {
//                if (file1.getName().endsWith(".json")) {
//                    return true;
//                }
//                return false;
//            }
//        };
//        for (File i : folder.listFiles(fileFilter)) {
//            String fileName = i.getName();
//            String categoryName = fileName.replace(".json", "");
//            allAvailableFilters.add(categoryName);
//            Category category = GetDataFromDatabase.getCategory(categoryName);
//        }
//        setAvailableFilters(allAvailableFilters);
        return allAvailableFilters;
    }

    public static void filterAnAvailableFilter() throws ExceptionsLibrary.NoFilterWithThisName, ExceptionsLibrary.NoProductException, ExceptionsLibrary.NoAccountException, ExceptionsLibrary.NoFeatureWithThisName, ExceptionsLibrary.NoCategoryException, ExceptionsLibrary.NoOffException {
        getResult().clear();
        ArrayList<Product> products = getOffProducts();
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

        String func = "Get Product Removed";
        Client.sendMessage(func);

        Object[] toSend = new Object[2];
        toSend[0] = product;
        toSend[1] = feature;
        Client.sendObject(toSend);

        return (boolean) Client.receiveObject();
//        ArrayList<String> featuresToString = new ArrayList<>();
//        for (Feature j : product.getCategoryFeatures()){
//            featuresToString.add(j.toString());
//        }
//        if (featuresToString.contains(feature)) {
//            return false;
//        } else {
//            return true;
//        }
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

        String func = "Seller Of This Product";
        Client.sendMessage(func);

        Client.sendObject(product);

        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoAccountException)
            throw new ExceptionsLibrary.NoAccountException();
//        String path = "Resources/Accounts/Seller";
//        File folder = new File(path);
//        FileFilter fileFilter = new FileFilter() {
//            @Override
//            public boolean accept(File file1) {
//                if (file1.getName().endsWith(".json")) {
//                    return true;
//                }
//                return false;
//            }
//        };
//        for (File i : folder.listFiles(fileFilter)) {
//            String fileName = i.getName();
//            String username = fileName.replace(".json", "");
//            Seller seller = (Seller) GetDataFromDatabase.getAccount(username);
//            for (Product j : seller.getSellerProducts()) {
//                if (j.getProductId() == product.getProductId()) {
//                    sellers.add(seller);
//                }
//            }
//        }
        return sellers;
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

    public static ArrayList<Product> showProducts() throws ExceptionsLibrary.NoProductException, ExceptionsLibrary.NoFilterWithThisName, ExceptionsLibrary.NoAccountException, ExceptionsLibrary.NoFeatureWithThisName, ExceptionsLibrary.NoCategoryException, ExceptionsLibrary.NoOffException {
        ArrayList<Product> products = new ArrayList<Product>();

        String func = "Show Products";
        Client.sendMessage(func);

        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoProductException)
            throw new ExceptionsLibrary.NoProductException();
        else if (response instanceof ExceptionsLibrary.NoFilterWithThisName)
            throw new ExceptionsLibrary.NoFilterWithThisName();
        else if (response instanceof ExceptionsLibrary.NoAccountException)
            throw new ExceptionsLibrary.NoAccountException();
        else if (response instanceof ExceptionsLibrary.NoFeatureWithThisName)
            throw new ExceptionsLibrary.NoFeatureWithThisName();
        else if (response instanceof ExceptionsLibrary.NoCategoryException)
            throw new ExceptionsLibrary.NoCategoryException();
        else if (response instanceof ExceptionsLibrary.NoOffException)
            throw new ExceptionsLibrary.NoOffException();
        else
            products = (ArrayList<Product>) response;

        return products;
//        if (getCurrentFilters().size() == 0) {
//            setResult(getOffProducts());
//        } else {
//            filterAnAvailableFilter();
//        }
//        Collections.sort(getResult(), new Comparator<Product>() {
//            @Override
//            public int compare(Product o1, Product o2) {
//                try {
//                    Field field = Product.class.getDeclaredField(getCurrentSort().get(0));
//                    field.setAccessible(true);
//                    if (getCurrentSort().get(0).equalsIgnoreCase("name") || getCurrentSort().get(0).equalsIgnoreCase("company")) {
//                        String o1Name = (String) field.get(o1);
//                        String o2Name = (String) field.get(o2);
//                        return o1Name.compareTo(o2Name);
//                    } else if (getCurrentSort().get(0).equalsIgnoreCase("date")) {
//                        Date o1Date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse((String) field.get(o1));
//                        Date o2Date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse((String) field.get(o2));
//                        return o1Date.compareTo(o2Date);
//                    } else if (getCurrentSort().get(0).equalsIgnoreCase("priceWithOff")) {
//                        Double o1Price = (Double) field.get(o1);
//                        Double o2Price = (Double) field.get(o2);
//                        return o1Price.compareTo(o2Price);
//                    } else if (getCurrentSort().get(0).equalsIgnoreCase("availability") || getCurrentSort().get(0).equalsIgnoreCase("productId")) {
//                        Integer o1Availability = (Integer) field.get(o1);
//                        Integer o2Availability = (Integer) field.get(o2);
//                        return o1Availability.compareTo(o2Availability);
//                    }
//
//                } catch (NoSuchFieldException | IllegalAccessException | ParseException e) {
//                    e.printStackTrace();
//                }
//                return 0;
//            }
//        });
//        return getResult();
    }

    public static Product goToProductPage(int productId) throws ExceptionsLibrary.NoProductException {
        Product product = null;

        String func = "Go To Product Page";
        Client.sendMessage(func);

        Client.sendMessage(String.valueOf(productId));
        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoProductException)
            throw new ExceptionsLibrary.NoProductException();
//        try {
//            product = GetDataFromDatabase.getProduct(productId);
//        } catch (ExceptionsLibrary.NoProductException e) {
//            throw new ExceptionsLibrary.NoProductException();
//        }
        return product;
    }

    private static boolean isFeature(Product i, String j) {
        String func = "Is Feature";
        Client.sendMessage(func);

        Object[] toSend = new Object[2];
        toSend[0] = i;
        toSend[1] = j;
        Client.sendObject(toSend);

        return (boolean) Client.receiveObject();
//        for (Feature k : i.getCategoryFeatures()) {
//            if (k.getParameter().equals(j)) {
//                return true;
//            }
//        }
//        return false;
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

    private static ArrayList<Product> getOffProducts() throws ExceptionsLibrary.NoProductException, ExceptionsLibrary.NoOffException {
        ArrayList<Product> allProducts = new ArrayList<>();

        String func = "Get Off Products";
        Client.sendMessage(func);

        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoProductException)
            throw new ExceptionsLibrary.NoProductException();
        else if (response instanceof ExceptionsLibrary.NoOffException)
            throw new ExceptionsLibrary.NoOffException();
//        String path = "Resources/Products";
//        File folder = new File(path);
//        FileFilter fileFilter = new FileFilter() {
//            @Override
//            public boolean accept(File file1) {
//                if (file1.getName().endsWith(".json")) {
//                    return true;
//                }
//                return false;
//            }
//        };
//        for (File i : folder.listFiles(fileFilter)) {
//            String fileName = i.getName();
//            int productId = Integer.parseInt(fileName.replace(".json", ""));
//            if (isInOff(productId)) {
//                allProducts.add(GetDataFromDatabase.getProduct(productId));
//            }
//        }
        return allProducts;
    }

    public static boolean isInOff(int productId) {

        String func = "Is In Off";
        Client.sendMessage(func);

        Client.sendMessage(String.valueOf(productId));

        return (boolean) Client.receiveObject();
//        String path = "Resources/Offs";
//        File folder = new File(path);
//        FileFilter fileFilter = new FileFilter() {
//            @Override
//            public boolean accept(File file1) {
//                if (file1.getName().endsWith(".json")) {
//                    return true;
//                }
//                return false;
//            }
//        };
//        for (File i : folder.listFiles(fileFilter)) {
//            String fileName = i.getName();
//            int offId = Integer.parseInt(fileName.replace(".json", ""));
//            Off off = null;
//            try {
//                off = GetDataFromDatabase.getOff(offId);
//            } catch (ExceptionsLibrary.NoOffException e) {
//                e.printStackTrace();
//            }
//            for (String j : off.getOffProducts()){
//                if (j.equals(String.valueOf(productId))){
//                    return true;
//                }
//            }
//        }
//        return false;
    }

    public static Off offDetails(int productId) throws ExceptionsLibrary.NoOffException {

        String func = "Off Details";
        Client.sendMessage(func);


        Client.sendMessage(String.valueOf(productId));
        Object response = Client.receiveObject();

        if (response instanceof ExceptionsLibrary.NoOffException)
            throw new ExceptionsLibrary.NoOffException();
        else
            return (Off) response;

//        String path = "Resources/Offs";
//        File folder = new File(path);
//        FileFilter fileFilter = new FileFilter() {
//            @Override
//            public boolean accept(File file1) {
//                if (file1.getName().endsWith(".json")) {
//                    return true;
//                }
//                return false;
//            }
//        };
//        for (File i : folder.listFiles(fileFilter)) {
//            String fileName = i.getName();
//            int offId = Integer.parseInt(fileName.replace(".json", ""));
//            Off off = GetDataFromDatabase.getOff(offId);
//            for (String j : off.getOffProducts()){
//                if (j.equals(String.valueOf(productId))){
//                    return off;
//                }
//            }
//        }
//        return null;
    }

}

