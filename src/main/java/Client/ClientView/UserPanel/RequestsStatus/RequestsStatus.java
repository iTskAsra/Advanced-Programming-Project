package Client.ClientView.UserPanel.RequestsStatus;

import Client.ClientController.SellerController;
import Client.ClientView.AlertBox.ErrorBox.ErrorBoxStart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import LocalExceptions.ExceptionsLibrary;
import java.net.URL;
import java.util.ResourceBundle;

public class RequestsStatus implements Initializable {

    @FXML
    private TableView table;

    ObservableList<Request> requests = null;
    TableColumn<Request,Integer> requestId = new TableColumn<>("Request ID");
    TableColumn<Request, RequestType> requestType = new TableColumn<>("Request Type");
    TableColumn<Request, RequestOrCommentCondition> requestCondition = new TableColumn<>("Request Condition");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requestId.setStyle("-fx-alignment: CENTER");
        requestType.setStyle("-fx-alignment: CENTER");
        requestCondition.setStyle("-fx-alignment: CENTER");
        table.getColumns().addAll(requestId, requestType, requestCondition);
        updateTable();
    }

    private void updateTable() {
        try {
            requests = FXCollections.observableArrayList(SellerController.showSellerRequests());
        } catch (ExceptionsLibrary.NoRequestException e) {
            ErrorBoxStart.errorRun(e);
        }
        requestId.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        requestType.setCellValueFactory(new PropertyValueFactory<>("requestType"));
        requestCondition.setCellValueFactory(new PropertyValueFactory<>("requestCondition"));
        table.setItems(requests);
    }

    public void close() {
        Stage stage = (Stage) table.getScene().getWindow();
        stage.close();
    }

}
