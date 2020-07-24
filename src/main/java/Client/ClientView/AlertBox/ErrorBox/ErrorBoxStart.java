package Client.ClientView.AlertBox.ErrorBox;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import LocalExceptions.ExceptionsLibrary;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ErrorBoxStart {
    public static void errorRun(Exception e) {
        AlertBox.setException(e);
        Stage stage = new Stage();
        File file = new File("src/main/java/Client/ClientView/AlertBox/ErrorBox/AlertBox.fxml");
        URL url = null;
        try {
            url = file.toURI().toURL();
        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        }
        Parent root = null;
        try {
            root = FXMLLoader.load(url);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.showAndWait();
    }
}
