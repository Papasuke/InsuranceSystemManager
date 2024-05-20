package org.system.Controller.Customer.Dependent;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.system.Controller.SharedVariable;
import org.system.utils.SceneController;

import java.io.IOException;

public class ClaimMenuController {

    @FXML
    private TableColumn<?, ?> claimCol;

    @FXML
    private AnchorPane claimMenu;

    @FXML
    private TableView<?> claimTable;

    @FXML
    private FontAwesomeIconView close;

    @FXML
    private TableColumn<?, ?> createCol;

    @FXML
    private TableColumn<?, ?> customerCol;

    @FXML
    private Text displayName;

    @FXML
    private TableColumn<?, ?> editCol;

    @FXML
    private TableColumn<?, ?> exanCol;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private Button logoutButton;

    @FXML
    private AnchorPane mainMenu;

    @FXML
    private FontAwesomeIconView refresh_button;

    @FXML
    private TextField searchBar;

    @FXML
    private FontAwesomeIconView searchButton;

    @FXML
    private TableColumn<?, ?> statusCol;

    @FXML
    private ComboBox<?> statusFilter;

    @FXML
    void handleMainMenuClick(MouseEvent event) {
        try {
            SceneController.switchScene(event, "/Fxml/Customer/Dependent/dependentDashBoard.fxml");
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

}
