package org.system.Controller.Customer.PolicyHolder;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
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

import static java.lang.String.valueOf;
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
    @FXML
    private Text insuranceFee;
    @FXML
    private Text displayName;

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
                usernameFld.setText(loggedInPolicyHolder.getUsername());
                insuranceFee.setText(valueOf(loggedInPolicyHolder.getInsuranceFee()) + "$");
                displayName.setText(loggedInPolicyHolder.getFullName().split(" ")[0]);
            } catch (Exception e) {
                // Handle potential exceptions during data fetching
                System.err.println("Error fetching policyholder data: " + e.getMessage());
            }
        } else {
            // Handle cases where there's no logged-in account or the account is not a Policyholder
            System.err.println("No logged-in Policyholder account found.");
        }
        System.out.println(loggedInPolicyHolder.toString());
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
    @FXML
    private void getEditView(MouseEvent event) {
        try {
            // Load the FXML for the edit view
            Parent editView = FXMLLoader.load(getClass().getResource("/Fxml/Customer/PolicyHolder/editHolderInfo.fxml"));

            // Create a new scene for the edit view
            Scene editScene = new Scene(editView);

            // Create a new stage for the edit window
            Stage editStage = new Stage();
            editStage.setScene(editScene);

            // Customize the edit window (optional)
            editStage.initStyle(StageStyle.DECORATED);
            editStage.initModality(Modality.APPLICATION_MODAL); // Prevent interaction with main window

            // Get the primary stage (for blur effect if needed)
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Apply blur effect to the primary stage (if needed)
            applyBlurEffect(primaryStage);

            // Show the edit window
            editStage.showAndWait(); // Wait for the edit window to close

            // Remove blur effect after the edit window is closed (if needed)
            removeBlurEffect(primaryStage);

            // Reload policyholder data and refresh UI fields
            reloadPolicyholderData();

        } catch (IOException ex) {
            Logger.getLogger(policyHolderClaimController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void getPwdView(MouseEvent event) {
        try {
            // Load the FXML for the edit view
            Parent editView = FXMLLoader.load(getClass().getResource("/Fxml/Customer/PolicyHolder/changePwd.fxml"));

            // Create a new scene for the edit view
            Scene editScene = new Scene(editView);

            // Create a new stage for the edit window
            Stage editStage = new Stage();
            editStage.setScene(editScene);

            // Customize the edit window (optional)
            editStage.initStyle(StageStyle.UTILITY);
            editStage.initModality(Modality.APPLICATION_MODAL); // Prevent interaction with main window

            // Get the primary stage (for blur effect if needed)
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Apply blur effect to the primary stage (if needed)
            applyBlurEffect(primaryStage);

            // Show the edit window
            editStage.showAndWait(); // Wait for the edit window to close

            // Remove blur effect after the edit window is closed (if needed)
            removeBlurEffect(primaryStage);

            // Reload policyholder data and refresh UI fields
            reloadPolicyholderData();

        } catch (IOException ex) {
            Logger.getLogger(policyHolderClaimController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void reloadPolicyholderData() {
        try {
            // Fetch the latest policyholder data
            loggedInPolicyHolder = getPolicyholderFromDatabase(loggedInAccount.getId());

            // Update the UI fields with the new data
            fullnameFld.setText(loggedInPolicyHolder.getFullName());
            emailFld.setText(loggedInPolicyHolder.getEmail());
            phoneFld.setText(loggedInPolicyHolder.getPhone());
            usernameFld.setText(loggedInPolicyHolder.getUsername());
            insuranceFee.setText(valueOf(loggedInPolicyHolder.getInsuranceFee()) + "$");
            displayName.setText(loggedInPolicyHolder.getFullName().split(" ")[0]);
        } catch (Exception e) {
            System.err.println("Error reloading policyholder data: " + e.getMessage());
        }
    }

    // Helper functions to apply and remove blur effect
    private void applyBlurEffect(Stage stage) {
        // Create a darkening effect (black overlay)
        ColorAdjust darken = new ColorAdjust();
        darken.setBrightness(-0.5); // Adjust for desired darkness

        // Create the Gaussian blur effect
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(15); // Adjust for desired blur intensity

        // Combine the darkening and blur effects
        Effect combinedEffect = new Blend(
                BlendMode.SRC_OVER,
                darken,
                blur
        );

        stage.getScene().getRoot().setEffect(combinedEffect);
    }

    private void removeBlurEffect(Stage stage) {
        stage.getScene().getRoot().setEffect(null);
    }


}



