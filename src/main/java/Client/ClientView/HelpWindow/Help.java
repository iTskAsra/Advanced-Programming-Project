package Client.ClientView.HelpWindow;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Help implements Initializable {
    @FXML
    private Button ok;
    @FXML
    private Label messageLabel;
    private static String message;

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        Help.message = message;
    }

    public void close(){
        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageLabel.setText(getMessage());
        messageLabel.setWrapText(true);
    }
}
