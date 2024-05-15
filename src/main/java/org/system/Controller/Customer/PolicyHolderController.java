package org.system.Controller.Customer;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    }
    @FXML
    private void close(MouseEvent event) {
        javafx.application.Platform.exit();
    }
    @FXML
    private void claimMenuClick(MouseEvent event) {
        try {
            // Load the new FXML
            Parent parent = FXMLLoader.load(getClass().getResource("/Fxml/Customer/PolicyHolderClaim.fxml"));
            Scene newScene = new Scene(parent);

            // Get the current stage using the mainMenu AnchorPane
            Stage currentStage = (Stage) claimMenu.getScene().getWindow();

            // Create the new stage and set the scene
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.initModality(Modality.APPLICATION_MODAL); // Optional: Make the new stage modal if needed
            newStage.initStyle(StageStyle.UNDECORATED); // Optional: Set new stage style if needed
            newStage.show();

            // Close the current stage
            currentStage.close();

        } catch (IOException ex) {
            Logger.getLogger(policyHolderClaimController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
