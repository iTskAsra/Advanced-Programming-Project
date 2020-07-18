package Client.ClientView.UserPanel.ManageSellerOffs;

import controller.ExceptionsLibrary;
import controller.SellerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Off;
import view.AlertBox.ErrorBox.ErrorBoxStart;
import view.AlertBox.MessageBox.AlertBoxStart;
import view.UserPanel.ManageSellerOffs.NewEditOffRequest.NewEditOffRequest;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageSellerOffs implements Initializable {

    @FXML
    private TableView table;
    @FXML
    private Button edit;

    ObservableList<Off> offs = null;
    TableColumn<Off,Integer> offId = new TableColumn<>("Off ID");
    TableColumn<Off,String> startDate = new TableColumn<>("Start Date");
    TableColumn<Off,String> endDate = new TableColumn<>("End Date");
    TableColumn<Off,Double> amount = new TableColumn<>("Off Amount");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        offId.setStyle("-fx-alignment: CENTER");
        startDate.setStyle("-fx-alignment: CENTER");
        endDate.setStyle("-fx-alignment: CENTER");
        amount.setStyle("-fx-alignment: CENTER");
        table.getColumns().addAll(offId, startDate, endDate, amount);
        updateTable();
    }

    public void newOffRequestButtonClicked() throws IOException {
        NewEditOffRequest.setNewOff(true);
        Stage stage = new Stage();
        File file = new File("src/main/java/view/UserPanel/ManageSellerOffs/NewEditOffRequest/NewEditOffRequest.fxml");
        URL url = file.toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.showAndWait();
        updateTable();
    }

    private void updateTable() {
        try {
            offs = FXCollections.observableArrayList(SellerController.showOffs());
        } catch (ExceptionsLibrary.NoAccountException e) {
            ErrorBoxStart.errorRun(e);
        }
        offId.setCellValueFactory(new PropertyValueFactory<>("offId"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        amount.setCellValueFactory(new PropertyValueFactory<>("offAmount"));
        table.setItems(offs);
    }

    public void close() {
        Stage stage = (Stage) table.getScene().getWindow();
        stage.close();
    }

    public void editButtonClicked() throws IOException {
        if (table.getSelectionModel().getSelectedItem() == null){
            AlertBoxStart.messageRun("Notice!","You should select an off from the table first!");
            return;
        }
        NewEditOffRequest.setEditOff((Off) table.getSelectionModel().getSelectedItem());
        NewEditOffRequest.setNewOff(false);
        Stage stage = new Stage();
        File file = new File("src/main/java/view/UserPanel/ManageSellerOffs/NewEditOffRequest/NewEditOffRequest.fxml");
        URL url = file.toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.showAndWait();
        updateTable();
    }
}
