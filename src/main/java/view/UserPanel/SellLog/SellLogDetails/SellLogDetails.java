package view.UserPanel.SellLog.SellLogDetails;

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
import model.SellLog;

import java.net.URL;
import java.util.ResourceBundle;

public class SellLogDetails implements Initializable {
    @FXML
    private Label logId;
    @FXML
    private Label logDate;
    @FXML
    private Label value;
    @FXML
    private Label discountApplied;
    @FXML
    private Label buyer;
    @FXML
    private TableView products;

    private static SellLog sellLog;

    public static SellLog getSellLog() {
        return sellLog;
    }

    public static void setSellLog(SellLog sellLog) {
        SellLogDetails.sellLog = sellLog;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logId.setText(String.valueOf(getSellLog().getLogId()));
        logDate.setText(getSellLog().getLogDate());
        value.setText(String.valueOf(getSellLog().getValue()));
        discountApplied.setText(String.valueOf(getSellLog().getDiscountApplied()));
        buyer.setText(getSellLog().getBuyer());

        TableColumn<BuyLog,Integer> productName = new TableColumn<>("Name");;
        TableColumn<BuyLog,Integer> price= new TableColumn<>("Price");;
        productName.setStyle("-fx-alignment: CENTER");
        price.setStyle("-fx-alignment: CENTER");
        products.getColumns().addAll(productName,price);
        ObservableList<Product> productsList = FXCollections.observableArrayList(getSellLog().getLogProducts().keySet());
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("priceWithOff"));
        products.setItems(productsList);
    }

    public void close() {
        Stage stage = (Stage) products.getScene().getWindow();
        stage.close();
    }

}
