package Client.ClientView.MainMenuStage;

import Client.Client;
import controller.ExceptionsLibrary;
import controller.GetDataFromDatabase;
import controller.SetPeriodicSales;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.RegisterAndLoginStage.Register.RegisterScene;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;

public class MainMenu extends Application {


    public MainMenu() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        Client.run();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        GetDataFromDatabase.setResources();
        try {
            SetPeriodicSales.setPeriodicSales();
            SetPeriodicSales.removeExpiredOff();
        } catch (ExceptionsLibrary.NoAccountException | ExceptionsLibrary.NoProductException | ParseException | ExceptionsLibrary.NoOffException e) {
            e.printStackTrace();
        }
        if (adminNotExists()) {
            RegisterScene.setAdmin(true);
            Stage stage = new Stage();
            File file = new File("src/main/java/view/RegisterAndLoginStage/Register/RegisterScene.fxml");
            URL url = file.toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.showAndWait();
        }
        File file = new File("src/main/java/view/MainMenuStage/MainMenu.fxml");
        URL url = file.toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean adminNotExists() {
        File file = new File("Resources/Accounts/Admin");
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File file1) {
                if (file1.getName().endsWith(".json")) {
                    return true;
                }
                return false;
            }
        };
        boolean exist = true;
        for (File i : file.listFiles(fileFilter)) {
            exist = false;
        }
        return exist;
    }
}
