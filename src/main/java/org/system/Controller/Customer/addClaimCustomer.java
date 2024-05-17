package org.system.Controller.Customer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.system.Controller.SharedVariable;
import org.system.DataConnection.SupabaseJDBC;
import org.system.Model.Claim;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

//import static org.jsoup.helper.StringUtil.isNumeric;

public class addClaimCustomer implements Initializable {
    @FXML
    private Button backButton;

    @FXML
    private TextField bankAccountName;

    @FXML
    private TextField bankAccountNum;

    @FXML
    private TextField bankName;

    @FXML
    private TextField claimAmount;


    @FXML
    private TextField descriptionBox;

    @FXML
    private TextField insuredPerson;

    @FXML
    private Button saveButton;
    @FXML
    private Text errorText;
    @FXML
    private Button uploadButton;

    @FXML
    private HBox imageContainer;

    @FXML
    private ImageView zoomImageView;
    int claimId;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    String query = null;
    String sql = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Claim claim = null;
    private boolean update;
    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    private void clean() {
        bankName.setText(null);
        insuredPerson.setText(null);
        descriptionBox.setText(null);
        claimAmount.setText(null);
        bankAccountName.setText(null);

    }
    @FXML
    private void save(MouseEvent event) {
        connection = SupabaseJDBC.mintDatabase();
        if (insuredPerson.getText().isEmpty() ||
                bankName.getText().isEmpty() ||
                bankAccountNum.getText().isEmpty() ||
                bankAccountName.getText().isEmpty() ||
                claimAmount.getText().isEmpty() ||
                descriptionBox.getText().isEmpty()) {
            showError("Please fill all data");
        } else if (!isNumeric(bankAccountNum.getText()) || !isNumeric(claimAmount.getText())) {
            showError("Account number and claim amount must be numeric");
        } else if (bankAccountNum.getText().length() > 10 || claimAmount.getText().length() > 10) {
            showError("Account number and claim amount must not exceed 10 digits");
        } else {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to save?", ButtonType.YES, ButtonType.NO);
            confirmAlert.showAndWait();

            if (confirmAlert.getResult() == ButtonType.YES) {
                // save logic here
                getQuery();
                insert();
                clean();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                System.out.println("New Claim Added");
                alert.setContentText("New Claim Added");
                alert.showAndWait();
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.close();
            }
        }
    }

    private void showError(String message) {
        errorText.setText(message);
        errorText.setFill(Color.RED);
        errorText.setVisible(true);
    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
    private void getQuery() {

        if (!update) {
            query = "INSERT INTO \"claims\"( \"insuredPerson\", \"bankName\", \"bankAccountName\", \"bankAccountNum\", \"claimAmount\",\"description\",\"status\" ) VALUES (?,?,?,?,?,?,?)";

        }else{
            query = "UPDATE claims SET \"insuredPerson\"=?, \"bankName\"=?, \"bankAccountNum\"=?, \"bankAccountName\"=?, \"claimAmount\"=?, \"description\"=?, \"status\"=? WHERE id=?";
        }

    }
private void insert() {

    try {
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, insuredPerson.getText());
        preparedStatement.setString(2, bankName.getText());
        preparedStatement.setString(3, bankAccountNum.getText());
        preparedStatement.setString(4, bankAccountName.getText());
        preparedStatement.setInt(5, Integer.parseInt(claimAmount.getText()));
        preparedStatement.setString(6, descriptionBox.getText());
        preparedStatement.setString(7, "Processing");
        if (update) {
            preparedStatement.setInt(8, claimId);
        }
        preparedStatement.executeUpdate();

    } catch (SQLException ex) {
        Logger.getLogger(addClaimCustomer.class.getName()).log(Level.SEVERE, null, ex);
    }

}
    void setTextField(int id,String insuredCustomer, String bankNameText, String bankAccountNumText, String bankAccountNameText, int claimAmountNum, String description) {
        claimId = id;
        insuredPerson.setText(insuredCustomer);
        bankName.setText(bankNameText);
        bankAccountNum.setText(bankAccountNumText);
        bankAccountName.setText(bankAccountNameText);
        claimAmount.setText(String.valueOf(claimAmountNum));
        descriptionBox.setText(description);

    }

    void setUpdate(boolean b) {
        this.update = b;

    }
    @FXML
    private void uploadImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            imageView.setPreserveRatio(true);
            imageView.setOnMouseClicked(e -> zoomImage(image));
            imageContainer.getChildren().add(imageView);
        }
    }

    private void zoomImage(Image image) {
        zoomImageView.setImage(image);
        zoomImageView.setVisible(true);
    }

    @FXML
    private void hideZoomImage(MouseEvent event) {
        zoomImageView.setVisible(false);
    }

}
