package Client.view.RegisterAndLoginStage.Register;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.AdminController;
import controller.ExceptionsLibrary;
import controller.RegisterAndLogin;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Admin;
import model.Customer;
import model.Seller;
import view.AlertBox.ErrorBox.ErrorBoxStart;
import view.AlertBox.MessageBox.AlertBoxStart;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegisterScene implements Initializable {
    @FXML
    private ChoiceBox accountType;
    @FXML
    private Button registerButton;
    @FXML
    private TextField username;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField credit;
    @FXML
    private PasswordField password;
    @FXML
    private Button close;
    @FXML
    private TextField company;
    @FXML
    private Label companyLabel;

    private static boolean admin;

    public static boolean isAdmin() {
        return admin;
    }

    public static void setAdmin(boolean admin) {
        RegisterScene.admin = admin;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accountType.getItems().addAll("Customer", "Seller", "Admin");
        if (!isAdmin()) {
            accountType.setValue("Customer");
        } else {
            accountType.setValue("Admin");
            accountType.setDisable(true);
            setAdmin(false);
        }
        company.setVisible(false);
        companyLabel.setVisible(false);
        accountType.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue.equals("Seller")) {
                company.setVisible(true);
                companyLabel.setVisible(true);
            } else {
                company.setVisible(false);
                companyLabel.setVisible(false);
            }
        });
    }

    public void registerButtonClicked() throws IOException {
        try {
            checkInputs();
        } catch (ExceptionsLibrary.NotAcceptableFormatInput notAcceptableFormatInput) {
            ErrorBoxStart.errorRun(notAcceptableFormatInput);
            return;
        }
        if (accountType.getValue().equals("Customer")) {
            Customer customer = new Customer(username.getText(), password.getText(), (String) accountType.getValue(), firstName.getText(), lastName.getText(), email.getText(), phoneNumber.getText(), new ArrayList<>(), Double.parseDouble(credit.getText()), new ArrayList<>());
            Gson gson = new GsonBuilder().serializeNulls().create();
            String data = gson.toJson(customer);
            try {
                RegisterAndLogin.register(data);
                close();
                AlertBoxStart.messageRun("Message", "Successfully Registered!");
            } catch (ExceptionsLibrary.AdminExist | ExceptionsLibrary.UsernameAlreadyExists e) {
                ErrorBoxStart.errorRun(e);
            }
        } else if (accountType.getValue().equals("Seller")){
            Seller seller = new Seller(username.getText(), password.getText(), (String) accountType.getValue(), firstName.getText(), lastName.getText(), email.getText(), phoneNumber.getText(), new ArrayList<>(), Double.parseDouble(credit.getText()), company.getText(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            Gson gson = new GsonBuilder().serializeNulls().create();
            String data = gson.toJson(seller);
            try {
                RegisterAndLogin.register(data);
                close();
                AlertBoxStart.messageRun("Message", "Request Sent!");
            } catch (ExceptionsLibrary.AdminExist | ExceptionsLibrary.UsernameAlreadyExists e) {
                ErrorBoxStart.errorRun(e);
            }
        } else {
            Admin admin = new Admin(username.getText(), password.getText(), (String) accountType.getValue(), firstName.getText(), lastName.getText(), email.getText(), phoneNumber.getText(), new ArrayList<>(), Double.parseDouble(credit.getText()), new ArrayList<>());
            Gson gson = new GsonBuilder().serializeNulls().create();
            String data = gson.toJson(admin);
            try {
                AdminController.addAdminAccount(data);
                close();
                AlertBoxStart.messageRun("Message", "Successfully Registered!");
            } catch (ExceptionsLibrary.UsernameAlreadyExists e) {
                ErrorBoxStart.errorRun(e);
            }
        }

    }

    private void checkInputs() throws ExceptionsLibrary.NotAcceptableFormatInput {
        boolean emailCheck = email.getText().matches("\\w+@\\w+.\\w+");
        boolean phoneNumberCheck = phoneNumber.getText().matches("\\d+");
        boolean creditCheck = credit.getText().matches("\\d+");
        boolean result = emailCheck && phoneNumberCheck && creditCheck;
        if (!result){
            throw new ExceptionsLibrary.NotAcceptableFormatInput();
        }
    }



    public void close() {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

}
