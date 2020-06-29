package Client.view.Cart;

import controller.CartController;
import controller.CustomerController;
import controller.ExceptionsLibrary;
import controller.GetDataFromDatabase;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Product;
import view.AlertBox.ErrorBox.ErrorBoxStart;
import view.AlertBox.MessageBox.AlertBoxStart;
import view.Cart.ProductElement.ProductElement;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class Cart implements Initializable {

    @FXML
    private VBox products;
    @FXML
    private Label totalPrice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateCart();
    }

    public void updateCart(){

        products.getChildren().clear();
        for (Product i : CartController.getCartProducts().keySet()){
            FXMLLoader loader = new FXMLLoader();
            File file = new File("src/main/java/view/Cart/ProductElement/ProductElement.fxml");
            URL urlFile = null;
            try {
                urlFile = file.toURI().toURL();
                loader.setLocation(urlFile);
                ProductElement.setProductId(i.getProductId());
                ProductElement.setQuantityNumber(CartController.getCartProducts().get(i));
                VBox productElement = loader.load();
                productElement.setId(String.valueOf(i.getProductId()));
                Button increase = null;
                Button decrease = null;
                for (Node j : productElement.getChildren()){
                    if (j instanceof Pane){
                        Pane pane = (Pane) j;
                        for (Node k : pane.getChildren()){
                            if (k instanceof ButtonBar){
                                ButtonBar buttonBar = (ButtonBar) k;
                                for (Node l : buttonBar.getButtons()) {
                                    if (l.getId().equals("increase")) {
                                        increase = (Button) l;
                                    } else if (l.getId().equals("decrease")) {
                                        decrease = (Button) l;
                                    }
                                }
                            }
                        }
                    }
                }
                increase.setOnAction( e -> {
                    try {
                        CartController.increaseProduct(i);
                        AlertBoxStart.messageRun("Message","Product increased by 1!");
                        updateCart();
                    } catch (ExceptionsLibrary.NoProductException | ExceptionsLibrary.NotEnoughNumberAvailableException exception) {
                        ErrorBoxStart.errorRun(exception);
                    }
                });
                decrease.setOnAction( e -> {
                    try {
                        CartController.decreaseProduct(i.getProductId());
                        AlertBoxStart.messageRun("Message","Product decreased by 1!");
                        updateCart();
                    } catch (ExceptionsLibrary.NoProductException exception) {
                        ErrorBoxStart.errorRun(exception);
                    }
                });
                products.setVgrow(productElement, Priority.ALWAYS);
                products.getChildren().add(productElement);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        totalPrice.textProperty().bind(new SimpleStringProperty(String.valueOf(CartController.getTotalPriceWithoutSale())));
    }

    public void close(){
        Stage stage = (Stage) products.getScene().getWindow();
        stage.close();
    }

    public void purchase() throws IOException {
        if (CartController.getCartCustomer() == null && CustomerController.getCustomer() == null) {
            ErrorBoxStart.errorRun(new ExceptionsLibrary.NotLoggedInException());
            return;
        }
        Stage stage = (Stage) totalPrice.getScene().getWindow();
        File file = new File("src/main/java/view/Purchase/Purchase.fxml");
        URL url = file.toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
    }

}
