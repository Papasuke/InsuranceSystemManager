package org.system.Controller.Customer;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.system.Controller.SharedVariable;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.system.DataConnection.SupabaseJDBC;
import org.system.Model.*;
import org.system.utils.SceneController;

import static org.system.Controller.SharedVariable.loggedInAccount;
import static org.system.Controller.SharedVariable.loggedInPolicyHolder;

public class PolicyHolderController implements Initializable {

    @FXML
    private AnchorPane claimMenu;

    @FXML
    private FontAwesomeIconView closeButton;

    @FXML
    private Button editButton;

    @FXML
    private Text emailFld;

    @FXML
    private Text fullnameFld;

    @FXML
    private FontAwesomeIconView logoutButton;

    @FXML
    private AnchorPane memberMenu;

    @FXML
    private Text phoneFld;

    @FXML
    private Label totalClaim;

    @FXML
    private Label totalFee;

    @FXML
    private Label totalMember;

    @FXML
    private Text usernameFld;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Account loggedInAccount = SharedVariable.loggedInAccount;

        if (loggedInAccount != null && loggedInAccount.getAccType().equals("POLICYHOLDER")) {
            try {
                // 1. Fetch Policyholder data using the account ID
                loggedInPolicyHolder = getPolicyholderFromDatabase(loggedInAccount.getId());

                // 2. Populate FXML fields
                fullnameFld.setText(loggedInPolicyHolder.getFullName()); // Corrected to use getFullName()
                emailFld.setText(loggedInPolicyHolder.getEmail());
                phoneFld.setText(loggedInPolicyHolder.getPhone());
                // ... set other fields based on your Policyholder attributes
            } catch (Exception e) {
                // Handle potential exceptions during data fetching
                System.err.println("Error fetching policyholder data: " + e.getMessage());
            }
        } else {
            // Handle cases where there's no logged-in account or the account is not a Policyholder
            System.err.println("No logged-in Policyholder account found.");
        }
    }
    @FXML
    private void close(MouseEvent event) {
        javafx.application.Platform.exit();
    }
    @FXML
    private void claimMenuClick(MouseEvent event) {
        try {
            SceneController.switchSceneCustomer(event, "PolicyHolderClaim");
        } catch (IOException e) {
            System.err.println("Error switching scene: " + e.getMessage());
        }
    }

    @FXML
    private void memberMenuClick(MouseEvent event) {
        try {
            SceneController.switchSceneCustomer(event, "PolicyHolderMembers");
        } catch (IOException e) {
            System.err.println("Error switching scene: " + e.getMessage());
        }
    }
    private PolicyHolder getPolicyholderFromDatabase(String accountId) {
        try (Connection connection = SupabaseJDBC.mintDatabase()) {
            String sql = "SELECT * FROM PolicyHolder WHERE policyholder_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, accountId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Process the result set and create a Policyholder object
                        String policyholderId = resultSet.getString("policyholder_id");
                        String username = resultSet.getString("username");
                        String password = resultSet.getString("password");
                        String email = resultSet.getString("email");
                        String phone = resultSet.getString("phone");
                        String fullName = resultSet.getString("fullname");
                        int insuranceFee = resultSet.getInt("insuranceFee");
                        // ... fetch claimId and dependentId (you'll need to handle the array data type)
                        return new PolicyHolder(policyholderId, username, password, email, phone, "POLICYHOLDER", fullName, insuranceFee /*, claimId, dependentId */);
                    } else {
                        // Handle case where no Policyholder is found for the given accountId
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            System.err.println("Error fetching policyholder from database: " + e.getMessage());
        }
        return null;
    }


}



