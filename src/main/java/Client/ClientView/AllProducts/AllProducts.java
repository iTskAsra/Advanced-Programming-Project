package Client.ClientView.AllProducts;

import Client.ClientController.*;
import Client.ClientView.HelpWindow.*;
import Client.ClientView.AlertBox.ErrorBox.*;
import Client.ClientView.AlertBox.MessageBox.*;
import Client.ClientView.MainMenuStage.CheckFields;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import model.*;
import view.Base.Main;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class AllProducts implements Initializable {

    @FXML
    private TilePane tilePane;
    @FXML
    private TreeView categoryTreeView;
    @FXML
    private TreeView categorySearchTreeView;
    @FXML
    private TreeView featureTreeView;
    @FXML
    private TextField search;
    @FXML
    private TextField minPrice;
    @FXML
    private TextField maxPrice;
    @FXML
    private TextField brand;
    @FXML
    private TextField seller;
    @FXML
    private Button searchApply;
    @FXML
    private Button priceApply;
    @FXML
    private Button brandApply;
    @FXML
    private Button sellerApply;
    @FXML
    private Button availabilityApply;
    @FXML
    private Button featureApplyButton;
    @FXML
    private TableView listView;
    @FXML
    private CheckBox availability;
    @FXML
    private TextField featureValue;
    @FXML
    private Button login;
    @FXML
    private ChoiceBox sort;

    private static TreeItem<String>[] treeItems = new TreeItem[2];

    public static TreeItem<String>[] getTreeItems() {
        return treeItems;
    }

    public static void setTreeItems(TreeItem<String>[] treeItems) {
        AllProducts.treeItems = treeItems;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        sort.getItems().addAll("Product ID", "Name", "Company", "Price", "Date", "Availability");

        sort.getSelectionModel().selectFirst();

        sort.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            AllProductsPanelController.getCurrentSort().clear();
            AllProductsPanelController.setSort((String) sort.getValue());
            updateProducts();
        });

        if (Main.statusProperty().getValue() != null) {
            login.setText("Account");
            login.setOnAction(e -> {
                try {
                    accountButtonClickedLoggedIn();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
        } else {
            login.setText("Login");
            login.setOnAction(e -> {
                try {
                    accountButtonClickedNotLoggedIn();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
        }
        Main.statusProperty().addListener((v, oldValue, newValue) -> {
            if (newValue != null) {
                login.setText("Account");
                login.setOnAction(e -> {
                    try {
                        accountButtonClickedLoggedIn();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });
            } else {
                login.setText("Login");
                login.setOnAction(e -> {
                    try {
                        accountButtonClickedNotLoggedIn();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });
            }
        });

        try {
            TableColumn<String, String> currentFilters = new TableColumn<>("Current Filters");
            currentFilters.setStyle("-fx-alignment: CENTER");
            currentFilters.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String, String> p) {
                    String[] splitFilters = p.getValue().split("--");
                    String formatted;
                    if (splitFilters.length == 2) {
                        formatted = splitFilters[0] + " : " + splitFilters[1];
                    } else if (splitFilters.length == 3) {
                        formatted = splitFilters[0] + " : " + splitFilters[1] + " - " + splitFilters[2];
                    } else {
                        formatted = splitFilters[0] + " -> " + splitFilters[1] + " -> " + splitFilters[2] + " -> " + splitFilters[3];
                    }

                    return new SimpleStringProperty(formatted);
                }
            });
            listView.getColumns().add(currentFilters);

            featureValue.setVisible(false);
            featureApplyButton.setVisible(false);
            listView.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2) {
                    if (listView.getSelectionModel().getSelectedItem() != null) {
                        AllProductsPanelController.disableFilter(listView.getSelectionModel().getSelectedIndex() + 1);
                        listView.getItems().remove(listView.getSelectionModel().getSelectedIndex());
                        updateProducts();
                    }
                }
            });

            updateFilterList();

            TreeItem<String> categoriesRoot = new TreeItem<String>("Categories");
            categoriesRoot.setExpanded(true);
            ArrayList<Category> categories = AdminController.showCategories();
            for (Category i : categories) {
                TreeItem<String> categoryName = new TreeItem<String>(i.getName());
                for (Feature j : i.getFeatures()) {
                    TreeItem<String> feature = new TreeItem<String>(j.getParameter());
                    categoryName.getChildren().add(feature);
                }
                categoriesRoot.getChildren().add(categoryName);
            }
            categoryTreeView.setRoot(categoriesRoot);

            TreeItem<String> categoriesRootCategorySearchTreeView = new TreeItem<String>("Categories");
            categoriesRootCategorySearchTreeView.setExpanded(true);
            for (Category i : categories) {
                TreeItem<String> categoryName = new TreeItem<String>(i.getName());
                categoriesRootCategorySearchTreeView.getChildren().add(categoryName);

            }
            categorySearchTreeView.setRoot(categoriesRootCategorySearchTreeView);
            categorySearchTreeView.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2) {
                    if (categorySearchTreeView.getSelectionModel().getSelectedItem() != null) {
                        categoryApplyClicked((TreeItem) categorySearchTreeView.getSelectionModel().getSelectedItem());
                    }
                }
            });


            TreeItem<String> categoriesRootFeatureSearchTreeView = new TreeItem<String>("Categories");
            categoriesRootFeatureSearchTreeView.setExpanded(true);
            for (Category i : categories) {
                TreeItem<String> categoryName = new TreeItem<String>(i.getName());
                for (Feature j : i.getFeatures()) {
                    TreeItem<String> feature = new TreeItem<String>(j.getParameter());
                    categoryName.getChildren().add(feature);
                }
                categoriesRootFeatureSearchTreeView.getChildren().add(categoryName);
            }
            featureTreeView.setRoot(categoriesRootFeatureSearchTreeView);
            featureTreeView.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2) {
                    if (featureTreeView.getSelectionModel().getSelectedItem() != null) {
                        TreeItem treeItem = (TreeItem) featureTreeView.getSelectionModel().getSelectedItem();
                        if (treeItem.isLeaf()) {
                            TreeItem<String>[] treeItemsSelected = new TreeItem[2];
                            treeItemsSelected[0] = treeItem.getParent();
                            treeItemsSelected[1] = treeItem;
                            setTreeItems(treeItemsSelected);
                            featureApplyButton.setVisible(true);
                            featureValue.setVisible(true);
                            featureValue.setText("");
                            featureValue.setPromptText("Value for " + treeItem.getValue() + ":");
                        }
                    }
                }
            });

            featureTreeView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
                TreeItem treeItem = (TreeItem) newValue;
                if (!treeItem.isLeaf()) {
                    featureValue.setPromptText("");
                    featureValue.setVisible(false);
                    featureApplyButton.setVisible(false);
                }
            });


        } catch (ExceptionsLibrary.NoCategoryException e) {
            ErrorBoxStart.errorRun(e);
        }
    }


    public void accountButtonClickedNotLoggedIn() throws IOException {
        Stage stage = new Stage();
        File file = new File("src/main/java/Client/ClientView/RegisterAndLoginStage/RegisterAndLogin.fxml");
        URL url = file.toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    public void accountButtonClickedLoggedIn() throws IOException {
        if (!Main.checkLoggedIn().equals("Admin")) {
            Stage stage = (Stage) login.getScene().getWindow();
            File file = new File("src/main/java/Client/ClientView/UserPanel/UserPanel.fxml");
            URL url = file.toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } else {
            Stage stage = (Stage) login.getScene().getWindow();
            File file = new File("src/main/java/Client/ClientView/UserPanel/AdminPanel/AdminPanel.fxml");
            URL url = file.toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }
    }


    private void updateFilterList() {
        listView.getItems().clear();
        ObservableList<String> filters = FXCollections.observableArrayList(AllProductsPanelController.getCurrentFilters());
        listView.setItems(filters);
        Iterator iterator = listView.getItems().iterator();
        while (iterator.hasNext()) {
            String temp = (String) iterator.next();
            if (temp == null) {
                iterator.remove();
            }
        }
        updateProducts();
    }

    private ImageView GetProductImage(Category category) throws IOException {
        File file = new File("Resources/Category/" + category.getName() + ".jpg");
        Image image = new Image(new FileInputStream(file), 300, 300, true, true);
        ImageView imageView = new ImageView(image);
        return imageView;
    }

    private void updateProducts() {
        clearProducts();
        AllProductsPanelController.getCurrentSort().clear();
        AllProductsPanelController.setSort((String) sort.getValue());
        ArrayList<Product> allProducts = null;
        try {
            allProducts = AllProductsPanelController.showProducts();
            for (Product i : allProducts) {
                VBox vBox = new VBox(5);
                vBox.setAlignment(Pos.CENTER);
                ImageView imageView = GetProductImage(i.getCategory());
                Label name = new Label(i.getName());
                Label price = new Label(String.valueOf(i.getPriceWithOff()));
                Double ratesSum = 0.00;
                for (Rate j : i.getRates()) {
                    ratesSum += j.getRateScore();
                }
                Double rateDouble = ratesSum / i.getRates().size();
                Label rate = new Label();
                if (!rateDouble.isNaN()) {
                    rate.setText(Math.round(rateDouble * 2) / 2.0 + "/5");
                } else {
                    rate.setText("Not rated yet!");
                }
                vBox.getChildren().addAll(imageView, name, price, rate);
                tilePane.getChildren().add(vBox);
                vBox.setId(String.valueOf(i.getProductId()));
                vBox.getStyleClass().add("vBoxProduct");
                vBox.setOnMouseClicked(e -> {
                    Product product = null;
                    try {
                        product = AllProductsPanelController.goToProductPage(Integer.parseInt(vBox.getId()));
                        ProductPageController.setProduct(product);
                        Stage stage = new Stage();
                        File file = new File("src/main/java/Client/ClientView/ProductPage/ProductPage.fxml");
                        URL url = file.toURI().toURL();
                        Parent root = FXMLLoader.load(url);
                        Scene scene = new Scene(root);
                        scene.setFill(Color.TRANSPARENT);
                        stage.setScene(scene);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initStyle(StageStyle.TRANSPARENT);
                        stage.show();
                    } catch (ExceptionsLibrary.NoProductException exception) {
                        ErrorBoxStart.errorRun(exception);
                    } catch (MalformedURLException malformedURLException) {
                        malformedURLException.printStackTrace();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                });
            }
        } catch (ExceptionsLibrary.NoProductException | ExceptionsLibrary.NoFilterWithThisName | ExceptionsLibrary.NoAccountException | ExceptionsLibrary.NoFeatureWithThisName | ExceptionsLibrary.NoCategoryException e) {
            ErrorBoxStart.errorRun(e);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void clearProducts() {
        tilePane.getChildren().clear();
    }

    public void searchApplyClicked() {
        Iterator iterator = AllProductsPanelController.getCurrentFilters().iterator();
        while (iterator.hasNext()) {
            String temp = (String) iterator.next();
            if (temp.startsWith("Search--")) {
                iterator.remove();
            }
        }
        AllProductsPanelController.getCurrentFilters().add("Search--" + search.getText());
        updateFilterList();
    }

    public void priceApplyClicked() {
        try {
            CheckFields.checkField("double",minPrice.getText());
            CheckFields.checkField("double",maxPrice.getText());
        } catch (ExceptionsLibrary.NotAcceptableFormatInput e) {
            ErrorBoxStart.errorRun(e);
            return;
        }

        AllProductsPanelController.getCurrentFilters().add("Price--" + minPrice.getText() + "--" + maxPrice.getText());
        updateFilterList();

    }

    public void availabilityApplyClicked() {
        boolean isSelected = availability.isSelected();
        if (isSelected) {
            Iterator iterator = AllProductsPanelController.getCurrentFilters().iterator();
            while (iterator.hasNext()) {
                String temp = (String) iterator.next();
                if (temp.startsWith("Availability--")) {
                    iterator.remove();
                }
            }
            AllProductsPanelController.getCurrentFilters().add("Availability--yes");
        } else {
            if (AllProductsPanelController.getCurrentFilters().contains("Availability--yes")) {
                AllProductsPanelController.getCurrentFilters().remove("Availability--yes");
            }
        }
        updateFilterList();

    }

    public void brandApplyClicked() {
        String brandName = brand.getText();
        AllProductsPanelController.getCurrentFilters().add("Brand--" + brandName);
        updateFilterList();
    }

    public void sellerApplyClicked() {
        String sellerName = seller.getText();
        try {
            if (GetDataFromDatabase.getAccount(sellerName) != null) {
                AllProductsPanelController.getCurrentFilters().add("Seller--" + sellerName);
                updateFilterList();
            } else {
                ErrorBoxStart.errorRun(new ExceptionsLibrary.NoAccountException());
            }
        } catch (ExceptionsLibrary.NoAccountException e) {
            ErrorBoxStart.errorRun(e);
        }
    }

    public void featureApplyClicked() {
        String category = getTreeItems()[0].getValue();
        String feature = getTreeItems()[1].getValue();
        String featureValueString = featureValue.getText();
        Iterator iterator = AllProductsPanelController.getCurrentFilters().iterator();
        while (iterator.hasNext()) {
            String temp = (String) iterator.next();
            if (temp.startsWith("Feature--")) {
                iterator.remove();
            }
        }
        AllProductsPanelController.getCurrentFilters().add("Feature--" + category + "--" + feature + "--" + featureValueString);
        updateFilterList();
    }

    public void categoryApplyClicked(TreeItem treeItem) {
        AllProductsPanelController.getCurrentFilters().add("Category--" + treeItem.getValue());
        updateFilterList();
    }

    public void backButtonClicked() throws IOException {
        Stage stage = (Stage) tilePane.getScene().getWindow();
        File file = new File("src/main/java/Client/ClientView/MainMenuStage/MainMenu.fxml");
        URL url = file.toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void helpButtonClicked() throws IOException {
        Help.setMessage("Here you can see products, sort and filter them, you can filter them by features value by double-clicking on the feature and see your current filters and delete them by double-clicking on them and you can see categories too!");
        Stage stage = new Stage();
        File file = new File("src/main/java/Client/ClientView/HelpWindow/Help.fxml");
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
