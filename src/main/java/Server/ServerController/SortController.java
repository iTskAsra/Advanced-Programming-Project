package Server.ServerController;

import model.*;
import LocalExceptions.ExceptionsLibrary;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class SortController {
    private static String sortElement;

    public static String getSortElement() {
        return sortElement;
    }

    public static void setSortElement(String sortElement) {
        SortController.sortElement = sortElement.replaceAll("\\s*", "");
    }


    public static ArrayList<Account> sortAccounts(ArrayList<Account> allAccounts) {
        Collections.sort(allAccounts, new Comparator<Account>() {
            @Override
            public int compare(Account o1, Account o2) {
                try {
                    Field field = null;
                    for (Field i : Account.class.getDeclaredFields()) {
                        if (i.getName().equalsIgnoreCase(getSortElement())) {
                            field = i;
                        }
                    }
                    field.setAccessible(true);
                    if (getSortElement().equalsIgnoreCase("credit")) {
                        Double o1SortElement = (Double) field.get(o1);
                        Double o2SortElement = (Double) field.get(o2);
                        return o1SortElement.compareTo(o2SortElement);
                    } else {
                        String o1SortElement = (String) field.get(o1);
                        String o2SortElement = (String) field.get(o2);
                        return o1SortElement.compareToIgnoreCase(o2SortElement);
                    }


                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        return allAccounts;
    }

    public static ArrayList<Sale> sortSales(ArrayList<Sale> allSales) {
        Collections.sort(allSales, new Comparator<Sale>() {
            @Override
            public int compare(Sale o1, Sale o2) {
                try {
                    Field field = null;
                    for (Field i : Sale.class.getDeclaredFields()) {
                        if (i.getName().equalsIgnoreCase(getSortElement())) {
                            field = i;
                        }
                    }
                    field.setAccessible(true);
                    if (getSortElement().equalsIgnoreCase("salePercent") || getSortElement().equalsIgnoreCase("saleMaxAmount")) {
                        Double o1SortElement = (Double) field.get(o1);
                        Double o2SortElement = (Double) field.get(o2);
                        return o1SortElement.compareTo(o2SortElement);
                    } else if (getSortElement().equalsIgnoreCase("validTimes")) {
                        Integer o1SortElement = (Integer) field.get(o1);
                        Integer o2SortElement = (Integer) field.get(o2);
                        return o1SortElement.compareTo(o2SortElement);
                    } else {
                        String o1SortElement = (String) field.get(o1);
                        String o2SortElement = (String) field.get(o2);
                        return o1SortElement.compareToIgnoreCase(o2SortElement);
                    }


                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        return allSales;
    }

    public static ArrayList<Request> sortRequest(ArrayList<Request> allRequests) {
        Collections.sort(allRequests, new Comparator<Request>() {
            @Override
            public int compare(Request o1, Request o2) {
                try {
                    Field field = null;
                    for (Field i : Request.class.getDeclaredFields()) {
                        if (i.getName().equalsIgnoreCase(getSortElement())) {
                            field = i;
                        }
                    }
                    field.setAccessible(true);
                    if (getSortElement().equalsIgnoreCase("requestId")) {
                        Integer o1SortElement = (Integer) field.get(o1);
                        Integer o2SortElement = (Integer) field.get(o2);
                        return o1SortElement.compareTo(o2SortElement);
                    } else if (getSortElement().equalsIgnoreCase("requestType")) {
                        RequestType o1SortElement = (RequestType) field.get(o1);
                        RequestType o2SortElement = (RequestType) field.get(o2);
                        return o1SortElement.compareTo(o2SortElement);
                    } else if (getSortElement().equalsIgnoreCase("requestCondition")) {
                        RequestOrCommentCondition o1SortElement = (RequestOrCommentCondition) field.get(o1);
                        RequestOrCommentCondition o2SortElement = (RequestOrCommentCondition) field.get(o2);
                        return o1SortElement.compareTo(o2SortElement);
                    } else {
                        String o1SortElement = (String) field.get(o1);
                        String o2SortElement = (String) field.get(o2);
                        return o1SortElement.compareToIgnoreCase(o2SortElement);
                    }


                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        return allRequests;
    }

    public static ArrayList<Category> sortCategories(ArrayList<Category> allCategories) {
        Collections.sort(allCategories, new Comparator<Category>() {
            @Override
            public int compare(Category o1, Category o2) {
                try {
                    Field field = null;
                    for (Field i : Category.class.getDeclaredFields()) {
                        if (i.getName().equalsIgnoreCase(getSortElement())) {
                            field = i;
                        }
                    }
                    field.setAccessible(true);
                    String o1SortElement = (String) field.get(o1);
                    String o2SortElement = (String) field.get(o2);
                    return o1SortElement.compareToIgnoreCase(o2SortElement);


                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        return allCategories;
    }

    public static ArrayList<SellLog> sortSellLogs(ArrayList<SellLog> allSellLogs) {
        Collections.sort(allSellLogs, new Comparator<SellLog>() {
            @Override
            public int compare(SellLog o1, SellLog o2) {
                try {
                    Field field = null;
                    for (Field i : SellLog.class.getDeclaredFields()) {
                        if (i.getName().equalsIgnoreCase(getSortElement())) {
                            field = i;
                        }
                    }
                    field.setAccessible(true);
                    if (getSortElement().equalsIgnoreCase("value") || getSortElement().equalsIgnoreCase("discountApplied")) {
                        Double o1SortElement = (Double) field.get(o1);
                        Double o2SortElement = (Double) field.get(o2);
                        return o1SortElement.compareTo(o2SortElement);
                    } else if (getSortElement().equalsIgnoreCase("logId")) {
                        Integer o1SortElement = (Integer) field.get(o1);
                        Integer o2SortElement = (Integer) field.get(o2);
                        return o1SortElement.compareTo(o2SortElement);
                    }
                    else {
                        String o1SortElement = (String) field.get(o1);
                        String o2SortElement = (String) field.get(o2);
                        return o1SortElement.compareToIgnoreCase(o2SortElement);
                    }


                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        return allSellLogs;
    }

    public static ArrayList<BuyLog> sortBuyLogs(ArrayList<BuyLog> allBuyLogs) {
        Collections.sort(allBuyLogs, new Comparator<BuyLog>() {
            @Override
            public int compare(BuyLog o1, BuyLog o2) {
                try {
                    Field field = null;
                    for (Field i : BuyLog.class.getDeclaredFields()) {
                        if (i.getName().equalsIgnoreCase(getSortElement())) {
                            field = i;
                        }
                    }
                    field.setAccessible(true);
                    if (getSortElement().equalsIgnoreCase("value") || getSortElement().equalsIgnoreCase("discountApplied")) {
                        Double o1SortElement = (Double) field.get(o1);
                        Double o2SortElement = (Double) field.get(o2);
                        return o1SortElement.compareTo(o2SortElement);
                    } else if (getSortElement().equalsIgnoreCase("logId")) {
                        Integer o1SortElement = (Integer) field.get(o1);
                        Integer o2SortElement = (Integer) field.get(o2);
                        return o1SortElement.compareTo(o2SortElement);
                    }
                    else {
                        String o1SortElement = (String) field.get(o1);
                        String o2SortElement = (String) field.get(o2);
                        return o1SortElement.compareToIgnoreCase(o2SortElement);
                    }


                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        return allBuyLogs;
    }

    public static ArrayList<Product> sortProducts(ArrayList<Product> allProducts) {
        Collections.sort(allProducts, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                try {
                    Field field = null;
                    for (Field i : Product.class.getDeclaredFields()) {
                        if (i.getName().equalsIgnoreCase(getSortElement())) {
                            field = i;
                        }
                    }
                    field.setAccessible(true);
                    if (getSortElement().equalsIgnoreCase("name") || getSortElement().equalsIgnoreCase("company")) {
                        String o1Name = (String) field.get(o1);
                        String o2Name = (String) field.get(o2);
                        return o1Name.compareTo(o2Name);
                    } else if (getSortElement().equalsIgnoreCase("date")) {
                        Date o1Date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse((String) field.get(o1));
                        Date o2Date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse((String) field.get(o2));
                        return o1Date.compareTo(o2Date);
                    } else if (getSortElement().equalsIgnoreCase("price")) {
                        Double o1Price = (Double) field.get(o1);
                        Double o2Price = (Double) field.get(o2);
                        return o1Price.compareTo(o2Price);
                    } else if (getSortElement().equalsIgnoreCase("availability")||getSortElement().equalsIgnoreCase("productId")) {
                        Integer o1Availability = (Integer) field.get(o1);
                        Integer o2Availability = (Integer) field.get(o2);
                        return o1Availability.compareTo(o2Availability);
                    }

                } catch (IllegalAccessException | ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        return allProducts;
    }

    public static ArrayList<Off> sortOffs(ArrayList<Off> sellerOffs) {
        Collections.sort(sellerOffs, new Comparator<Off>() {
            @Override
            public int compare(Off o1, Off o2) {
                try {
                    Field field = null;
                    for (Field i : Off.class.getDeclaredFields()) {
                        if (i.getName().equalsIgnoreCase(getSortElement())) {
                            field = i;
                        }
                    }
                    field.setAccessible(true);
                    if (getSortElement().equalsIgnoreCase("startDate") || getSortElement().equalsIgnoreCase("endDate")) {
                        String o1SortElement = (String) field.get(o1);
                        String o2SortElement = (String) field.get(o2);
                        return o1SortElement.compareTo(o2SortElement);
                    } else if (getSortElement().equalsIgnoreCase("offId")) {
                        Integer o1SortElement = (Integer) field.get(o1);
                        Integer o2SortElement = (Integer) field.get(o2);
                        return o1SortElement.compareTo(o2SortElement);
                    } else if (getSortElement().equalsIgnoreCase("offAmount")) {
                        Double o1SortElement = (Double) field.get(o1);
                        Double o2SortElement = (Double) field.get(o2);
                        return o1SortElement.compareTo(o2SortElement);
                    } else if (getSortElement().equalsIgnoreCase("offCondition")) {
                        ProductOrOffCondition o1SortElement = (ProductOrOffCondition) field.get(o1);
                        ProductOrOffCondition o2SortElement = (ProductOrOffCondition) field.get(o2);
                        return o1SortElement.compareTo(o2SortElement);
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        return sellerOffs;
    }
}
