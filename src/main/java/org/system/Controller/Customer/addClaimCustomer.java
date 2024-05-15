package org.system.Controller.Customer;

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
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        SharedVariable.openOnce = false;
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
        if (bankAccountNum.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All Data");
            alert.showAndWait();
        } else  {
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
                SharedVariable.openOnce = false;
                alert.showAndWait();
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.close();
            }
        }

    }
    private void getQuery() {

        if (!update) {
            query = "INSERT INTO \"claims\"( \"insuredPerson\", \"bankName\", \"bankAccount\", \"getBankAccountName\", \"claimAmount\",\"description\",\"status\" ) VALUES (?,?,?,?,?,?,?)";

        }else{
//            query = "UPDATE \"claims\" SET "
//                    + "\"insuredPerson\"=?,"
//                    + "\"bankName\"=?,"
//                    + "\"bankAccountNum\"=?,"
//                    + "\"bankAccountName\"= ?,"
//                    +  "\"claimAmount\"= ?,"
//                    + "\"description\"= ?,"
//                    + "\"status\"= ? WHERE \"id\" = '"+ claimId;
            String sql = "UPDATE claims SET insuredPerson=?, bankName=?, bankAccountNum=?, bankAccountName=?, claimAmount=?, description=?, status=? WHERE id = ?";
        }

    }
private void insert() {

    try {
//        String countQuery = null;
//        countQuery = "SELECT COUNT(*) AS count FROM Claims"; // replace with your SQL query
//        preparedStatement = connection.prepareStatement(countQuery);
//        resultSet = preparedStatement.executeQuery();
//        if (resultSet.next()) {
//            int count = resultSet.getInt("count");
//            System.out.println("Total number of objects: " + count);
//        }
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, insuredPerson.getText());
        preparedStatement.setString(2, bankName.getText());
        preparedStatement.setString(3, bankAccountNum.getText());
        preparedStatement.setString(4, bankAccountName.getText());
        preparedStatement.setInt(5, Integer.parseInt(claimAmount.getText()));
        preparedStatement.setString(6, descriptionBox.getText());
        preparedStatement.setString(7, "Processing");
        preparedStatement.executeUpdate()   ;

    } catch (SQLException ex) {
        Logger.getLogger(addClaimCustomer.class.getName()).log(Level.SEVERE, null, ex);
    }

}
    void setTextField(int id, String bankNameText, String bankAccountNumText, String bankAccountNameText, int claimAmountNum, String description) {

        claimId = id;
        insuredPerson.setText(String.valueOf(id));
        bankName.setText(bankNameText);
        bankAccountNum.setText(bankAccountNumText);
        bankAccountName.setText(bankAccountNameText);
        claimAmount.setText(String.valueOf(claimAmountNum));
        descriptionBox.setText(description);

    }

    void setUpdate(boolean b) {
        this.update = b;

    }

}
