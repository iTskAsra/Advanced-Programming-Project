package view.UserPanel.BuyLog.BuyLogDetails;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.BuyLog;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class BuyLogDetailsStart {
    public static void start(BuyLog buyLog) throws IOException {
        BuyLogDetails.setBuyLog(buyLog);
        Stage stage = new Stage();
        File file = new File("src/main/java/view/UserPanel/BuyLog/BuyLogDetails/BuyLogDetails.fxml");
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
