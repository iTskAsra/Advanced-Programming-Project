package view.UserPanel.AdminPanel.Requests;

import controller.AdminController;
import controller.ExceptionsLibrary;
import controller.SellerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Request;
import model.RequestType;
import view.AlertBox.ErrorBox.ErrorBoxStart;
import view.AlertBox.MessageBox.AlertBoxStart;
import view.UserPanel.AdminPanel.Requests.RequestDetails.RequestDetails;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Requests implements Initializable {

    @FXML
    private TableView table;
    @FXML
    private ButtonBar processBar;


    ObservableList<Request> requests = null;
    TableColumn<Request,Integer> requestId = new TableColumn<>("Request ID");
    TableColumn<Request, RequestType> requestType = new TableColumn<>("Request Type");
    TableColumn<Request,String> seller = new TableColumn<>("Request Seller");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        processBar.setDisable(true);
        requestId.setStyle("-fx-alignment: CENTER");
        requestType.setStyle("-fx-alignment: CENTER");
        seller.setStyle("-fx-alignment: CENTER");
        table.getColumns().addAll(requestId, requestType, seller);
        table.setOnMouseClicked(e -> {
            processBar.setDisable(false);
        });
        updateTable();
    }


    private void updateTable() {
        try {
            requests = FXCollections.observableArrayList(AdminController.showAdminRequests());
        } catch (ExceptionsLibrary.NoRequestException e) {
            ErrorBoxStart.errorRun(e);
        }
        requestId.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        requestType.setCellValueFactory(new PropertyValueFactory<>("requestType"));
        seller.setCellValueFactory(new PropertyValueFactory<>("requestSeller"));
        table.setItems(requests);
    }

    @FXML
    private void accept(){
        Request temp = (Request) table.getSelectionModel().getSelectedItem();
        try {
            AdminController.processRequest(temp.getRequestId(),true);
        } catch (ExceptionsLibrary.NoRequestException | ExceptionsLibrary.NoAccountException | ExceptionsLibrary.UsernameAlreadyExists | ExceptionsLibrary.NoProductException e) {
            ErrorBoxStart.errorRun(e);
        }
        updateTable();
    }

    @FXML
    private void reject(){
        Request temp = (Request) table.getSelectionModel().getSelectedItem();
        try {
            AdminController.processRequest(temp.getRequestId(),false);
        } catch (ExceptionsLibrary.NoRequestException | ExceptionsLibrary.NoAccountException | ExceptionsLibrary.UsernameAlreadyExists | ExceptionsLibrary.NoProductException e) {
            ErrorBoxStart.errorRun(e);
        }
        updateTable();
    }

    @FXML
    private void showDetails() throws IOException {
        Request temp = (Request) table.getSelectionModel().getSelectedItem();
        if (temp == null){
            AlertBoxStart.messageRun("Notice!","You should select a request from the table first!");
            return;
        }
        RequestDetails.setRequest(temp);
        Stage stage = new Stage();
        File file = new File("src/main/java/view/UserPanel/AdminPanel/Requests/RequestDetails/RequestDetails.fxml");
        URL url = file.toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void close() {
        Stage stage = (Stage) table.getScene().getWindow();
        stage.close();
    }
}