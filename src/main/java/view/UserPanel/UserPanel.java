package view.UserPanel;

import controller.AdminController;
import controller.CustomerController;
import controller.SellerController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Customer;
import model.Seller;
import view.Base.Main;
import view.HelpWindow.Help;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserPanel implements Initializable {
    @FXML
    private Button back;
    @FXML
    private Label name;
    @FXML
    private Label username;
    @FXML
    private Label role;
    @FXML
    private Label email;
    @FXML
    private Label phoneNumber;
    @FXML
    private Label password;
    @FXML
    private Label balance;
    @FXML
    private Button editInfoButton;
    @FXML
    private Label editInfoLabel;
    @FXML
    private Button buySellLogButton;
    @FXML
    private Label buySellLogLabel;
    @FXML
    private Button discountOrOffButton;
    @FXML
    private Label discountOrOffLabel;
    @FXML
    private Button cartButton;
    @FXML
    private Label cartLabel;
    @FXML
    private Button requests;



    public void back() throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        File file = new File("src/main/java/view/MainMenuStage/MainMenu.fxml");
        URL url = file.toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root, 1920, 1080);
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateScene();
    }

    public void helpButtonClicked() throws IOException {
        if (Main.checkLoggedIn().equals("Customer")) {
            Help.setMessage("Here is the Menu for Customers\nYou can see the personal info, cart, orders, balance and discount codes in this menu.\n");
        }
        else {
            Help.setMessage("Here is the panel for Sellers.\nYou can see the personal or company information, view sales history, manage, add or remove products and also you can see categories, offs or balances.\n");
        }
        Stage stage = new Stage();
        File file = new File("src/main/java/view/HelpWindow/Help.fxml");
        URL url = file.toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    public void logout() throws IOException {
        if (Main.checkLoggedIn() != null){
            if (CustomerController.getCustomer() != null){
                CustomerController.setCustomer(null);
            }
            else if (SellerController.getSeller() != null){
                SellerController.setSeller(null);
            }
            else if (AdminController.getAdmin() != null){
                AdminController.setAdmin(null);
            }
            Main.setStatus(null);
            back();
        }
    }

    public void editInfoButtonClicked() throws IOException {
        Stage stage = new Stage();
        File file = new File("src/main/java/view/UserPanel/EditInfo/EditInfo.fxml");
        URL url = file.toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.showAndWait();
        updateScene();
    }

    public void buySellLogButtonClicked() throws IOException {
        if (Main.checkLoggedIn().equals("Customer")) {
            Stage stage = new Stage();
            File file = new File("src/main/java/view/UserPanel/BuyLog/BuyLog.fxml");
            URL url = file.toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.showAndWait();
        }
        else {
            Stage stage = new Stage();
            File file = new File("src/main/java/view/UserPanel/SellLog/SellLog.fxml");
            URL url = file.toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.showAndWait();
        }
    }

    public void discountOrOffButtonClicked() throws IOException {
        if (Main.checkLoggedIn().equals("Customer")) {
            Stage stage = new Stage();
            File file = new File("src/main/java/view/UserPanel/DiscountCodes/DiscountCodes.fxml");
            URL url = file.toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.showAndWait();
        }
        else {
            Stage stage = new Stage();
            File file = new File("src/main/java/view/UserPanel/ManageSellerOffs/ManageSellerOffs.fxml");
            URL url = file.toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.showAndWait();
        }
    }

    private void updateScene() {
        if (Main.checkLoggedIn().equals("Customer")) {
            Customer customer = CustomerController.getCustomer();
            name.setText("Welcome " + customer.getFirstName() + " " + customer.getLastName() + "!");
            username.setText(customer.getUsername());
            role.setText(customer.getRole());
            email.setText(customer.getEmail());
            phoneNumber.setText(customer.getPhoneNumber());
            password.setText(customer.getPassword());
            balance.setText("Your current balance is: " + customer.getCredit());
            requests.setVisible(false);
        }
        else {
            Seller seller = SellerController.getSeller();
            name.setText("Welcome " + seller.getFirstName() + " " + seller.getLastName() + "!");
            username.setText(seller.getUsername());
            role.setText(seller.getRole()+"       Company:"+seller.getCompanyName());
            email.setText(seller.getEmail());
            phoneNumber.setText(seller.getPhoneNumber());
            password.setText(seller.getPassword());
            balance.setText("Your current balance is: " + seller.getCredit());
            buySellLogLabel.setText("Sell Log");
            buySellLogButton.setText("Sell Log");
            cartLabel.setText("Manage Products");
            cartButton.setText("Manage Products");
            discountOrOffLabel.setText("Manage Offs");
            discountOrOffButton.setText("Manage Offs");
        }
    }

    public void cartButtonClicked() throws IOException {
        if (Main.checkLoggedIn().equals("Customer")) {

        }
        else {
            Stage stage = new Stage();
            File file = new File("src/main/java/view/UserPanel/ManageSellerProducts/ManageSellerProducts.fxml");
            URL url = file.toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        }
    }

    public void requests() throws IOException {
        Stage stage = new Stage();
        File file = new File("src/main/java/view/UserPanel/RequestsStatus/RequestsStatus.fxml");
        URL url = file.toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

}
