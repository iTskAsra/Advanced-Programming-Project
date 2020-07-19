package Client.ClientView.ProductPage;

import Client.ClientController.CustomerController;
import Client.ClientController.ExceptionsLibrary;
import Client.ClientController.GetDataFromDatabase;
import Client.ClientController.ProductPageController;
import Client.ClientView.AlertBox.ErrorBox.ErrorBoxStart;
import Client.ClientView.AlertBox.MessageBox.AlertBoxStart;
import Client.ClientView.MainMenuStage.CheckFields;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.*;
import view.Base.Main;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductPage implements Initializable {

    @FXML
    private Label productId;
    @FXML
    private Label name;
    @FXML
    private Label price;
    @FXML
    private Label company;
    @FXML
    private Label rates;
    @FXML
    private Label category;
    @FXML
    private Label description;
    @FXML
    private VBox features;
    @FXML
    private TextField rateTextField;
    @FXML
    private Button rateButton;
    @FXML
    private Label rateRatePage;
    @FXML
    private TextField commentTitle;
    @FXML
    private TextArea commentText;
    @FXML
    private Button submitButton;
    @FXML
    private VBox comments;
    @FXML
    private Button addToCart;
    @FXML
    private Button compare;
    @FXML
    private Button close;
    @FXML
    private Button account;
    @FXML
    private Label title;
    @FXML
    private ImageView image;
    @FXML
    private Label rateLabel;
    @FXML
    private Accordion accordion;
    @FXML
    private TitledPane digest;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        rateTextField.setVisible(false);
        rateButton.setVisible(false);
        rateLabel.setVisible(false);

        Product product = ProductPageController.getProduct();
        title.setText(product.getName());
        image.setImage(GetProductImage(product.getCategory()));
        productId.setText(String.valueOf(product.getProductId()));
        name.setText(product.getName());
        if (product.getPrice() != product.getPriceWithOff()) {
            price.setText(String.valueOf(product.getPriceWithOff()) + " IN OFF!");
            price.setStyle("-fx-text-fill: firebrick");
        }
        else {
            price.setText(String.valueOf(product.getPriceWithOff()));
        }
        company.setText(product.getCompany());
        if (product.getRates().size() == 0){
            rates.setText("Not rated yet!");
        }
        else {
            rates.setText(calculateRate(product.getRates()));
        }
        category.setText(product.getCategory().getName());
        description.setText(product.getDescription());
        for (Feature i : product.getCategoryFeatures()){
            HBox hBox = new HBox(70);
            hBox.setAlignment(Pos.CENTER);
            Label title = new Label(i.getParameter()+ ":");
            Label value = new Label(i.getParameterValue());
            title.getStyleClass().add("infoTitle");
            value.getStyleClass().add("infoValue");
            hBox.getChildren().addAll(title,value);
            hBox.getStyleClass().add("featureHBox");
            features.getChildren().add(hBox);
        }
        if (product.getRates().size() == 0){
            rateRatePage.setText("Not rated yet!");
        }
        else {
            rateRatePage.setText(calculateRate(product.getRates())+"/5");
        }
        if (Main.checkLoggedIn() != null && Main.checkLoggedIn().equals("Customer")) {
            if (ProductPageController.isBoughtByCommenter(CustomerController.getCustomer(),product)){
                rateTextField.setVisible(true);
                rateButton.setVisible(true);
                rateLabel.setVisible(true);
            }
        }

        rateButton.setOnAction( e -> {
            try {
                try {
                    CheckFields.checkField("double",rateTextField.getText());
                } catch (ExceptionsLibrary.NotAcceptableFormatInput notAcceptableFormatInput) {
                    ErrorBoxStart.errorRun(notAcceptableFormatInput);
                    return;
                }
                CustomerController.rateProduct(product.getProductId(), Double.parseDouble(rateTextField.getText()));
                AlertBoxStart.messageRun("Message", "Rate successful!");
            } catch (ExceptionsLibrary.NoProductException exception) {
                ErrorBoxStart.errorRun(exception);
            }
        });

        submitButton.setOnAction( e -> {
            try {
                ProductPageController.addComment(commentTitle.getText(),commentText.getText());
                AlertBoxStart.messageRun("Message", "Added comment successfully!");
            } catch (ExceptionsLibrary.NotLoggedInException | ExceptionsLibrary.NoAccountException | ExceptionsLibrary.NoProductException exception) {
                ErrorBoxStart.errorRun(exception);
            }
        });


        for (Comment i : product.getProductComments()){
            VBox vBox = new VBox(10);
            vBox.setAlignment(Pos.CENTER);
            Label sender = new Label("Sender:" + i.getCommenterAccount());
            Label title = new Label("Title:" + i.getCommentTitle());
            Label text = new Label("Text:" + i.getCommentText());
            sender.getStyleClass().add("infoTitle");
            title.getStyleClass().add("infoValue");
            title.setStyle("-fx-text-fill: darkgreen");
            text.getStyleClass().add("commentText");
            vBox.getChildren().addAll(sender, title, text);
            vBox.getStyleClass().add("commentsVBoxStyle");
            comments.getChildren().add(vBox);
        }

        if (Main.statusProperty().getValue() != null) {
            account.setText("Account");
            account.setOnAction(e -> {
                try {
                    accountButtonClickedLoggedIn();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
        } else {
            account.setText("Login");
            account.setOnAction(e -> {
                try {
                    accountButtonClickedNotLoggedIn();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
        }
        Main.statusProperty().addListener((v, oldValue, newValue) -> {
            if (newValue != null) {
                account.setText("Account");
                account.setOnAction(e -> {
                    try {
                        accountButtonClickedLoggedIn();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });
            } else {
                account.setText("Login");
                account.setOnAction(e -> {
                    try {
                        accountButtonClickedNotLoggedIn();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });
            }
        });

        accordion.setExpandedPane(digest);

    }

    public void compareButtonClicked() throws IOException {
        Stage stage = new Stage();
        File file = new File("src/main/java/view/ProductPage/Compare/Compare.fxml");
        URL url = file.toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    public void addToCartButtonClicked(){
        try {
            ProductPageController.selectSeller(GetDataFromDatabase.findSellersFromProductId(ProductPageController.getProduct().getProductId()).get(0).getUsername());
            ProductPageController.addToCart();
            AlertBoxStart.messageRun("Message","Added to cart!");
        } catch (ExceptionsLibrary.SelectASeller | ExceptionsLibrary.NotEnoughNumberAvailableException | ExceptionsLibrary.NoAccountException e) {
            ErrorBoxStart.errorRun(e);
        }
    }

    public void accountButtonClickedNotLoggedIn() throws IOException {
        Stage stage = new Stage();
        File file = new File("src/main/java/view/RegisterAndLoginStage/RegisterAndLogin.fxml");
        URL url = file.toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    public void accountButtonClickedLoggedIn() throws IOException {
        if (!Main.checkLoggedIn().equals("Admin")) {
            Stage stage = (Stage) Stage.getWindows().get(0);
            File file = new File("src/main/java/view/UserPanel/UserPanel.fxml");
            URL url = file.toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } else {
            Stage stage = (Stage) Stage.getWindows().get(0);
            File file = new File("src/main/java/view/UserPanel/AdminPanel/AdminPanel.fxml");
            URL url = file.toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }
        closeButtonClicked();
    }

    public void closeButtonClicked() {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    private String calculateRate(ArrayList<Rate> rates) {
        double rateSum = 0.00;
        for (Rate i : rates){
            rateSum += i.getRateScore();
        }
        rateSum = rateSum / rates.size();
        return String.valueOf(rateSum);
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
