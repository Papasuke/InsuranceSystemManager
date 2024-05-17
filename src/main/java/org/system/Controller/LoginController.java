package org.system.Controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController {

    @FXML
    private FontAwesomeIconView close_button;

    @FXML
    private Button login_button;

    @FXML
    private PasswordField password_input;

    @FXML
    private ComboBox<String> selectRole;

    @FXML
    private CheckBox showPwdCheckBox;

    @FXML
    private TextField username_input;


    // Database connection details
    private final String DB_URL = "jdbc:postgresql://your_db_host:5432/your_db_name";
    private final String DB_USER = "your_db_user";
    private final String DB_PASSWORD = "your_db_password";

//    @FXML
//    public void initialize() {
//        selectRole.getItems().addAll("Customer", "Manager", "Admin");
//        selectRole.setValue("Customer"); // Default role
//    }
//
//    @FXML
//    private void handleLoginButtonAction() {
//        String username = username_input.getText();
//        String password = password_input.getText();
//        String role = selectRole.getValue();
//
//        if (username.isEmpty() || password.isEmpty() || role == null) {
//            showAlert("Error", "Please enter username, password, and select a role.");
//            return;
//        }
//
//        try (Connection connection = SupabaseJDBC.mintDatabase()) {
//            String query = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setString(1, username);
//            statement.setString(2, password);
//            statement.setString(3, role);
//
//            ResultSet resultSet = statement.executeQuery();
//
//            if (resultSet.next()) {
//                // Successful login
//                switch (role) {
//                    case "Customer":
//                        checkCustomerTables(connection, username);
//                        break;
//                    case "Manager":
//                        // Handle manager login
//                        break;
//                    case "Admin":
//                        // Handle admin login
//                        break;
//                }
//            } else {
//                showAlert("Error", "Invalid username, password, or role.");
//            }
//
//        } catch (SQLException e) {
//            showAlert("Error", "Database connection error: " + e.getMessage());
//        }
//    }
//
//    private void checkCustomerTables(Connection connection, String username) {
//        try {
//            // Check policyHolder table
//            String policyQuery = "SELECT * FROM policyHolder WHERE customer_id = ?";
//            PreparedStatement policyStatement = connection.prepareStatement(policyQuery);
//            policyStatement.setString(1, username); // Assuming customer_id is the username
//            ResultSet policyResult = policyStatement.executeQuery();
//
//            // Check dependent table
//            String dependentQuery = "SELECT * FROM dependent WHERE customer_id = ?";
//            PreparedStatement dependentStatement = connection.prepareStatement(dependentQuery);
//            dependentStatement.setString(1, username);
//            ResultSet dependentResult = dependentStatement.executeQuery();
//
//            // Process the results from both tables as needed
//            // ...
//
//        } catch (SQLException e) {
//            showAlert("Error", "Error checking customer tables: " + e.getMessage());
//        }
//    }
//
//    private void showAlert(String title, String message) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
}
