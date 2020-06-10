package view.UserPanel.BuyLog.BuyLogDetails;

import controller.CustomerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.BuyLog;
import model.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class BuyLogDetails implements Initializable {
    @FXML
    private Label logId;
    @FXML
    private Label logDate;
    @FXML
    private Label value;
    @FXML
    private Label discountApplied;
    @FXML
    private Label delivery;
    @FXML
    private TableView products;

    private static BuyLog buyLog;

    public static BuyLog getBuyLog() {
        return buyLog;
    }

    public static void setBuyLog(BuyLog buyLog) {
        BuyLogDetails.buyLog = buyLog;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logId.setText(String.valueOf(getBuyLog().getLogId()));
        logDate.setText(getBuyLog().getLogDate());
        value.setText(String.valueOf(getBuyLog().getValue()));
        discountApplied.setText(String.valueOf(getBuyLog().getDiscountApplied()));
        delivery.setText(getBuyLog().getReceiverInfo().get("name"));

        TableColumn<BuyLog,Integer> productName = new TableColumn<>("Name");;
        TableColumn<BuyLog,Integer> price= new TableColumn<>("Price");;
        productName.setStyle("-fx-alignment: CENTER");
        price.setStyle("-fx-alignment: CENTER");
        products.getColumns().addAll(productName,price);
        ObservableList<Product> productsList = FXCollections.observableArrayList(getBuyLog().getLogProducts().keySet());
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("priceWithOff"));
        products.setItems(productsList);
    }

    public void close() {
        Stage stage = (Stage) products.getScene().getWindow();
        stage.close();
    }

}
