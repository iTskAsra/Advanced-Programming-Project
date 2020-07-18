package Client.ClientView.UserPanel.AdminPanel.Categories;

import Client.ClientController.AdminController;
import Client.ClientController.ExceptionsLibrary;
import Client.ClientController.GetDataFromDatabase;
import Client.ClientView.AlertBox.ErrorBox.ErrorBoxStart;
import Client.ClientView.AlertBox.MessageBox.AlertBoxStart;
import Client.ClientView.UserPanel.AdminPanel.Categories.NewEditCategories.NewEditCategories;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Category;
import model.Feature;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Categories implements Initializable {

    @FXML
    private TreeView treeView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTree();
    }

    private void updateTree() {
        TreeItem<String> categoriesRoot = new TreeItem<String>("Categories");
        categoriesRoot.setExpanded(true);
        try {
            ArrayList<Category> categories = AdminController.showCategories();
            for (Category i : categories) {
                TreeItem<String> categoryName = new TreeItem<String>(i.getName());
                for (Feature j : i.getFeatures()) {
                    TreeItem<String> feature = new TreeItem<String>(j.getParameter());
                    categoryName.getChildren().add(feature);
                }
                categoriesRoot.getChildren().add(categoryName);
            }
            treeView.setRoot(categoriesRoot);
        } catch (ExceptionsLibrary.NoCategoryException e) {
            ErrorBoxStart.errorRun(e);
        }
    }

    public void addCategoryButtonClicked() throws IOException {
        NewEditCategories.setNewCategory(true);
        Stage stage = new Stage();
        File file = new File("src/main/java/view/UserPanel/AdminPanel/Categories/NewEditCategories/NewEditCategories.fxml");
        URL url = file.toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        updateTree();
        stage.showAndWait();
    }

    public void editCategoryButtonClicked() throws IOException {
        NewEditCategories.setNewCategory(false);
        TreeItem temp = (TreeItem) treeView.getSelectionModel().getSelectedItem();
        if (temp == null || temp.isLeaf() || temp.getParent() == null) {
            AlertBoxStart.messageRun("Notice!", "You should select a category (and not features) from the table first!");
            return;
        }
        Category tempCategory = null;
        try {
            tempCategory = GetDataFromDatabase.getCategory((String) temp.getValue());
            NewEditCategories.setEditCategory(tempCategory);
            Stage stage = new Stage();
            File file = new File("src/main/java/view/UserPanel/AdminPanel/Categories/NewEditCategories/NewEditCategories.fxml");
            URL url = file.toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.showAndWait();
            updateTree();
        } catch (ExceptionsLibrary.NoCategoryException e) {
            ErrorBoxStart.errorRun(e);
        }
    }

    public void removeCategoryButtonClicked() throws IOException {
        TreeItem temp = (TreeItem) treeView.getSelectionModel().getSelectedItem();
        if (temp == null || temp.isLeaf() || temp.getParent() == null) {
            AlertBoxStart.messageRun("Notice!", "You should select a category (and not features) from the table first!");
            return;
        }
        try {
            AdminController.deleteCategory((String) temp.getValue());
            updateTree();
        } catch (ExceptionsLibrary.NoCategoryException | ExceptionsLibrary.NoProductException e) {
            ErrorBoxStart.errorRun(e);
        }
    }

    public void close() {
        Stage stage = (Stage) treeView.getScene().getWindow();
        stage.close();
    }

}
