package Client.ClientView.UserPanel.BuyLog;

import Client.ClientController.CustomerController;
import Client.ClientView.UserPanel.BuyLog.BuyLogDetails.BuyLogDetailsStart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.BuyLog;
import LocalExceptions.ExceptionsLibrary;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BuyLogController implements Initializable {

    @FXML
    private TableView table;

    ObservableList<BuyLog> buyLogs;
    TableColumn<BuyLog,Integer> logId = new TableColumn<>("Log ID");
    TableColumn<BuyLog,String> logDate= new TableColumn<>("Log Date");
    TableColumn<BuyLog,Double> value= new TableColumn<>("Value");
    TableColumn<BuyLog,Double> discountApplied = new TableColumn<>("Discount");
    TableColumn<BuyLog,String> delivery = new TableColumn<>("Delivery");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        logId.setStyle("-fx-alignment: CENTER");
        logDate.setStyle("-fx-alignment: CENTER");
        value.setStyle("-fx-alignment: CENTER");
        discountApplied.setStyle("-fx-alignment: CENTER");
        delivery.setStyle("-fx-alignment: CENTER");
        table.getColumns().addAll(logId,logDate,value,discountApplied,delivery);
        updateTable();
    }

    private void updateTable(){
        try {
            buyLogs = FXCollections.observableArrayList(CustomerController.showCustomerLogs());
        } catch (ExceptionsLibrary.CreditNotSufficientException e) {
            e.printStackTrace();
        }
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
