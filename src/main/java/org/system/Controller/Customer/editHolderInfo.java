package org.system.Controller.Customer;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.system.Controller.SharedVariable;
import org.system.DataConnection.SupabaseJDBC;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.system.Controller.SharedVariable.*;

public class editHolderInfo {
    @FXML
    private Button backButton;

    @FXML
    private FontAwesomeIconView closeButton;

    @FXML
    private TextField emailFld;

    @FXML
    private Text errorText;

    @FXML
    private TextField feeFld;

    @FXML
    private TextField fullNameFld;

    @FXML
    private TextField idFld;

    @FXML
    private TextField phoneFld;

    @FXML
    private Button saveButton;

    @FXML
    private TextField usernameFld;
    StringBuilder errorMessages = new StringBuilder();
    public void initialize() {
        fillFields();
    }

    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    private void fillFields() {
        idFld.setText(loggedInPolicyHolder.getId());
        fullNameFld.setText(loggedInPolicyHolder.getFullName());
        usernameFld.setText(loggedInPolicyHolder.getUsername());
        emailFld.setText(loggedInPolicyHolder.getEmail());
        phoneFld.setText(loggedInPolicyHolder.getPhone());
        feeFld.setText(String.valueOf(loggedInPolicyHolder.getInsuranceFee())); // Assuming fee is a numeric type
    }
    @FXML
    private void saveChanges(MouseEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        String fullName = fullNameFld.getText();
        String phone = phoneFld.getText();
        String email = emailFld.getText();

        errorMessages.setLength(0); // Clear previous error messages

        // Validate phone number (only numbers)
        if (!phone.matches("[0-9]+")) {
            errorMessages.append("Phone number must contain only numbers.\n");
        }

        // Validate email format
        if (!email.matches(".+@.+\\..+")) {
            errorMessages.append("Invalid email format.\n");
        }

        // Display errors if any
        if (!errorMessages.isEmpty()) {
            showError(errorMessages.toString());
            return; // Stop further processing if there are errors
        }
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to save?", ButtonType.YES, ButtonType.NO);
        confirmAlert.showAndWait();
        if (confirmAlert.getResult() == ButtonType.YES) {
            loggedInPolicyHolder.setFullName(fullName);
            loggedInPolicyHolder.setPhone(phone);
            loggedInPolicyHolder.setEmail(email);
            try (Connection connection = SupabaseJDBC.mintDatabase();
                 PreparedStatement statement = connection.prepareStatement(
                         "UPDATE PolicyHolder SET fullname=?, phone=?, email=? WHERE policyholder_id=?")) {

                statement.setString(1, loggedInPolicyHolder.getFullName());
                statement.setString(2, loggedInPolicyHolder.getPhone());
                statement.setString(3, loggedInPolicyHolder.getEmail());
                statement.setString(4, loggedInPolicyHolder.getId()); // Assuming PolicyHolder.getId() returns the policyholder_id

                int rowsAffected = statement.executeUpdate();
                System.out.println(rowsAffected + " rows updated.");

            } catch (SQLException e) {
                showError("Error updating database: " + e.getMessage());
                e.printStackTrace();
                return; // Stop further processing if there's a database error
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            System.out.println("Your Information Updated");
//            alert.setContentText("Your Information Updated");
            alert.showAndWait();
        }
        stage.close();
    }

    private void showError(String message) {
        errorText.setText(message);
        errorText.setFill(Color.RED);
        errorText.setVisible(true);
    }
}
