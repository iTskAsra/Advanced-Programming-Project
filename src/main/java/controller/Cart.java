package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Cart {
    private static HashMap<Product, Integer> cartProducts;
    private static Customer cartCustomer;
    private static HashMap<String, String> receiverInfo;
    private static double saleDiscount = 0.00;
    private static double totalPrice = showTotalPrice();

    static {
        cartProducts = new HashMap<>();
        receiverInfo = new HashMap<>();
    }

    public Cart(Customer customer) {
        this.cartCustomer = customer;
    }

    public static double getSaleDiscount() {
        return saleDiscount;
    }

    public static void setSaleDiscount(double saleDiscount) {
        Cart.saleDiscount = saleDiscount;
    }

    public static double getTotalPrice() {
        return totalPrice;
    }

    public static void setTotalPrice(double totalPrice) {
        Cart.totalPrice = totalPrice;
    }

    public static HashMap<String, String> getReceiverInfo() {
        return receiverInfo;
    }

    public static void setReceiverInfo(HashMap<String, String> receiverInfo) {
        Cart.receiverInfo = receiverInfo;
    }

    public static HashMap<Product, Integer> getCartProducts() {
        return cartProducts;
    }

    public static void setCartProducts(HashMap<Product, Integer> cartProducts) {
        Cart.cartProducts = cartProducts;
    }

    public static Customer getCartCustomer() {
        return cartCustomer;
    }

    public static void setCartCustomer(Customer cartCustomer) {
        Cart.cartCustomer = cartCustomer;
    }

    public static HashMap<Product, Integer> showCartProducts() {
        return getCartProducts();
    }

    public static void increaseProduct(int productId) {
        for (Product i : getCartProducts().keySet()) {
            //Product temp = GetDataFromDatabase.getProduct(i.getProductId());
            if (i.getProductId() == productId) {
                if (i.getAvailability() >= cartProducts.get(i) + 1) {
                    cartProducts.put(i, cartProducts.get(i) + 1);
                } else {
                    //TODO Error
                }
            }
        }
        //TODO Error
    }

    public static void decreaseProduct(int productId) {
        for (Product i : getCartProducts().keySet()) {
            //Product temp = GetDataFromDatabase.getProduct(i.getProductId());
            if (i.getProductId() == productId) {
                if (cartProducts.get(i) == 1) {
                    cartProducts.remove(i);
                } else {
                    cartProducts.put(i, cartProducts.get(i) - 1);
                }
            }
        }
        //TODO Error
    }

    public static String viewCartProductDetails(int productId) {
        Product product = GetDataFromDatabase.getProduct(productId);
        Gson gson = new GsonBuilder().serializeNulls().create();
        String data = gson.toJson(product);
        return data;
    }

    public static double showTotalPrice() {
        Double totalPrice = 0.00;
        for (Product i : cartProducts.keySet()) {
            totalPrice += (i.getPrice() * cartProducts.get(i));
        }
        setTotalPrice(totalPrice - getSaleDiscount());
        return totalPrice;
    }

    public static void receiverProcess(HashMap<String, String> data) {
        setReceiverInfo(data);
    }

    public static void discountApply(String saleCode) {
        Sale sale = GetDataFromDatabase.getSale(saleCode);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date startDate = simpleDateFormat.parse(sale.getStartDate());
            Date endDate = simpleDateFormat.parse(sale.getEndDate());
            Date now = new Date();
            if (now.compareTo(startDate) > 0) {
                if (now.compareTo(endDate) < 0) {
                    for (Sale j : cartCustomer.getSaleCodes()) {
                        if (j.getSaleCode().equals(saleCode)) {
                            if (j.getValidTimes() >= 1) {
                                if (sale.getSaleMaxAmount() <= (sale.getSalePercent() * showTotalPrice())) {
                                    setSaleDiscount(sale.getSaleMaxAmount());
                                } else setSaleDiscount(sale.getSalePercent() * showTotalPrice());
                                setTotalPrice(getTotalPrice() - getSaleDiscount());
                                SetDataToDatabase.setAccount(cartCustomer);
                            } else {
                                //TODO Error
                            }
                        }
                    }

                } else {
                    //TODO Error
                }
            } else {
                //TODO Error
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void purchase() {
        //TODO Off price
        if (getTotalPrice() <= getCartCustomer().getCredit()) {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String dateNow = dateFormat.format(date);
            ArrayList<Product> products = new ArrayList<>();
            ArrayList<String> sellerNames = new ArrayList<>();
            for (Product i : cartProducts.keySet()) {
                products.add(i);
                sellerNames.add(i.getSeller().getCompanyName());
            }
            Gson gson = new GsonBuilder().serializeNulls().create();
            String receiverInfo = gson.toJson(getReceiverInfo());
            BuyLog buyLog = new BuyLog(dateNow, getTotalPrice(), getSaleDiscount(), products, sellerNames, receiverInfo);
            cartCustomer.getCustomerLog().add(buyLog);
            SetDataToDatabase.setAccount(cartCustomer);
            HashMap<Seller, ArrayList<Product>> productSellers = new HashMap<>();
            for (Product i : cartProducts.keySet()) {
                ArrayList<Product> sellerProducts = productSellers.get(i.getSeller());
                if (sellerProducts == null) {
                    sellerProducts = new ArrayList<>();
                    sellerProducts.add(i);
                    productSellers.put(i.getSeller(), sellerProducts);
                } else {
                    if (!sellerProducts.contains(i)) sellerProducts.add(i);
                }
            }
            for (Seller i : productSellers.keySet()){
                double price = 0.00;
                for (Product j : productSellers.get(i)){
                    price+=j.getPrice();
                }
                SellLog sellLog = new SellLog(dateNow, price, getSaleDiscount(),productSellers.get(i),cartCustomer,receiverInfo);
                i.getSellerLogs().add(sellLog);
                i.setCredit(i.getCredit()+price);//TODO calculate off
                SetDataToDatabase.setAccount(i);
            }
        }
    }


}
