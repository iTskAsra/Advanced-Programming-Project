package Client.ClientView.MainMenuStage;


import Client.ClientController.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.Base.Main;
import Client.ClientView.HelpWindow.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    private Button help;
    @FXML
    private Button account;
    @FXML
    private Button logout;
    @FXML
    private Button exit;

    public void helpButtonClicked() throws IOException {
        Help.setMessage("Here you can login and logout,\nview your account details,\nsearch for products and offs,\nand see your cart\n");
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
            Stage stage = (Stage) account.getScene().getWindow();
            File file = new File("src/main/java/view/UserPanel/UserPanel.fxml");
            URL url = file.toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } else {
            Stage stage = (Stage) account.getScene().getWindow();
            File file = new File("src/main/java/view/UserPanel/AdminPanel/AdminPanel.fxml");
            URL url = file.toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }
    }

    public void productsButtonClicked() throws IOException {
        Stage stage = (Stage) account.getScene().getWindow();
        File file = new File("src/main/java/Client/ClientView/AllProducts/AllProducts.fxml");
        URL url = file.toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void offsButtonClicked() throws IOException {
        Stage stage = (Stage) account.getScene().getWindow();
        File file = new File("src/main/java/view/OffPanel/OffPanel.fxml");
        URL url = file.toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void exitButtonClicked() {
        System.exit(0);
    }

    public void logout() throws IOException {
        if (Main.checkLoggedIn() != null) {
            if (CustomerController.getCustomer() != null) {
                CustomerController.setCustomer(null);
            } else if (SellerController.getSeller() != null) {
                SellerController.setSeller(null);
            } else if (AdminController.getAdmin() != null) {
                AdminController.setAdmin(null);
            }
            Main.setStatus(null);
        }
    }

    public void cartButtonClicked() throws IOException {
        Stage stage = new Stage();
        File file = new File("src/main/java/view/Cart/Cart.fxml");
        URL url = file.toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Main.statusProperty().getValue() != null) {
            account.setText("Account");
            account.setOnAction(e -> {
                try {
                    accountButtonClickedLoggedIn();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            logout.setVisible(true);
        } else {
            logout.setVisible(false);
            account.setText("Login/Signup");
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
                logout.setVisible(true);
            } else {
                account.setText("Login/Signup");
                account.setOnAction(e -> {
                    try {
                        accountButtonClickedNotLoggedIn();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });
                logout.setVisible(false);
            }
        });
    }


}
