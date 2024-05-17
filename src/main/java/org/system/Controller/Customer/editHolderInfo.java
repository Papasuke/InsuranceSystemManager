package org.system.Controller.Customer;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.system.Controller.SharedVariable;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class editHolderInfo {
    @FXML
    private Button backButton;

    @FXML
    private FontAwesomeIconView closeButton;

    @FXML
    private TextField emailFld;

    @FXML
    private Text errorText;

    @FXML
    private TextField feeFld;

    @FXML
    private TextField fullNameFld;

    @FXML
    private TextField idFld;

    @FXML
    private TextField phoneFld;

    @FXML
    private Button saveButton;

    @FXML
    private TextField usernameFld;
    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
