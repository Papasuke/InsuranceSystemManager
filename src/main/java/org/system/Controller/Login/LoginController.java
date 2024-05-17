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
    private Button login_button;
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

//    private AccountDAOImpl accountDAO = new AccountDAOImpl();
//
//    public void initialize() {
//        selectRole.getItems().addAll("POLICYHOLDER", "DEPENDENT");
//    }
//
//    @FXML
//    void closeWindow(MouseEvent event) {
//        System.exit(0);
//    }
//
//    @FXML
//    void login(ActionEvent event) throws IOException {
//        String username = username_input.getText();
//        String password = password_input.getText();
//        String role = selectRole.getValue();
//
//        if (username.isEmpty() || password.isEmpty() || role == null) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText(null);
//            alert.setContentText("Please fill in all fields.");
//            alert.showAndWait();
//            return;
//        }
//
//        Account account = accountDAO.login(username, password, role);
//
//        if (account != null) {
//            // Successful login
//            SceneController.switchScene(event, "dashboard");
//        } else {
//            // Invalid credentials
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText(null);
//            alert.setContentText("Invalid username or password.");
//            alert.showAndWait();
//        }
//    }

    @FXML
    void showPassword(ActionEvent event) {
        if (showPwdCheckBox.isSelected()) {
            password_input.setText(password_input.getText());
            password_input.setPromptText(password_input.getText());
        } else {
            password_input.setText("");
            password_input.setPromptText("password");
        }
    }
}
