package org.system.Controller.Login;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
//import org.system.DAO.Impl.AccountDAOImpl;
import org.system.Model.Account;
import org.system.utils.SceneController;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button loginButton;
    @FXML
    private TextField username_input;
    @FXML
    private PasswordField password_input;
    @FXML
    private FontAwesomeIconView close_button;
    @FXML
    private CheckBox showPwdCheckBox;
    @FXML
    private ComboBox<String> selectRole;
    @FXML
    private TextField password_text;

//    private AccountDAOImpl accountDAO = new AccountDAOImpl();
//
    public void initialize() {
        selectRole.getItems().addAll("POLICYHOLDER", "DEPENDENT");
    }

    @FXML
    void closeWindow(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void handleLoginButtonClick(ActionEvent event) {
        String username = username_input.getText();
        String password = showPwdCheckBox.isSelected() ? password_text.getText() : password_input.getText();
        String role = selectRole.getValue();

        if (username.isEmpty() || password.isEmpty() || role == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        Account account = new AccountDAOImpl().login(username, password, role);

        if (account != null) {
            if(role == "POLICYHOLDER"){
                try {
                    SceneController.switchSceneCustomer(event, "policyHolderDashBoard");
                    System.out.println("login success");
                } catch (IOException e) {
                    // Handle the IOException here, e.g.,
                    System.err.println("Error switching scene: " + e.getMessage());
                    // You could also display an error alert to the user
                }
            }
        } else {
            // Invalid credentials
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password.");
            alert.showAndWait();
        }
    }

    boolean passToggle = true;
    @FXML
    void showPassword(ActionEvent event) {
        if (showPwdCheckBox.isSelected()) {
            // Show the password in the TextField
            password_text.setText(password_input.getText());
            password_text.setVisible(true);
            password_input.setVisible(false);
        } else {
            // Hide the password, show the PasswordField
            password_input.setText(password_text.getText());
            password_text.setVisible(false);
            password_input.setVisible(true);
        }
    }
}
