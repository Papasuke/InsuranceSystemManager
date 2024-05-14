package org.system.Controller.Customer;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.system.Controller.SharedVariable;
import org.system.DataConnection.SupabaseJDBC;
import org.system.Model.Claim;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Claim student = null;
    private boolean update;
    int studentId;
    @FXML
    private void close(MouseEvent event) {
        SharedVariable.openOnce = false;
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    private void save(MouseEvent event) {
        connection = SupabaseJDBC.mintDatabase();
        if (bankAccountNum.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All Data");
            alert.showAndWait();
        } else  {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to save?", ButtonType.YES, ButtonType.NO);
            confirmAlert.showAndWait();

            if (confirmAlert.getResult() == ButtonType.YES) {
                // implement your save logic here
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                System.out.println("New Claim Added");
                alert.setContentText("New Claim Added");
                alert.showAndWait();
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.close();
            }
        }

    }
    private void getQuery() {

        if (update == false) {

            query = "INSERT INTO \"claims\"( `name`, `birth`, `adress`, `email`) VALUES (?,?,?,?)";

        }else{
            query = "UPDATE `student` SET "
                    + "`name`=?,"
                    + "`birth`=?,"
                    + "`adress`=?,"
                    + "`email`= ? WHERE id = '"+studentId+"'";
        }

    }

}
