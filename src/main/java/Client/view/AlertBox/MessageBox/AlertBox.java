package Client.view.AlertBox.MessageBox;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AlertBox implements Initializable {
    @FXML
    private Button ok;
    @FXML
    private Label messageLabel;
    @FXML
    private Label title;
    private static String alertTitle;
    private static String message;

    public static String getAlertTitle() {
        return alertTitle;
    }

    public static void setAlertTitle(String alertTitle) {
        AlertBox.alertTitle = alertTitle;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        AlertBox.message = message;
    }

    public void close(){
        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title.setText(getAlertTitle());
        messageLabel.setText(getMessage());
        messageLabel.setWrapText(true);
    }
}
