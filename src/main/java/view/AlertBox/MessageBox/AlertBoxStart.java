package view.AlertBox.MessageBox;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AlertBoxStart {
    public static void messageRun(String title,String message) throws IOException {
        AlertBox.setAlertTitle(title);
        AlertBox.setMessage(message);
        Stage stage = new Stage();
        File file = new File("src/main/java/view/AlertBox/MessageBox/AlertBox.fxml");
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
