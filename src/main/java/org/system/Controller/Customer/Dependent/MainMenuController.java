package org.system.Controller.Customer.Dependent;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.system.Controller.SharedVariable;
import org.system.utils.AdvancedFunction;
import org.system.utils.SceneController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.String.valueOf;
import static org.system.Controller.SharedVariable.*;

public class MainMenuController implements Initializable {

    @FXML
    private AnchorPane claimMenu;

    @FXML
    private FontAwesomeIconView closeButton;

    @FXML
    private Text customerId;

    @FXML
    private Text displayName;

    @FXML
    private Text emailFld;

    @FXML
    private Text fullnameFld;

    @FXML
    private Label holderId;

    @FXML
    private Text insuranceFeeFld;

    @FXML
    private Button logoutButton;

    @FXML
    private Label memberFee;

    @FXML
    private Text phoneFld;

    @FXML
    private Label totalClaim;

    @FXML
    private Text usernameFld;

    @FXML
    void claimMenuClick(MouseEvent event) {
        try {
            SceneController.switchScene(event, "/Fxml/Customer/Dependent/dependentClaim.fxml");
        } catch (IOException e) {
            System.err.println("Error switching scene: " + e.getMessage());
        }
    }

    @FXML
    void close(MouseEvent event) {
        javafx.application.Platform.exit();
    }

    @FXML
    private void logOut(MouseEvent event) {
        SharedVariable.resetValue();
        try {
            SceneController.switchScene(event, "/Fxml/Login.fxml");
        } catch (IOException e) {
            System.err.println("Error switching scene: " + e.getMessage());

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (SharedVariable.loggedInAccount != null && SharedVariable.loggedInAccount.getAccType().equals("DEPENDENT")) {
            try {
                // 1. Fetch Policyholder data using the account ID
                SharedVariable.loggedInDependent = AdvancedFunction.getDependentFromDatabase(SharedVariable.loggedInAccount.getId());

                // 2. Populate FXML fields
                customerId.setText(SharedVariable.loggedInDependent.getId());
                fullnameFld.setText(SharedVariable.loggedInDependent.getFullName()); // Corrected to use getFullName()
                emailFld.setText(SharedVariable.loggedInDependent.getEmail());
                phoneFld.setText(SharedVariable.loggedInDependent.getPhone());
                usernameFld.setText(SharedVariable.loggedInDependent.getUsername());
                insuranceFeeFld.setText(valueOf(SharedVariable.loggedInDependent.getInsuranceFee()) + "$");
                displayName.setText(SharedVariable.loggedInDependent.getFullName().split(" ")[0]);
                holderId.setText(SharedVariable.loggedInDependent.getPolicyHolderId());
                memberFee.setText(valueOf(SharedVariable.loggedInDependent.getInsuranceFee()));
            } catch (Exception e) {
                // Handle potential exceptions during data fetching
                System.err.println("Error fetching policyholder data: " + e.getMessage());
            }
        } else {
            // Handle cases where there's no logged-in account or the account is not a Policyholder
            System.err.println("No logged-in Policyholder account found.");
        }
        System.out.println(SharedVariable.loggedInDependent.toString());
    }
}
