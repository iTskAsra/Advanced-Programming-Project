package Client.ClientView.UserPanel.DiscountCodes;

import controller.CustomerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.BuyLog;
import model.Sale;

import java.net.URL;
import java.util.ResourceBundle;

public class DiscountCodes implements Initializable {

    @FXML
    private TableView table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<BuyLog,Integer> saleCode = new TableColumn<>("Sale Code");;
        TableColumn<BuyLog,Integer> startDate= new TableColumn<>("Start Date");
        TableColumn<BuyLog,Integer> endDate= new TableColumn<>("End Date");;
        TableColumn<BuyLog,Integer> salePercent = new TableColumn<>("Percent");
        TableColumn<BuyLog,Integer> maxAmount = new TableColumn<>("Max Amount");
        TableColumn<BuyLog,Integer> validTimes = new TableColumn<>("Valid Times");
        saleCode.setStyle("-fx-alignment: CENTER");
        startDate.setStyle("-fx-alignment: CENTER");
        endDate.setStyle("-fx-alignment: CENTER");
        salePercent.setStyle("-fx-alignment: CENTER");
        maxAmount.setStyle("-fx-alignment: CENTER");
        validTimes.setStyle("-fx-alignment: CENTER");
        table.getColumns().addAll(saleCode,startDate,endDate,salePercent,maxAmount,validTimes);
        ObservableList<Sale> sales = FXCollections.observableArrayList(CustomerController.showDiscountCodes());
        saleCode.setCellValueFactory(new PropertyValueFactory<>("saleCode"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        salePercent.setCellValueFactory(new PropertyValueFactory<>("salePercent"));
        maxAmount.setCellValueFactory(new PropertyValueFactory<>("saleMaxAmount"));
        validTimes.setCellValueFactory(new PropertyValueFactory<>("validTimes"));
        table.setItems(sales);
    }

    public void close() {
        Stage stage = (Stage) table.getScene().getWindow();
        stage.close();
    }

}
