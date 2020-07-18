package Client.ClientView.UserPanel.ManageSellerProducts;

import Client.ClientController.ExceptionsLibrary;
import Client.ClientController.SellerController;
import Client.ClientView.AlertBox.ErrorBox.ErrorBoxStart;
import Client.ClientView.AlertBox.MessageBox.AlertBoxStart;
import Client.ClientView.UserPanel.ManageSellerProducts.NewEditProductRequest.NewEditProductRequest;
import Client.ClientView.UserPanel.ManageSellerProducts.ProductBuyers.ProductBuyers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Product;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageSellerProducts implements Initializable {

    @FXML
    private TableView table;
    @FXML
    private Button edit;

    ObservableList<Product> products = null;
    TableColumn<Product,Integer> productId = new TableColumn<>("Product ID");
    TableColumn<Product,String> name = new TableColumn<>("Name");
    TableColumn<Product,String> company = new TableColumn<>("Company");
    TableColumn<Product,Double> price = new TableColumn<>("Price");
    TableColumn<Product,Integer> quantity = new TableColumn<>("Quantity");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productId.setStyle("-fx-alignment: CENTER");
        name.setStyle("-fx-alignment: CENTER");
        company.setStyle("-fx-alignment: CENTER");
        price.setStyle("-fx-alignment: CENTER");
        quantity.setStyle("-fx-alignment: CENTER");
        table.getColumns().addAll(productId, name, company,price, quantity);
        updateTable();
    }

    public void newProductRequestButtonClicked() throws IOException {
        NewEditProductRequest.setNewProduct(true);
        Stage stage = new Stage();
        File file = new File("src/main/java/view/UserPanel/ManageSellerProducts/NewEditProductRequest/NewEditProductRequest.fxml");
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
            products = FXCollections.observableArrayList(SellerController.showSellerProducts());
        } catch (ExceptionsLibrary.NoAccountException e) {
            ErrorBoxStart.errorRun(e);
        }
        productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        company.setCellValueFactory(new PropertyValueFactory<>("company"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("availability"));
        table.setItems(products);
    }

    public void close() {
        Stage stage = (Stage) table.getScene().getWindow();
        stage.close();
    }

    public void editButtonClicked() throws IOException {
        if (table.getSelectionModel().getSelectedItem() == null){
            AlertBoxStart.messageRun("Notice!","You should select a product from the table first!");
            return;
        }
        NewEditProductRequest.setEditProduct((Product) table.getSelectionModel().getSelectedItem());
        NewEditProductRequest.setNewProduct(false);
        Stage stage = new Stage();
        File file = new File("src/main/java/view/UserPanel/ManageSellerProducts/NewEditProductRequest/NewEditProductRequest.fxml");
        URL url = file.toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.showAndWait();
        updateTable();
    }

    public void removeProduct() throws IOException {
        if (table.getSelectionModel().getSelectedItem() == null){
            AlertBoxStart.messageRun("Notice!","You should select a request from the table first!");
            return;
        }
        Product temp = (Product) table.getSelectionModel().getSelectedItem();
        try {
            SellerController.removeProductRequest(temp.getProductId());
            AlertBoxStart.messageRun("Message","Request Sent!");
            updateTable();
        } catch (ExceptionsLibrary.NoProductException | ExceptionsLibrary.NoAccountException e) {
            ErrorBoxStart.errorRun(e);
        }
    }

    public void productBuyers() throws IOException {
        if (table.getSelectionModel().getSelectedItem() == null){
            AlertBoxStart.messageRun("Notice!","You should select a product from the table first!");
            return;
        }
        Product temp = (Product) table.getSelectionModel().getSelectedItem();
        ProductBuyers.setProductId(temp.getProductId());
        Stage stage = new Stage();
        File file = new File("src/main/java/view/UserPanel/ManageSellerProducts/ProductBuyers/ProductBuyers.fxml");
        URL url = file.toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
