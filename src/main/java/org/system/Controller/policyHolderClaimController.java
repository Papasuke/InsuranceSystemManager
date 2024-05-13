package org.system.Controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.system.Model.Claim;

public class policyHolderClaimController {
    @FXML
    private FontAwesomeIconView addClaim;

    @FXML
    private TableView<Claim> claimTable;
    @FXML
    private TableColumn<Claim, String> claimCol;

    @FXML
    private TableColumn<Claim, String> createCol;

    @FXML
    private TableColumn<Claim, String> cutsomerCol;

    @FXML
    private TableColumn<Claim, String> editCol;

    @FXML
    private TableColumn<Claim, String> exanCol;

    @FXML
    private TableColumn<Claim, String> idCol;

    @FXML
    private Button logoutButton;

    @FXML
    private FontAwesomeIconView refresh_button;

    @FXML
    private ScrollBar scrollBar;

    @FXML
    private FontAwesomeIconView searcgButton;

    @FXML
    private TextField searchBar;

    @FXML
    private TableColumn<?, ?> statusCol;

    @FXML
    private ComboBox<?> statusFilter;

}
