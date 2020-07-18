package Client.ClientView.UserPanel.SellLog.SellLogDetails;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
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

        TableColumn<String[],Integer> productId = new TableColumn<>("Product ID");
        TableColumn<String[],String> productName = new TableColumn<>("Name");
        TableColumn<String[],Double> price= new TableColumn<>("Price");
        TableColumn<String[],Integer> quantity = new TableColumn<>("Quantity");

        productId.setStyle("-fx-alignment: CENTER");
        productName.setStyle("-fx-alignment: CENTER");
        quantity.setStyle("-fx-alignment: CENTER");
        price.setStyle("-fx-alignment: CENTER");
        products.getColumns().addAll(productId,productName,price,quantity);
        ObservableList<String[]> productsList = FXCollections.observableArrayList(getSellLog().getLogProducts());
        productId.setCellValueFactory(c -> {
            return new SimpleIntegerProperty(Integer.parseInt(c.getValue()[0])).asObject();
        });
        productName.setCellValueFactory(c -> {
            return new SimpleStringProperty(c.getValue()[1]);
        });
        price.setCellValueFactory(c -> {
            return new SimpleDoubleProperty(Double.parseDouble(c.getValue()[2])).asObject();
        });
        quantity.setCellValueFactory(c -> {
            return new SimpleIntegerProperty(Integer.parseInt(c.getValue()[3])).asObject();
        });

        products.setItems(productsList);
    }

    public void close() {
        Stage stage = (Stage) products.getScene().getWindow();
        stage.close();
    }

}
