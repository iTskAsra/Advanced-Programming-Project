package controller;

import model.*;
import view.Purchase.Purchase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        setTotalPriceWithSale();
        return totalPriceWithSale;
    }

    public static void setTotalPriceWithSale() {
        CartController.totalPriceWithSale = getTotalPriceWithoutSale() - getSaleDiscount();
    }

    public static double getTotalPriceWithoutSale() {
        setTotalPriceWithoutSale(showTotalPrice());
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
        if (cartProducts == null) {
            cartProducts = new HashMap<>();
        }
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
            if (i.getProductId() == product.getProductId()) {
                if (i.getAvailability() >= cartProducts.get(i) + 1) {
                    cartProducts.put(i, cartProducts.get(i) + 1);
                    return;
                } else {
                    throw new ExceptionsLibrary.NotEnoughNumberAvailableException();
                }
            }
        }
        throw new ExceptionsLibrary.NoProductException();
    }

    public static void decreaseProduct(int productId) throws ExceptionsLibrary.NoProductException {
        for (Product i : getCartProducts().keySet()) {
            if (i.getProductId() == productId) {
                if (cartProducts.get(i) == 1) {
                    cartProducts.remove(i);
                } else {
                    cartProducts.put(i, cartProducts.get(i) - 1);
                }
                return;
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
        getCartProducts();
        for (Product i : cartProducts.keySet()) {
            totalPrice += (i.getPriceWithOff() * cartProducts.get(i));
        }
        return totalPrice;
    }

    public static void receiverProcess(HashMap<String, String> data) throws ExceptionsLibrary.NotLoggedInException {
        if (getCartCustomer() == null && CustomerController.getCustomer() == null) {
            throw new ExceptionsLibrary.NotLoggedInException();
        } else if (getCartCustomer() == null && CustomerController.getCustomer() != null) {
            setCartCustomer(CustomerController.getCustomer());
        }
        setReceiverInfo(data);
    }

    public static void discountApply(String saleCode) throws ExceptionsLibrary.NoSaleException, ExceptionsLibrary.UsedAllValidTimesException, ExceptionsLibrary.SaleExpiredException, ExceptionsLibrary.SaleNotStartedYetException {
        if (saleCode == null) {
            setTotalPriceWithoutSale(showTotalPrice());
            setSaleDiscount(0.00);
            setTotalPriceWithSale();
            return;
        } else if (saleCode.startsWith("Off:")) {
            Double amount = Double.parseDouble(saleCode.replace("Off:", ""));
            setSaleDiscount(amount);
            setTotalPriceWithSale();
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
                                    if (sale.getSaleMaxAmount() <= (sale.getSalePercent() * showTotalPrice() / 100)) {
                                        setSaleDiscount(sale.getSaleMaxAmount());
                                    } else {
                                        setSaleDiscount(sale.getSalePercent() * showTotalPrice() / 100);
                                    }
                                    j.setValidTimes(j.getValidTimes() - 1);
                                    setTotalPriceWithSale();
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
            offAmount += ((i.getPrice() - i.getPriceWithOff())*products.get(i));
        }
        return offAmount;
    }

    public static double amountOfMoneyFromSell(HashMap<Product, Integer> products) {
        Double amount = 0.00;
        for (Product i : products.keySet()) {
            amount += (i.getPriceWithOff()*products.get(i));
        }
        return amount;
    }

    public static void purchase() throws ExceptionsLibrary.CreditNotSufficientException, ExceptionsLibrary.NoAccountException, ExceptionsLibrary.NoProductException {
        if (getTotalPriceWithSale() <= getCartCustomer().getCredit()) {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String dateNow = dateFormat.format(date);
            ArrayList<String[]> cartProducts = new ArrayList<>();
            for (Product i : getCartProducts().keySet()){
                String[] productDetails = new String[5];
                productDetails[0] = String.valueOf(i.getProductId());
                productDetails[1] = i.getName();
                productDetails[2] = String.valueOf(i.getPriceWithOff());
                productDetails[3] = String.valueOf(getCartProducts().get(i));
                cartProducts.add(productDetails);
            }
            BuyLog buyLog = new BuyLog(dateNow, getTotalPriceWithSale(), getSaleDiscount(), cartProducts, DeliveryCondition.NOT_SENT, getReceiverInfo());
            getCartCustomer().getCustomerLog().add(buyLog);
            getCartCustomer().setCredit(getCartCustomer().getCredit() - getTotalPriceWithSale());
            SetDataToDatabase.setAccount(getCartCustomer());
            HashMap<String, HashMap<Product, Integer>> productSellers = new HashMap<>();
            for (Product i : getCartProducts().keySet()) {
                if (productSellers.containsKey(i.getSeller().getUsername())) {
                    productSellers.get(i.getSeller().getUsername()).put(i, getCartProducts().get(i));
                } else {
                    HashMap<Product, Integer> products = new HashMap<>();
                    products.put(i, getCartProducts().get(i));
                    productSellers.put(i.getSeller().getUsername(), products);
                }
            }
            for (String k : productSellers.keySet()) {
                ArrayList<String[]> productSeller = new ArrayList<>();
                for (Product j : productSellers.get(k).keySet()){
                    String[] productDetails = new String[5];
                    productDetails[0] = String.valueOf(j.getProductId());
                    productDetails[1] = j.getName();
                    productDetails[2] = String.valueOf(j.getPriceWithOff());
                    productDetails[3] = String.valueOf(getCartProducts().get(j));
                    productSeller.add(productDetails);
                    j.setAvailability(j.getAvailability() - productSellers.get(k).get(j));
                    SetDataToDatabase.setProduct(j);
                    SetDataToDatabase.updateSellerOfProduct(j, 0);
                }
                Seller newSeller = (Seller) GetDataFromDatabase.getAccount(k);
                SellLog sellLog = new SellLog(dateNow, amountOfMoneyFromSell(productSellers.get(k)), getOffFromHashMap(productSellers.get(k)), productSeller, getCartCustomer().getUsername(), "Sent");
                newSeller.setCredit(newSeller.getCredit() + amountOfMoneyFromSell(productSellers.get(newSeller.getUsername())));
                newSeller.getSellerLogs().add(sellLog);
                SetDataToDatabase.setAccount(newSeller);

            }
            cartProducts.clear();
        } else {
            throw new ExceptionsLibrary.CreditNotSufficientException();
        }
    }


}
