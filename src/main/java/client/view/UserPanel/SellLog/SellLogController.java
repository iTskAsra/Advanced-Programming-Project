package client.view.UserPanel.SellLog;

import controller.SellerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.SellLog;
import client.view.UserPanel.SellLog.SellLogDetails.SellLogDetailsStart;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SellLogController implements Initializable {

    @FXML
    private TableView table;

    ObservableList<SellLog> sellLogs;
    TableColumn<SellLog,Integer> logId = new TableColumn<>("Log ID");;
    TableColumn<SellLog,String> logDate= new TableColumn<>("Log Date");
    TableColumn<SellLog,Double> value= new TableColumn<>("Value");;
    TableColumn<SellLog,Double> discountApplied = new TableColumn<>("Discount");
    TableColumn<SellLog,String> buyer = new TableColumn<>("Buyer");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        logId.setStyle("-fx-alignment: CENTER");
        logDate.setStyle("-fx-alignment: CENTER");
        value.setStyle("-fx-alignment: CENTER");
        discountApplied.setStyle("-fx-alignment: CENTER");
        buyer.setStyle("-fx-alignment: CENTER");
        table.getColumns().addAll(logId,logDate,value,discountApplied,buyer);
        logId.setCellValueFactory(new PropertyValueFactory<>("logId"));
        logDate.setCellValueFactory(new PropertyValueFactory<>("logDate"));
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        discountApplied.setCellValueFactory(new PropertyValueFactory<>("discountApplied"));
        buyer.setCellValueFactory(new PropertyValueFactory<>("buyer"));
        table.setItems(sellLogs);
        table.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                try {
                    SellLogDetailsStart.start((SellLog) table.getSelectionModel().getSelectedItem());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        updateTable();
    }

    private void updateTable() {
        sellLogs = FXCollections.observableArrayList(SellerController.showSellerLogs());
        logId.setCellValueFactory(new PropertyValueFactory<>("logId"));
        logDate.setCellValueFactory(new PropertyValueFactory<>("logDate"));
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        discountApplied.setCellValueFactory(new PropertyValueFactory<>("discountApplied"));
        buyer.setCellValueFactory(new PropertyValueFactory<>("buyer"));
        table.setItems(sellLogs);
        table.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                try {
                    SellLogDetailsStart.start((SellLog) table.getSelectionModel().getSelectedItem());
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
