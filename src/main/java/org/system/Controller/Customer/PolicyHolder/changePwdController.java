package org.system.Controller.Customer.PolicyHolder;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.system.utils.UIEffects;
import org.system.DataConnection.SupabaseJDBC;
import org.system.Model.Account;
import org.system.Controller.SharedVariable;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class changePwdController implements Initializable{

    @FXML
    private Button changePwd;

    @FXML
    private FontAwesomeIconView closeButton;

    @FXML
    private PasswordField confirmPwd;

    @FXML
    private PasswordField currentPwd;

    @FXML
    private PasswordField newPwd;
    @FXML
    private void handleChangePwd(MouseEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        String currentPassword = currentPwd.getText();
        String newPassword = newPwd.getText();
        String confirmPassword = confirmPwd.getText();

        if (!newPassword.equals(confirmPassword)) {
            showAlert(AlertType.ERROR, "Password Mismatch", "New password and confirm password do not match.");
            return;
        }
        if (SharedVariable.loggedInPolicyHolder != null && SharedVariable.loggedInPolicyHolder.getPassword().equals(currentPassword)) {
            try (Connection connection = SupabaseJDBC.mintDatabase()) {
                String sql = "UPDATE PolicyHolder SET password = ? WHERE policyholder_id=?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, newPassword);
                    statement.setString(2, SharedVariable.loggedInPolicyHolder.getId());
                    int rowsUpdated = statement.executeUpdate();
                    SharedVariable.loggedInPolicyHolder.setPassword(newPassword);
                    if (rowsUpdated > 0) {
                        showAlert(AlertType.INFORMATION, "Success", "Password changed successfully.");
                    } else {
                        showAlert(AlertType.ERROR, "Error", "Failed to change password.");
                    }
                }
            } catch (SQLException e) {
                showAlert(AlertType.ERROR, "Database Error", "Error updating password: " + e.getMessage());
            }
        } else {
            showAlert(AlertType.ERROR, "Invalid Password", "Current password is incorrect.");
        }
        stage.close();
    }

    @FXML
    private void handleClose(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
