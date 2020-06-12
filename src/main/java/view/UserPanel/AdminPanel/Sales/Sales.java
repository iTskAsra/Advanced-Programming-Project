package view.UserPanel.AdminPanel.Sales;

import controller.AdminController;
import controller.CustomerController;
import controller.ExceptionsLibrary;
import controller.GetDataFromDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Account;
import model.BuyLog;
import model.Sale;
import view.AlertBox.ErrorBox.ErrorBoxStart;
import view.AlertBox.MessageBox.AlertBoxStart;
import view.UserPanel.BuyLog.BuyLogDetails.BuyLogDetailsStart;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Sales implements Initializable {

    @FXML
    private TableView table;
    @FXML
    private TextField saleCode;
    @FXML
    private TextField percent;
    @FXML
    private TextField maxAmount;
    @FXML
    private TextField validTimes;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Label usernamesLabel;
    @FXML
    private TextField usernames;

    ObservableList<Sale> sales = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saleCode.setEditable(false);
        TableColumn<Sale,String> saleCode = new TableColumn<>("Sale Code");
        TableColumn<Sale,String> startDate= new TableColumn<>("Sale Start Date");
        TableColumn<Sale,String> endDate= new TableColumn<>("Sale End Date");
        TableColumn<Sale,Double> percent= new TableColumn<>("Percent");
        TableColumn<Sale,Double> maxAmount = new TableColumn<>("Max Amount");
        TableColumn<Sale,Integer> validTimes = new TableColumn<>("Valid Times");
        saleCode.setStyle("-fx-alignment: CENTER");
        startDate.setStyle("-fx-alignment: CENTER");
        endDate.setStyle("-fx-alignment: CENTER");
        percent.setStyle("-fx-alignment: CENTER");
        maxAmount.setStyle("-fx-alignment: CENTER");
        validTimes.setStyle("-fx-alignment: CENTER");
        table.getColumns().addAll(saleCode,startDate,endDate,percent,maxAmount,validTimes);
        try {
            sales = FXCollections.observableArrayList(AdminController.showSales());
        } catch (ExceptionsLibrary.NoSaleException e) {
            ErrorBoxStart.errorRun(e);
        }
        saleCode.setCellValueFactory(new PropertyValueFactory<>("saleCode"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        percent.setCellValueFactory(new PropertyValueFactory<>("salePercent"));
        maxAmount.setCellValueFactory(new PropertyValueFactory<>("saleMaxAmount"));
        validTimes.setCellValueFactory(new PropertyValueFactory<>("validTimes"));
        table.setItems(sales);
        table.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                usernamesLabel.setVisible(false);
                usernames.setVisible(false);
                Sale sale = (Sale) table.getSelectionModel().getSelectedItem();
                this.saleCode.setText(sale.getSaleCode());
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDate startDateLocalDate = LocalDate.parse(sale.getStartDate(),dateTimeFormatter);
                LocalDate endDateLocalDate = LocalDate.parse(sale.getEndDate(),dateTimeFormatter);
                this.startDate.setValue(startDateLocalDate);
                this.endDate.setValue(endDateLocalDate);
                this.percent.setText(String.valueOf(sale.getSalePercent()));
                this.maxAmount.setText(String.valueOf(sale.getSaleMaxAmount()));
                this.validTimes.setText(String.valueOf(sale.getValidTimes()));

            }
        });
    }

    public void resetButtonClicked(){
        this.saleCode.setText("");
        this.startDate.setValue(null);
        this.endDate.setValue(null);
        this.percent.setText("");
        this.maxAmount.setText("");
        this.validTimes.setText("");
        usernames.setVisible(true);
        usernamesLabel.setVisible(true);
    }

    //TODO updateTables() (sales, buyLogs, sellLogs)

    public void editButtonClicked() throws IOException {
        Sale sale = (Sale) table.getSelectionModel().getSelectedItem();
        HashMap<String,String> dataToEdit = new HashMap();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDateString = startDate.getValue().format(dateTimeFormatter);
        String endDateString = endDate.getValue().format(dateTimeFormatter);
        startDateString = startDateString + " 00:00";
        endDateString = endDateString + " 00:00";
        dataToEdit.put("startDate",startDateString);
        dataToEdit.put("endDate",endDateString);
        dataToEdit.put("salePercent",percent.getText());
        dataToEdit.put("saleMaxAmount",maxAmount.getText());
        dataToEdit.put("validTimes",validTimes.getText());
        try {
            AdminController.editSaleInfo(sale.getSaleCode(),dataToEdit);
            AlertBoxStart.messageRun("Message","Sale edited!");
        } catch (ExceptionsLibrary.NoSaleException | ExceptionsLibrary.CannotChangeThisFeature | ExceptionsLibrary.NoFeatureWithThisName e) {
            ErrorBoxStart.errorRun(e);
        }
    }

    public void newSaleButtonClicked() throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDateString = startDate.getValue().format(dateTimeFormatter);
        String endDateString = endDate.getValue().format(dateTimeFormatter);
        startDateString = startDateString + " 00:00";
        endDateString = endDateString + " 00:00";
        ArrayList<Account> saleAccounts = new ArrayList<>();
        String[] userList = usernames.getText().split("\\s*,\\s*");
        for (String i : userList){
            Account account = null;
            try {
                account = GetDataFromDatabase.getAccount(i);
                saleAccounts.add(account);
            } catch (ExceptionsLibrary.NoAccountException e) {
                ErrorBoxStart.errorRun(e);
            }
        }
        if (usernames.isVisible()) {
            Sale sale = new Sale(startDateString,endDateString,Double.parseDouble(percent.getText()),Double.parseDouble(maxAmount.getText()),Integer.parseInt(validTimes.getText()),saleAccounts);
            AdminController.addSale(sale);
            sales.add(sale);
            AlertBoxStart.messageRun("Message","Sale added!");
        }
        else {
            ErrorBoxStart.errorRun(new ExceptionsLibrary.NoSaleException());
        }
    }

    public void removeButtonClicked(){
        try {
            Sale sale = null;
            for (Sale i : sales){
                if (i.getSaleCode().equals(saleCode.getText())){
                    sale = i;
                }
            }
            AdminController.removeSaleCode(saleCode.getText());
            AlertBoxStart.messageRun("Message","Sale removed!");
            sales.remove(sale);
        } catch (ExceptionsLibrary.NoSaleException e) {
            ErrorBoxStart.errorRun(e);
        }
    }

    public void close() {
        Stage stage = (Stage) table.getScene().getWindow();
        stage.close();
    }

}
