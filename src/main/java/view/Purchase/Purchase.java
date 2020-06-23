package view.Purchase;

import controller.CartController;
import controller.CustomerController;
import controller.ExceptionsLibrary;
import controller.SetPeriodicSales;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.BuyLog;
import view.AlertBox.ErrorBox.ErrorBoxStart;
import view.AlertBox.MessageBox.AlertBoxStart;
import view.UserPanel.BuyLog.BuyLogDetails.BuyLogDetailsStart;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Purchase implements Initializable {

    @FXML
    private Label receiverInfo;
    @FXML
    private Label nameSale;
    @FXML
    private Label address;
    @FXML
    private Label phoneNumber;
    @FXML
    private Label discountApply;
    @FXML
    private Label payment;
    @FXML
    private Label pricesSales;
    @FXML
    private Button close;
    @FXML
    private Button next;
    @FXML
    private Button apply;
    @FXML
    private Button back;
    @FXML
    private TextField nameText;
    @FXML
    private TextField saleText;
    @FXML
    private TextField addressText;
    @FXML
    private TextField phoneNumberText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        close.setOnAction( e -> {
            closePurchase();
        });
        state1();
    }

    private void closePurchase() {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    private void state1() {
        receiverInfo.setVisible(true);
        nameSale.setVisible(true);
        nameSale.setText("Name:");
        address.setVisible(true);
        phoneNumber.setVisible(true);
        nameText.setVisible(true);
        addressText.setVisible(true);
        phoneNumberText.setVisible(true);
        next.setText("Next");

        saleText.setVisible(false);
        back.setVisible(false);
        discountApply.setVisible(false);
        payment.setVisible(false);
        pricesSales.setVisible(false);
        apply.setVisible(false);
        next.setOnAction( e -> {
            HashMap<String, String> receiverInfo = new HashMap<>();
            receiverInfo.put("name",nameText.getText());
            receiverInfo.put("address",addressText.getText());
            receiverInfo.put("phone",phoneNumberText.getText());
            try {
                CartController.receiverProcess(receiverInfo);
            } catch (ExceptionsLibrary.NotLoggedInException exception) {
                ErrorBoxStart.errorRun(exception);
            }
            state2();
        });

    }

    private void state2() {
        discountApply.setVisible(true);
        nameSale.setText("Sale:");
        nameSale.setVisible(true);
        back.setVisible(true);
        apply.setVisible(true);
        nameText.setVisible(false);

        saleText.setVisible(true);


        receiverInfo.setVisible(false);
        addressText.setVisible(false);
        phoneNumberText.setVisible(false);
        address.setVisible(false);
        phoneNumber.setVisible(false);
        payment.setVisible(false);
        pricesSales.setVisible(true);
        pricesSales.setText("");
        next.setText("Next");



        next.setOnAction( e -> {
            String saleCode;
            if (CartController.getTotalPriceWithoutSale() >= 1000000 && CartController.getSaleDiscount() != 0) {
                Double saleAmount = SetPeriodicSales.randomOff();
                int saleAmountRounded = (int) Math.round(saleAmount);
                AlertBoxStart.messageRun("Sale","Your cart value is greater or equal to 1,000,000 so you will get a " + saleAmountRounded + " off!");
                saleCode = "Off:" + saleAmountRounded;
                try {
                    CartController.discountApply(saleCode);
                } catch (ExceptionsLibrary.NoSaleException | ExceptionsLibrary.UsedAllValidTimesException | ExceptionsLibrary.SaleExpiredException | ExceptionsLibrary.SaleNotStartedYetException exception) {
                    ErrorBoxStart.errorRun(exception);
                }
            }
            state3();
        });
        back.setOnAction( e -> {
            state1();
        });
        apply.setOnAction( e -> {
            try {
                CartController.discountApply(saleText.getText());
                pricesSales.setText("You got a " + CartController.getSaleDiscount() + " discount!");
            } catch (ExceptionsLibrary.NoSaleException | ExceptionsLibrary.UsedAllValidTimesException | ExceptionsLibrary.SaleExpiredException | ExceptionsLibrary.SaleNotStartedYetException exception) {
                ErrorBoxStart.errorRun(exception);
            }
        });

    }

    private void state3() {
        receiverInfo.setVisible(false);
        nameSale.setVisible(false);
        address.setVisible(false);
        phoneNumber.setVisible(false);
        nameText.setVisible(false);
        addressText.setVisible(false);
        phoneNumberText.setVisible(false);

        back.setVisible(true);
        discountApply.setVisible(false);
        payment.setVisible(true);
        pricesSales.setVisible(true);
        pricesSales.setText("Total Price With Sale : "+ CartController.getTotalPriceWithSale() + "\nSale Amount: "+CartController.getSaleDiscount());
        apply.setVisible(false);
        next.setText("Pay");
        saleText.setVisible(false);

        next.setOnAction( e -> {
            try {
                CartController.purchase();
                BuyLogDetailsStart.start(getLastLog());
                AlertBoxStart.messageRun("Message","Cart Bought!");
                closePurchase();
            } catch (ExceptionsLibrary.CreditNotSufficientException | ExceptionsLibrary.NoAccountException | ExceptionsLibrary.NoProductException exception) {
                ErrorBoxStart.errorRun(exception);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        back.setOnAction( e -> {
            state2();
        });

    }

    private BuyLog getLastLog() {
        ArrayList<BuyLog> buyLogs = CustomerController.showCustomerLogs();
        return buyLogs.get(buyLogs.size()-1);
    }
}
