package Client.ClientView.UserPanel.ManageSellerOffs.NewEditOffRequest;

import Client.ClientController.SellerController;
import Client.ClientView.AlertBox.ErrorBox.ErrorBoxStart;
import Client.ClientView.AlertBox.MessageBox.AlertBoxStart;
import Client.ClientView.MainMenuStage.CheckFields;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import LocalExceptions.ExceptionsLibrary;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;

public class NewEditOffRequest implements Initializable {

    @FXML
    private TextField offId;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private TextField offAmount;
    @FXML
    private TextField products;
    @FXML
    private Label productsLabel;

    private static boolean newOff;
    private static Off editOff;

    public static boolean isNewOff() {
        return newOff;
    }

    public static void setNewOff(boolean newOff) {
        NewEditOffRequest.newOff = newOff;
    }

    public static Off getEditOff() {
        return editOff;
    }

    public static void setEditOff(Off editOff) {
        NewEditOffRequest.editOff = editOff;
    }

    public void sendRequestButtonClicked() {
        try {
            CheckFields.checkField("double",offAmount.getText());
        } catch (ExceptionsLibrary.NotAcceptableFormatInput notAcceptableFormatInput) {
            ErrorBoxStart.errorRun(notAcceptableFormatInput);
            return;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDateString = startDate.getValue().format(dateTimeFormatter);
        String endDateString = endDate.getValue().format(dateTimeFormatter);
        startDateString = startDateString + " 00:00";
        endDateString = endDateString + " 00:00";
        if (isNewOff()) {
            Off off = new Off(null, ProductOrOffCondition.PENDING_TO_CREATE, startDateString, endDateString, Double.parseDouble(offAmount.getText()));
            try {
                SellerController.addOffRequest(off, products.getText());
                AlertBoxStart.messageRun("Message","Request sent!");
                close();
            } catch (ExceptionsLibrary.NoProductException e) {
                ErrorBoxStart.errorRun(e);
            }
        }
        else {
            HashMap<String, String> dataToEdit = new HashMap<>();
            dataToEdit.put("startDate",startDateString);
            dataToEdit.put("endDate",endDateString);
            dataToEdit.put("offAmount",offAmount.getText());
            try {
                SellerController.editOffRequest(Integer.parseInt(offId.getText()),dataToEdit);
                AlertBoxStart.messageRun("Message","Request sent!");
                close();
            } catch (ExceptionsLibrary.NoOffException | ExceptionsLibrary.NoFeatureWithThisName | ExceptionsLibrary.CannotChangeThisFeature e) {
                ErrorBoxStart.errorRun(e);
            }
        }
    }

    public void close() {
        Stage stage = (Stage) offId.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        offId.setDisable(true);

        if (!isNewOff()) {
            offId.setText(String.valueOf(editOff.getOffId()));
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDate startDateLocalDate = LocalDate.parse(getEditOff().getStartDate(), dateTimeFormatter);
            LocalDate endDateLocalDate = LocalDate.parse(getEditOff().getEndDate(), dateTimeFormatter);
            this.startDate.setValue(startDateLocalDate);
            this.endDate.setValue(endDateLocalDate);
            offAmount.setText(String.valueOf(editOff.getOffAmount()));
            products.setVisible(false);
            productsLabel.setVisible(false);
        }


    }

}
