package Client.ClientView.UserPanel.EditInfo;

import Client.ClientController.AdminController;
import Client.ClientController.CustomerController;
import Client.ClientController.ExceptionsLibrary;
import Client.ClientController.SellerController;
import Client.ClientView.AlertBox.ErrorBox.ErrorBoxStart;
import Client.ClientView.AlertBox.MessageBox.AlertBoxStart;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Admin;
import model.Customer;
import model.Seller;
import Client.ClientView.MainMenuStage.Main;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class EditInfo implements Initializable {

    @FXML
    private Button close;
    @FXML
    private Button edit;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private TextField phoneNumber;
    @FXML
    private PasswordField password;
    @FXML
    private TextField company;
    @FXML
    private Label companyLabel;

    public void close() {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    public void editInfo() {
        if (Main.checkLoggedIn().equals("Customer")) {
            HashMap<String, String> dataToEdit = new HashMap<>();
            dataToEdit.put("firstName", firstName.getText());
            dataToEdit.put("lastName", lastName.getText());
            dataToEdit.put("email", email.getText());
            dataToEdit.put("phoneNumber", phoneNumber.getText());
            dataToEdit.put("password", password.getText());
            try {
                checkFields();
                CustomerController.editCustomerInfo(dataToEdit);
                AlertBoxStart.messageRun("Message", "Edited Successfully!");
                close();
            } catch (ExceptionsLibrary.NoAccountException | ExceptionsLibrary.NoFeatureWithThisName | ExceptionsLibrary.ChangeUsernameException | ExceptionsLibrary.NotAcceptableFormatInput e) {
                ErrorBoxStart.errorRun(e);
            }
        } else if (Main.checkLoggedIn().equals("Seller")) {
            HashMap<String, String> dataToEdit = new HashMap<>();
            dataToEdit.put("firstName", firstName.getText());
            dataToEdit.put("lastName", lastName.getText());
            dataToEdit.put("email", email.getText());
            dataToEdit.put("phoneNumber", phoneNumber.getText());
            dataToEdit.put("password", password.getText());
            dataToEdit.put("companyName", company.getText());
            try {
                checkFields();
                SellerController.editSellerInfo(dataToEdit);
                AlertBoxStart.messageRun("Message", "Edited Successfully!");
                close();
            } catch (ExceptionsLibrary.NoAccountException | ExceptionsLibrary.NoFeatureWithThisName | ExceptionsLibrary.ChangeUsernameException | ExceptionsLibrary.NotAcceptableFormatInput e) {
                ErrorBoxStart.errorRun(e);
            }
        }
        else {
            HashMap<String, String> dataToEdit = new HashMap<>();
            dataToEdit.put("firstName", firstName.getText());
            dataToEdit.put("lastName", lastName.getText());
            dataToEdit.put("email", email.getText());
            dataToEdit.put("phoneNumber", phoneNumber.getText());
            dataToEdit.put("password", password.getText());
            try {
                checkFields();
                AdminController.editAdminInfo(dataToEdit);
                AlertBoxStart.messageRun("Message", "Edited Successfully!");
                close();
            } catch (ExceptionsLibrary.NoAccountException | ExceptionsLibrary.NoFeatureWithThisName | ExceptionsLibrary.ChangeUsernameException | ExceptionsLibrary.NotAcceptableFormatInput e) {
                ErrorBoxStart.errorRun(e);
            }
        }
    }

    private void checkFields() throws ExceptionsLibrary.NotAcceptableFormatInput {
        if (!(email.getText().matches("\\w+@\\w+.\\w+") && phoneNumber.getText().matches("\\d+"))) {
            throw new ExceptionsLibrary.NotAcceptableFormatInput();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Main.checkLoggedIn().equals("Customer")) {
            Customer customer = CustomerController.getCustomer();
            companyLabel.setVisible(false);
            company.setVisible(false);
            firstName.setText(customer.getFirstName());
            lastName.setText(customer.getLastName());
            email.setText(customer.getEmail());
            phoneNumber.setText(customer.getPhoneNumber());
            password.setText(customer.getPassword());
        } else if (Main.checkLoggedIn().equals("Seller")) {
            Seller seller = SellerController.getSeller();
            companyLabel.setVisible(true);
            company.setVisible(true);
            firstName.setText(seller.getFirstName());
            lastName.setText(seller.getLastName());
            email.setText(seller.getEmail());
            phoneNumber.setText(seller.getPhoneNumber());
            password.setText(seller.getPassword());
            company.setText(seller.getCompanyName());
        } else if (Main.checkLoggedIn().equals("Admin")){
            Admin admin = AdminController.getAdmin();
            companyLabel.setVisible(false);
            company.setVisible(false);
            firstName.setText(admin.getFirstName());
            lastName.setText(admin.getLastName());
            email.setText(admin.getEmail());
            phoneNumber.setText(admin.getPhoneNumber());
            password.setText(admin.getPassword());
        }
    }
}
