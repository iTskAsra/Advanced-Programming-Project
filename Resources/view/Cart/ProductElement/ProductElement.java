package view.Cart.ProductElement;

import controller.ExceptionsLibrary;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.Category;
import model.Product;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductElement extends VBox implements Initializable {

    @FXML
    private ImageView image;
    @FXML
    private Label name;
    @FXML
    private Label quantity;
    @FXML
    private Label priceForEach;
    @FXML
    private Label totalPriceForProduct;
    @FXML
    private Button increase;
    @FXML
    private Button decrease;


    private static int productId;

    private static int quantityNumber;



    public static int getProductId() {
        return productId;
    }

    public static void setProductId(int productId) {
        ProductElement.productId = productId;
    }

    public static int getQuantityNumber() {
        return quantityNumber;
    }

    public static void setQuantityNumber(int quantityNumber) {
        ProductElement.quantityNumber = quantityNumber;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Product product = GetDataFromDatabase.getProduct(productId);
            image.setImage(GetProductImage(product.getCategory()));
            name.setText(product.getName());
            quantity.setText(String.valueOf(getQuantityNumber()));
            priceForEach.setText(String.valueOf(product.getPriceWithOff()));
            totalPriceForProduct.setText(String.valueOf(quantityNumber*product.getPriceWithOff()));
        } catch (ExceptionsLibrary.NoProductException exception) {
            exception.printStackTrace();
        }
    }


    private Image GetProductImage(Category category) {
        File file = new File("Resources/Category/" + category.getName() + ".jpg");
        Image image = null;
        try {
            image = new Image(new FileInputStream(file), 300, 300, true, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return image;
    }

}
