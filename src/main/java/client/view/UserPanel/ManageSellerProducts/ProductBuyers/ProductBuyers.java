package client.view.UserPanel.ManageSellerProducts.ProductBuyers;

import client.view.AlertBox.ErrorBox.ErrorBoxStart;
import controller.ExceptionsLibrary;
import controller.GetDataFromDatabase;
import controller.SellerController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Customer;
import model.SellLog;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductBuyers implements Initializable {

    @FXML
    private ListView list;

    private static int productId;

    public static int getProductId() {
        return productId;
    }

    public static void setProductId(int productId) {
        ProductBuyers.productId = productId;
    }

    public void close() {
        Stage stage = (Stage) list.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<SellLog> buyersLogs = null;
        try {
            buyersLogs = SellerController.showProductBuyers(getProductId());
            setProductId(0);
            for (SellLog i : buyersLogs) {
                Customer customer = null;
                try {
                    customer = (Customer) GetDataFromDatabase.getAccount(i.getBuyer());
                    String formatted = "Log ID : "+ i.getLogId() +" - "+"Customer : " +i.getBuyer()+ " - "+"Date : "+i.getLogDate()+" - "+"Total log price : "+i.getValue();
                    list.getItems().add(formatted);
                } catch (ExceptionsLibrary.NoAccountException e) {
                    e.printStackTrace();
                }
            }
        } catch (ExceptionsLibrary.NoProductException | ExceptionsLibrary.NoAccountException e) {
            ErrorBoxStart.errorRun(e);
        }
    }
}
