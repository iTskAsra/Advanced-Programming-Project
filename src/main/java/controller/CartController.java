package controller;

import model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class CartController {
    private static HashMap<Product, Integer> cartProducts;
    private static Customer cartCustomer;
    private static HashMap<String, String> receiverInfo;
    private static double saleDiscount = 0.00;
    private static double totalPriceWithoutSale = showTotalPrice();
    private static double totalPriceWithSale = 0.00;


    static {
        cartProducts = new HashMap<>();
        receiverInfo = new HashMap<>();
    }

    public CartController(Customer customer) {
        this.cartCustomer = customer;
    }

    public static double getSaleDiscount() {
        return saleDiscount;
    }

    public static void setSaleDiscount(double saleDiscount) {
        CartController.saleDiscount = saleDiscount;
    }

    public static double getTotalPriceWithSale() {
        return totalPriceWithSale;
    }

    public static void setTotalPriceWithSale(double totalPriceWithSale) {
        CartController.totalPriceWithSale = totalPriceWithSale;
    }

    public static double getTotalPriceWithoutSale() {
        return totalPriceWithoutSale;
    }

    public static void setTotalPriceWithoutSale(double totalPriceWithoutSale) {
        CartController.totalPriceWithoutSale = totalPriceWithoutSale;
    }

    public static HashMap<String, String> getReceiverInfo() {
        return receiverInfo;
    }

    public static void setReceiverInfo(HashMap<String, String> receiverInfo) {
        CartController.receiverInfo = receiverInfo;
    }

    public static HashMap<Product, Integer> getCartProducts() {
        return cartProducts;
    }

    public static void setCartProducts(HashMap<Product, Integer> cartProducts) {
        CartController.cartProducts = cartProducts;
    }

    public static Customer getCartCustomer() {
        return cartCustomer;
    }

    public static void setCartCustomer(Customer cartCustomer) {
        CartController.cartCustomer = cartCustomer;
    }

    public static HashMap<Product, Integer> showCartProducts() {
        return getCartProducts();
    }

    public static void increaseProduct(Product product) throws ExceptionsLibrary.NoProductException, ExceptionsLibrary.NotEnoughNumberAvailableException {
        for (Product i : getCartProducts().keySet()) {
            if (i.getProductId() == product.getProductId() && i.getSeller().getUsername().equals(product.getSeller().getUsername())) {
                if (i.getAvailability() >= cartProducts.get(i) + 1) {
                    cartProducts.put(i, cartProducts.get(i) + 1);
                    return;
                } else {
                    throw new ExceptionsLibrary.NotEnoughNumberAvailableException();
                }
            } else if (i.getProductId() == product.getProductId() && !(i.getSeller().getUsername().equals(product.getSeller().getUsername()))) {
                if (product.getAvailability() >= 1) {
                    cartProducts.put(product, 1);
                    return;
                } else {
                    throw new ExceptionsLibrary.NotEnoughNumberAvailableException();
                }
            }
        }
        throw new ExceptionsLibrary.NoProductException();
    }

    public static void decreaseProduct(int productId) throws ExceptionsLibrary.NoProductException {
        //TODO check decrease
        for (Product i : getCartProducts().keySet()) {
            if (i.getProductId() == productId) {
                if (cartProducts.get(i) == 1) {
                    cartProducts.remove(i);
                    return;
                } else {
                    cartProducts.put(i, cartProducts.get(i) - 1);
                    return;
                }
            }
        }
        throw new ExceptionsLibrary.NoProductException();
    }

    public static Product viewCartProductDetails(int productId) throws ExceptionsLibrary.NoProductException {
        Product product = null;
        try {
            product = GetDataFromDatabase.getProduct(productId);
            return product;
        } catch (ExceptionsLibrary.NoProductException e) {
            throw new ExceptionsLibrary.NoProductException();
        }

    }

    public static double showTotalPrice() {
        Double totalPrice = 0.00;
        for (Product i : cartProducts.keySet()) {
            totalPrice += (i.getPriceWithOff() * cartProducts.get(i));
        }
        return totalPrice;
    }

    public static void receiverProcess(HashMap<String, String> data) {

        setReceiverInfo(data);
    }

    public static void discountApply(String saleCode) throws ExceptionsLibrary.NoSaleException, ExceptionsLibrary.UsedAllValidTimesException, ExceptionsLibrary.SaleExpiredException, ExceptionsLibrary.SaleNotStartedYetException {
        if (saleCode == null) {
            return;
        } else if (saleCode.startsWith("Off:")) {
            saleCode.replace("Off:","");
            Double amount = Double.parseDouble(saleCode);
            setSaleDiscount(amount);
            setTotalPriceWithSale(getTotalPriceWithoutSale() - getSaleDiscount());
        } else {
            Sale sale = GetDataFromDatabase.getSale(saleCode);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date startDate = simpleDateFormat.parse(sale.getStartDate());
                Date endDate = simpleDateFormat.parse(sale.getEndDate());
                Date now = new Date();
                if (now.compareTo(startDate) >= 0) {
                    if (now.compareTo(endDate) <= 0) {
                        for (Sale j : cartCustomer.getSaleCodes()) {
                            if (j.getSaleCode().equals(saleCode)) {
                                if (j.getValidTimes() >= 1) {
                                    if (sale.getSaleMaxAmount() <= (sale.getSalePercent() * showTotalPrice())) {
                                        setSaleDiscount(sale.getSaleMaxAmount());
                                    } else {
                                        setSaleDiscount(sale.getSalePercent() * showTotalPrice());
                                    }
                                    j.setValidTimes(j.getValidTimes() - 1);
                                    setTotalPriceWithSale(getTotalPriceWithoutSale() - getSaleDiscount());
                                } else {
                                    throw new ExceptionsLibrary.UsedAllValidTimesException();
                                }
                            }
                        }

                    } else {
                        throw new ExceptionsLibrary.SaleExpiredException();
                    }
                } else {
                    throw new ExceptionsLibrary.SaleNotStartedYetException();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static double getOffFromHashMap(HashMap<Product, Integer> products) {
        Double offAmount = 0.00;
        for (Product i : products.keySet()) {
            offAmount += (i.getPrice() - i.getPriceWithOff());
        }
        return offAmount;
    }

    public static double amountOfMoneyFromSell(HashMap<Product, Integer> products) {
        Double amount = 0.00;
        for (Product i : products.keySet()) {
            amount += (i.getPrice() - i.getPriceWithOff());
        }
        return amount;
    }

    public static void purchase() throws ExceptionsLibrary.CreditNotSufficientException {
        if (getTotalPriceWithSale() <= getCartCustomer().getCredit()) {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String dateNow = dateFormat.format(date);
            BuyLog buyLog = new BuyLog(dateNow, getTotalPriceWithSale(), getSaleDiscount(), getCartProducts(), "Delivered", getReceiverInfo());
            cartCustomer.getCustomerLog().add(buyLog);
            cartCustomer.setCredit(cartCustomer.getCredit() - getTotalPriceWithSale());
            SetDataToDatabase.setAccount(cartCustomer);
            HashMap<Seller, HashMap<Product, Integer>> productSellers = new HashMap<>();
            for (Product i : getCartProducts().keySet()) {
                if (productSellers.containsKey(i.getSeller())) {
                    productSellers.get(i.getSeller()).put(i, getCartProducts().get(i));
                } else {
                    HashMap<Product, Integer> products = new HashMap<>();
                    productSellers.get(i.getSeller()).put(i, getCartProducts().get(i));
                    productSellers.put(i.getSeller(), products);
                }
            }
            for (Seller i : productSellers.keySet()) {
                SellLog sellLog = new SellLog(dateNow, amountOfMoneyFromSell(productSellers.get(i)), getOffFromHashMap(productSellers.get(i)), productSellers.get(i), getCartCustomer(), "Sent");
                i.setCredit(i.getCredit() + amountOfMoneyFromSell(productSellers.get(i)));
                i.getSellerLogs().add(sellLog);
                SetDataToDatabase.setAccount(i);
            }

        } else {
            throw new ExceptionsLibrary.CreditNotSufficientException();
        }
    }


}
