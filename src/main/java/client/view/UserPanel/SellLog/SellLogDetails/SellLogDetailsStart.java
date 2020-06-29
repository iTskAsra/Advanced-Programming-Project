package client.view.UserPanel.SellLog.SellLogDetails;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.SellLog;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SellLogDetailsStart {
    public static void start(SellLog sellLog) throws IOException {
        SellLogDetails.setSellLog(sellLog);
        Stage stage = new Stage();
        File file = new File("src/main/java/view/UserPanel/SellLog/SellLogDetails/SellLogDetails.fxml");
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
