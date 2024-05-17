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
import org.system.utils.SceneController;

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
            SceneController.switchSceneCustomer(event, "policyHolderDashBoard");
        } catch (IOException e) {
            System.err.println("Error switching scene: " + e.getMessage());
        }
    }
    @FXML
    private void claimMenuClick(MouseEvent event) {
        try {
            SceneController.switchSceneCustomer(event, "PolicyHolderClaim");
        } catch (IOException e) {
            System.err.println("Error switching scene: " + e.getMessage());
        }
    }
}
