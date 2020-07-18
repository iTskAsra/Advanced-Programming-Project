package Client.ClientView.AlertBox.ErrorBox;

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
    private static Exception exception;

    public static Exception getException() {
        return exception;
    }

    public static void setException(Exception exception) {
        AlertBox.exception = exception;
    }

    public void close(){
        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageLabel.setText(getException().getMessage());
        messageLabel.setWrapText(true);
    }
}
