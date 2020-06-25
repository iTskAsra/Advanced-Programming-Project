package view.ProductPage.Compare;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.ExceptionsLibrary;
import controller.ProductPageController;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Feature;
import model.Product;
import model.Rate;
import view.AlertBox.ErrorBox.ErrorBoxStart;
import view.MainMenuStage.CheckFields;

import java.net.URL;
import java.util.ResourceBundle;

public class Compare implements Initializable {

    @FXML
    private TableView table;
    @FXML
    private TextField productIdTextField;

    ObservableList<Product> products = null;
    @FXML
    TableColumn<Product, Integer> productId = new TableColumn<>("Product ID");
    TableColumn<Product, String> name = new TableColumn<>("Name");
    TableColumn<Product, String> company = new TableColumn<>("Company");
    TableColumn<Product, Double> price = new TableColumn<>("Price");
    TableColumn<Product, Double> rate = new TableColumn<>("Rate");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table.setVisible(false);

    }

    public void compareButtonClicked() {
        try {
            CheckFields.checkField("int",productIdTextField.getText());
        } catch (ExceptionsLibrary.NotAcceptableFormatInput notAcceptableFormatInput) {
            ErrorBoxStart.errorRun(notAcceptableFormatInput);
            return;
        }
        int productIds = Integer.parseInt(productIdTextField.getText());
        try {
            products = FXCollections.observableArrayList(ProductPageController.compare(productIds));
            Product product1 = products.get(0);
            Product product2 = products.get(1);

            productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            company.setCellValueFactory(new PropertyValueFactory<>("company"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
            rate.setCellValueFactory( c -> {
                return new SimpleDoubleProperty(calculateRate(c.getValue())).asObject();
            });

            productId.setStyle("-fx-alignment: CENTER");
            name.setStyle("-fx-alignment: CENTER");
            company.setStyle("-fx-alignment: CENTER");
            price.setStyle("-fx-alignment: CENTER");
            rate.setStyle("-fx-alignment: CENTER");
            table.getColumns().addAll(productId, name, company, price, rate);

            for (Feature i : product1.getCategoryFeatures()){
                TableColumn<Product, String> feature  = new TableColumn<>(i.getParameter());
                feature.setStyle("-fx-alignment: CENTER");
                feature.setCellValueFactory( c -> {
                    return new SimpleStringProperty(getFeatureOfProduct(c.getValue(),i));
                });
                table.getColumns().add(feature);
            }

            table.setItems(products);
            table.setVisible(true);

        } catch (ExceptionsLibrary.NoProductException | ExceptionsLibrary.CategoriesNotMatch e) {
            ErrorBoxStart.errorRun(e);
        }

    }

    private String getFeatureOfProduct(Product product, Feature i) {
        for (Feature j : product.getCategoryFeatures()){
            if (j.getParameter().equals(i.getParameter())){
                return j.getParameterValue();
            }
        }
        return null;
    }

    private double calculateRate(Product product) {
        double rateSum = 0.00;
        for (Rate i : product.getRates()){
            rateSum += i.getRateScore();
        }
        rateSum = rateSum / product.getRates().size();
        return rateSum;
    }

    public void closeButtonClicked(){
        Stage stage = (Stage) table.getScene().getWindow();
        stage.close();
    }

}
