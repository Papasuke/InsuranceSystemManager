package org.system.Controller.Customer;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PolicyHolderMemberController implements Initializable {

    @FXML
    private FontAwesomeIconView addMember;

    @FXML
    private AnchorPane claimMenu;

    @FXML
    private FontAwesomeIconView closeButton;

    @FXML
    private TableColumn<?, ?> editCol;

    @FXML
    private TableColumn<?, ?> emailCol;

    @FXML
    private TableColumn<?, ?> feeCol;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private AnchorPane mainMenu;

    @FXML
    private AnchorPane memberMenu;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> phoneCol;

    @FXML
    private FontAwesomeIconView refresh_button;

    @FXML
    private TextField searchBar;

    @FXML
    private TableColumn<?, ?> usernameCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    private void close(MouseEvent event) {
        javafx.application.Platform.exit();
    }
    @FXML
    private void handleMainMenuClick(MouseEvent event) {
        try {
            // Load the new FXML
            Parent parent = FXMLLoader.load(getClass().getResource("/Fxml/Customer/policyHolderDashBoard.fxml"));
            Scene newScene = new Scene(parent);

            // Get the current stage using the mainMenu AnchorPane
            Stage currentStage = (Stage) mainMenu.getScene().getWindow();

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
