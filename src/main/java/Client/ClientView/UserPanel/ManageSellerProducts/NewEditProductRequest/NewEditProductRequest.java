package Client.ClientView.UserPanel.ManageSellerProducts.NewEditProductRequest;

import Client.ClientController.AdminController;
import Client.ClientController.ExceptionsLibrary;
import Client.ClientController.SellerController;
import Client.ClientView.AlertBox.ErrorBox.ErrorBoxStart;
import Client.ClientView.AlertBox.MessageBox.AlertBoxStart;
import Client.ClientView.MainMenuStage.CheckFields;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;
import view.Base.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class NewEditProductRequest implements Initializable {

    @FXML
    private TextField productId;
    @FXML
    private TextField name;
    @FXML
    private TextField company;
    @FXML
    private TextField price;
    @FXML
    private TextField quantity;
    @FXML
    private ComboBox category;
    @FXML
    private TextField description;
    @FXML
    private VBox features;

    private static boolean newProduct;
    private static Product editProduct;

    public static boolean isNewProduct() {
        return newProduct;
    }

    public static void setNewProduct(boolean newProduct) {
        NewEditProductRequest.newProduct = newProduct;
    }

    public static Product getEditProduct() {
        return editProduct;
    }

    public static void setEditProduct(Product editProduct) {
        NewEditProductRequest.editProduct = editProduct;
    }

    public void sendRequestButtonClicked() {
        try {
            CheckFields.checkField("double",price.getText());
            CheckFields.checkField("int",quantity.getText());
        } catch (ExceptionsLibrary.NotAcceptableFormatInput notAcceptableFormatInput) {
            ErrorBoxStart.errorRun(notAcceptableFormatInput);
            return;
        }

        if (isNewProduct()) {
            ArrayList<Feature> productFeatures = new ArrayList<>();
            for (Node j : features.getChildren()) {
                TextField i = (TextField) j;
                Feature feature = new Feature(i.getId(), i.getText());
                productFeatures.add(feature);
            }
            Product product = new Product(ProductOrOffCondition.PENDING_TO_CREATE, name.getText(), company.getText(), Double.parseDouble(price.getText()), null, Integer.parseInt(quantity.getText()), (Category) category.getValue(), productFeatures, description.getText(), new ArrayList<Rate>(), new ArrayList<Comment>(), Main.localDateTime.format(Main.dateTimeFormatter), Double.parseDouble(price.getText()));
            SellerController.addProductRequest(product);
            AlertBoxStart.messageRun("Message","Request sent!");
            close();
        }
        else {
            HashMap<String, String> dataToEdit = new HashMap<>();
            dataToEdit.put("name",name.getText());
            dataToEdit.put("company",company.getText());
            dataToEdit.put("price",price.getText());
            dataToEdit.put("availability",quantity.getText());
            dataToEdit.put("description",description.getText());
            try {
                SellerController.editProductRequest(Integer.parseInt(productId.getText()),dataToEdit);
                AlertBoxStart.messageRun("Message","Request sent!");
                close();
            } catch (ExceptionsLibrary.NoProductException | ExceptionsLibrary.NoFeatureWithThisName | ExceptionsLibrary.CannotChangeThisFeature e) {
                ErrorBoxStart.errorRun(e);
            }
        }
    }

    public void close() {
        setNewProduct(false);
        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        category.setCellFactory(
                new Callback<ListView<Category >, ListCell<Category >>() {
                    @Override
                    public ListCell<Category > call(ListView<Category > p) {
                        ListCell cell = new ListCell<Category >() {
                            @Override
                            protected void updateItem(Category item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setText("");
                                } else {
                                    setText(item.getName());
                                }
                            }
                        };
                        return cell;
                    }
                });
        category.setButtonCell(
                new ListCell<Category>() {
                    @Override
                    protected void updateItem(Category t, boolean bln) {
                        super.updateItem(t, bln);
                        if (bln) {
                            setText("");
                        } else {
                            setText(t.getName());
                        }
                    }
                });

        productId.setDisable(true);
        try {
            category.getItems().addAll(AdminController.showCategories());
        } catch (ExceptionsLibrary.NoCategoryException e) {
            e.printStackTrace();
        }
        if (isNewProduct()) {

            category.valueProperty().addListener((v, oldValue, newValue) -> {
                Category newValueCategory = (Category) newValue;
                for (Feature i : newValueCategory.getFeatures()) {
                    TextField feature = new TextField();
                    feature.setPromptText("Value for " + i.getParameter());
                    feature.setId(i.getParameter());
                    features.getChildren().add(feature);
                }
            });
        }
        else {
            for (Feature i : getEditProduct().getCategoryFeatures()) {
                TextField feature = new TextField();
                feature.setText(i.getParameterValue());
                feature.setId(i.getParameterValue());
                features.getChildren().add(feature);
            }
            productId.setText(String.valueOf(editProduct.getProductId()));
            name.setText(editProduct.getName());
            company.setText(editProduct.getCompany());
            price.setText(String.valueOf(editProduct.getPrice()));
            quantity.setText(String.valueOf(editProduct.getAvailability()));
            category.setDisable(true);
            description.setText(editProduct.getDescription());
        }


    }

}
