package Client.ClientView.RegisterAndLoginStage;

import Client.ClientController.ExceptionsLibrary;
import Client.ClientController.RegisterAndLoginController;
import Client.ClientView.AlertBox.ErrorBox.ErrorBoxStart;
import Client.ClientView.RegisterAndLoginStage.Register.RegisterScene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Client.ClientView.AlertBox.MessageBox.AlertBoxStart;
import view.Base.Main;



import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;


public class RegisterAndLogin {

    @FXML
    private Button close;
    @FXML
    private Button register;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    public void close(){
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    public void registerButtonClicked() throws IOException {
        Stage stage = (Stage) close.getScene().getWindow();
        File file = new File("src/main/java/Client/ClientView/RegisterAndLoginStage/Register/RegisterScene.fxml");
        URL url = file.toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void loginButtonClicked() throws IOException {
        HashMap<String,String> dataToSend = new HashMap();
        dataToSend.put("username",username.getText());
        dataToSend.put("password", password.getText());
        try {
            RegisterAndLoginController.login(dataToSend);
            AlertBoxStart.messageRun("Message", "Successfully Logged In!");
            Main.checkLoggedIn();
            close();
        }
        catch (ExceptionsLibrary.WrongPasswordException | ExceptionsLibrary.WrongUsernameException | ExceptionsLibrary.NoAccountException e){
            ErrorBoxStart.errorRun(e);
        }
    }

}
