package view.UserPanel.BuyLog;

import controller.CustomerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.BuyLog;
import view.UserPanel.BuyLog.BuyLogDetails.BuyLogDetailsStart;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BuyLogController implements Initializable {

    @FXML
    private TableView table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<BuyLog,Integer> logId = new TableColumn<>("Log ID");
        TableColumn<BuyLog,String> logDate= new TableColumn<>("Log Date");
        TableColumn<BuyLog,Double> value= new TableColumn<>("Value");
        TableColumn<BuyLog,Double> discountApplied = new TableColumn<>("Discount");
        TableColumn<BuyLog,String> delivery = new TableColumn<>("Delivery");
        logId.setStyle("-fx-alignment: CENTER");
        logDate.setStyle("-fx-alignment: CENTER");
        value.setStyle("-fx-alignment: CENTER");
        discountApplied.setStyle("-fx-alignment: CENTER");
        delivery.setStyle("-fx-alignment: CENTER");
        table.getColumns().addAll(logId,logDate,value,discountApplied,delivery);
        ObservableList<BuyLog> buyLogs = FXCollections.observableArrayList(CustomerController.showCustomerLogs());
        logId.setCellValueFactory(new PropertyValueFactory<>("logId"));
        logDate.setCellValueFactory(new PropertyValueFactory<>("logDate"));
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        discountApplied.setCellValueFactory(new PropertyValueFactory<>("discountApplied"));
        delivery.setCellValueFactory(new PropertyValueFactory<>("deliveryCondition"));
        table.setItems(buyLogs);
        table.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                try {
                    BuyLogDetailsStart.start((BuyLog) table.getSelectionModel().getSelectedItem());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    public void close() {
        Stage stage = (Stage) table.getScene().getWindow();
        stage.close();
    }

}
