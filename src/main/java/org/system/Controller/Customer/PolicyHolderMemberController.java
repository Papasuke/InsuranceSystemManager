package org.system.Controller.Customer;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.util.ResourceBundle;

public class PolicyHolderMemberController implements Initializable {

    @FXML
    private FontAwesomeIconView addMember;

    @FXML
    private TableColumn<?, ?> editCol;

    @FXML
    private TableColumn<?, ?> emailCol;

    @FXML
    private TableColumn<?, ?> feeCol;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> phoneCol;

    @FXML
    private FontAwesomeIconView refresh_button;

    @FXML
    private TableColumn<?, ?> usernameCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
