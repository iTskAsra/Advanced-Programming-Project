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
    TableColumn<Sale,String> saleCodeTable = new TableColumn<>("Sale Code");
    TableColumn<Sale,String> startDateTable= new TableColumn<>("Sale Start Date");
    TableColumn<Sale,String> endDateTable= new TableColumn<>("Sale End Date");
    TableColumn<Sale,Double> percentTable= new TableColumn<>("Percent");
    TableColumn<Sale,Double> maxAmountTable = new TableColumn<>("Max Amount");
    TableColumn<Sale,Integer> validTimesTable = new TableColumn<>("Valid Times");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saleCode.setEditable(false);

        saleCodeTable.setStyle("-fx-alignment: CENTER");
        startDateTable.setStyle("-fx-alignment: CENTER");
        endDateTable.setStyle("-fx-alignment: CENTER");
        percentTable.setStyle("-fx-alignment: CENTER");
        maxAmountTable.setStyle("-fx-alignment: CENTER");
        validTimesTable.setStyle("-fx-alignment: CENTER");
        table.getColumns().addAll(saleCodeTable,startDateTable,endDateTable,percentTable,maxAmountTable,validTimesTable);
        updateTable();
    }

    private void updateTable() {
        try {
            sales = FXCollections.observableArrayList(AdminController.showSales());
        } catch (ExceptionsLibrary.NoSaleException e) {
            ErrorBoxStart.errorRun(e);
        }
        saleCodeTable.setCellValueFactory(new PropertyValueFactory<>("saleCode"));
        startDateTable.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateTable.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        percentTable.setCellValueFactory(new PropertyValueFactory<>("salePercent"));
        maxAmountTable.setCellValueFactory(new PropertyValueFactory<>("saleMaxAmount"));
        validTimesTable.setCellValueFactory(new PropertyValueFactory<>("validTimes"));
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

    //TODO cart
    //TODO purchase
    //TODO fields check
    //TODO admin register first
    //TODO modify database update

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
            updateTable();
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
            updateTable();
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
            updateTable();
        } catch (ExceptionsLibrary.NoSaleException e) {
            ErrorBoxStart.errorRun(e);
        }
    }

    public void close() {
        Stage stage = (Stage) table.getScene().getWindow();
        stage.close();
    }

}
