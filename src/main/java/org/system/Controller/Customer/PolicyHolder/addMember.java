package org.system.Controller.Customer.PolicyHolder;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.system.Controller.SharedVariable;
import org.system.DataConnection.SupabaseJDBC;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static java.lang.String.valueOf;

public class addMember implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private FontAwesomeIconView closeButton;

    @FXML
    private TextField emailFld;

    @FXML
    private Text errorText;

    @FXML
    private TextField fullNameFld;

    @FXML
    private TextField insuranceFeeFld;

    @FXML
    private PasswordField passwordFld;

    @FXML
    private TextField phoneFld;

    @FXML
    private Button saveButton;

    @FXML
    private TextField usernameFld;

    @FXML
    void close(MouseEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void save(MouseEvent event) {
        // Clear previous error messages
        errorText.setText("");

        // Collect validation errors
        List<String> errors = new ArrayList<>();

        // Validate input fields
        if (usernameFld.getText().isEmpty()) {
            errors.add("Username is required.");
        }
        if (passwordFld.getText().isEmpty()) {
            errors.add("Password is required.");
        } else if (passwordFld.getText().length() <= 8) {
            errors.add("Password must be longer than 8 characters.");
        }
        if (emailFld.getText().isEmpty()) {
            errors.add("Email is required.");
        } else {
            String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
            if (!Pattern.matches(emailPattern, emailFld.getText())) {
                errors.add("Invalid email format.");
            }
        }
        if (phoneFld.getText().isEmpty()) {
            errors.add("Phone number is required.");
        } else if (!phoneFld.getText().matches("\\d{8,}")) {
            errors.add("Phone number must contain only digits and be at least 8 digits long.");
        }
        if (fullNameFld.getText().isEmpty()) {
            errors.add("Full name is required.");
        }
        if (insuranceFeeFld.getText().isEmpty()) {
            errors.add("Insurance fee is required.");
        } else {
            try {
                Integer.parseInt(insuranceFeeFld.getText());
            } catch (NumberFormatException e) {
                errors.add("Insurance fee must be a valid number.");
            }
        }

        // If there are validation errors, display them and return
        if (!errors.isEmpty()) {
            errorText.setText(String.join("\n", errors));
            return;
        }

        // Database connection parameters
        String url = "jdbc:postgresql://localhost:5432/your_database";
        String user = "your_username";
        String dbPassword = "your_password";

        try (Connection conn = SupabaseJDBC.mintDatabase()) {
            // Check for unique username
            if (!isUnique(conn, "username", usernameFld.getText())) {
                errors.add("Username already exists.");
            }

            // Check for unique email
            if (!isUnique(conn, "email", emailFld.getText())) {
                errors.add("Email already exists.");
            }

            // Check for unique phone
            if (!isUnique(conn, "phone", phoneFld.getText())) {
                errors.add("Phone number already exists.");
            }

            // If there are validation errors, display them and return
            if (!errors.isEmpty()) {
                errorText.setText(String.join("\n", errors));
                return;
            }

            // Insert new dependent
            String insertSQL = "INSERT INTO Dependent (username, password, email, phone, policyHolder_id, fullname, insuranceFee, accType) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setString(1, usernameFld.getText());
                pstmt.setString(2, passwordFld.getText());
                pstmt.setString(3, emailFld.getText());
                pstmt.setString(4, phoneFld.getText());
                pstmt.setString(5, SharedVariable.loggedInPolicyHolder.getId());
                pstmt.setString(6, fullNameFld.getText());
                pstmt.setInt(7, SharedVariable.loggedInPolicyHolder.getInsuranceFee()*60/100);
                pstmt.setString(8, "DEPENDENT");

                pstmt.executeUpdate();

                // Close the window after saving
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("New Member Added");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            errorText.setText("Error saving data: " + e.getMessage());
        }
    }

    private boolean isUnique(Connection conn, String column, String value) throws SQLException {
        String query = "SELECT COUNT(*) FROM Dependent WHERE " + column + " = ? UNION ALL SELECT COUNT(*) FROM PolicyHolder WHERE " + column + " = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, value);
            pstmt.setString(2, value);
            try (ResultSet rs = pstmt.executeQuery()) {
                int dependentCount = 0;
                int policyHolderCount = 0;
                if (rs.next()) {
                    dependentCount = rs.getInt(1);
                }
                if (rs.next()) {
                    policyHolderCount = rs.getInt(1);
                }
                return dependentCount == 0 && policyHolderCount == 0;
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        insuranceFeeFld.setText(valueOf(SharedVariable.loggedInPolicyHolder.getInsuranceFee()*60/100));
    }
}
