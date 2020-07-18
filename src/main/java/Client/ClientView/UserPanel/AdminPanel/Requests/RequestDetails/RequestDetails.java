package Client.ClientView.UserPanel.AdminPanel.Requests.RequestDetails;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Off;
import model.Product;
import model.Request;
import model.Seller;

import java.net.URL;
import java.util.ResourceBundle;

public class RequestDetails implements Initializable {
    @FXML
    private Button close;
    @FXML
    private Label requestId;
    @FXML
    private Label requestType;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label5;
    @FXML
    private Label label6;
    @FXML
    private Label title1;
    @FXML
    private Label title2;
    @FXML
    private Label title3;
    @FXML
    private Label title4;
    @FXML
    private Label title5;
    @FXML
    private Label title6;


    private static Request request;

    public static Request getRequest() {
        return request;
    }

    public static void setRequest(Request request) {
        RequestDetails.request = request;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requestId.setText(String.valueOf(getRequest().getRequestId()));
        requestType.setText(String.valueOf(getRequest().getRequestType()));
        Gson gson = new GsonBuilder().serializeNulls().create();
        switch (request.getRequestType()) {
            case REGISTER_SELLER:
                Seller seller = gson.fromJson(request.getRequestDescription(), Seller.class);
                title1.setText("Username:");
                label1.setText(seller.getUsername());
                title2.setText("First Name:");
                label2.setText(seller.getFirstName());
                title3.setText("Last Name:");
                label3.setText(seller.getLastName());
                title4.setText("E-mail:");
                label4.setText(seller.getEmail());
                title5.setText("Phone Number:");
                label5.setText(seller.getPhoneNumber());
                title6.setText("Credit:");
                label6.setText(String.valueOf(seller.getCredit()));
                break;
            case ADD_PRODUCT:
            case EDIT_PRODUCT:
                Product product = gson.fromJson(request.getRequestDescription(), Product.class);
                title1.setText("Username:");
                label1.setText(product.getName());
                title2.setText("Company:");
                label2.setText(product.getCompany());
                title3.setText("Price:");
                label3.setText(String.valueOf(product.getPrice()));
                title4.setText("Category:");
                label4.setText(product.getCategory().getName());
                title5.setText("Quantity:");
                label5.setText(String.valueOf(product.getAvailability()));
                title6.setVisible(false);
                label6.setVisible(false);
                break;
            case ADD_OFF:
            case EDIT_OFF:
                Off off = gson.fromJson(request.getRequestDescription(), Off.class);
                title1.setText("Off Amount:");
                label1.setText(String.valueOf(off.getOffAmount()));
                title2.setText("Start Date:");
                label2.setText(off.getStartDate());
                title3.setText("End Date:");
                label3.setText(off.getEndDate());
                title4.setVisible(false);
                label4.setVisible(false);
                title5.setVisible(false);
                label5.setVisible(false);
                title6.setVisible(false);
                label6.setVisible(false);
                break;
        }
    }

    public void close() {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
}
