package view.UserPanel.AdminPanel.Categories.NewEditCategories;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.AdminController;
import controller.ExceptionsLibrary;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Category;
import model.Feature;
import view.AlertBox.ErrorBox.ErrorBoxStart;
import view.AlertBox.MessageBox.AlertBoxStart;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class NewEditCategories implements Initializable {

    @FXML
    private Button picture;
    @FXML
    private TextField name;
    @FXML
    private TextField features;
    @FXML
    private Label address;
    @FXML
    private Button addCategory;

    private static boolean newCategory;
    private static Category editCategory;

    public static boolean isNewCategory() {
        return newCategory;
    }

    public static void setNewCategory(boolean newCategory) {
        NewEditCategories.newCategory = newCategory;
    }

    public static Category getEditCategory() {
        return editCategory;
    }

    public static void setEditCategory(Category editCategory) {
        NewEditCategories.editCategory = editCategory;
    }

    FileChooser fileChooser = new FileChooser();
    File file;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        address.setVisible(false);
        fileChooser.setTitle("Choose Picture");
        if (!isNewCategory()) {
            name.setText(getEditCategory().getName());
            String featuresString = "";
            for (Feature i : getEditCategory().getFeatures()) {
                featuresString = featuresString + " , " + i.getParameter();
                addCategory.setText("Edit Category");
            }
            features.setText(featuresString.substring(2));

        }
    }

    public void pictureSelect() {
        file = fileChooser.showOpenDialog(picture.getScene().getWindow());
        address.setText(file.getName());
        address.setVisible(true);
    }

    public void addCategory() {
        if (!(AdminController.checkCategoryName(name.getText()) && isNewCategory())) {
            if (isNewCategory() || file != null) {
                Path source = file.toPath();
                Path destination = Paths.get("Resources/Category/" + name.getText() + ".jpg");
                try {
                    Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String[] featuresList = features.getText().split("\\s*,\\s*");
            ArrayList<Feature> categoryFeatures = new ArrayList<>();
            for (String i : featuresList) {
                Feature feature = new Feature(i, null);
                categoryFeatures.add(feature);
            }
            Category category = new Category(name.getText(), categoryFeatures, null);
            Gson gson = new GsonBuilder().serializeNulls().create();
            String data = gson.toJson(category);
            try {
                if (isNewCategory()){
                    setNewCategory(false);
                    AdminController.addCategory(data);
                    AlertBoxStart.messageRun("Message", "Added Category!");
                    close();
                }
                else {
                    setNewCategory(false);
                    HashMap<String, String> dataToEdit = new HashMap<>();
                    dataToEdit.put("name",name.getText());
                    dataToEdit.put("features",features.getText());
                    setEditCategory(null);
                    try {
                        AdminController.editCategory(name.getText(),dataToEdit);
                        AlertBoxStart.messageRun("Message", "Edited Category!");
                        close();
                    } catch (ExceptionsLibrary.NoCategoryException | ExceptionsLibrary.NoFeatureWithThisName | ExceptionsLibrary.NoAccountException | ExceptionsLibrary.NoProductException e) {
                        ErrorBoxStart.errorRun(e);
                    }
                }
            } catch (ExceptionsLibrary.CategoryExistsWithThisName e) {
                ErrorBoxStart.errorRun(e);
            }

        } else {
            ErrorBoxStart.errorRun(new ExceptionsLibrary.CategoryExistsWithThisName());
        }
    }

    public void close() {
        Stage stage = (Stage) picture.getScene().getWindow();
        stage.close();
    }
}
