package org.system.Controller.Customer.PolicyHolder;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.system.Controller.SharedVariable;
import org.system.DataConnection.SupabaseJDBC;
import org.system.Model.Dependent;
import org.system.utils.SceneController;
import org.system.utils.UIEffects;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private Text displayName;

    @FXML
    private TableColumn<Dependent, String> editCol;

    @FXML
    private TableColumn<Dependent, String> emailCol;

    @FXML
    private TableColumn<Dependent, Integer> feeCol;

    @FXML
    private TableColumn<Dependent, Integer> idCol;

    @FXML
    private Button logoutButton;

    @FXML
    private AnchorPane mainMenu;

    @FXML
    private AnchorPane memberMenu;

    @FXML
    private TableColumn<Dependent, String> nameCol;

    @FXML
    private TableColumn<Dependent, String> phoneCol;

    @FXML
    private FontAwesomeIconView refresh_button;

    @FXML
    private TextField searchBar;

    @FXML
    private TableColumn<Dependent, String> usernameCol;

    @FXML
    private TableView<Dependent> memberTable;

    ObservableList<Dependent> dependentList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        // Initialize the table columns
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        feeCol.setCellValueFactory(new PropertyValueFactory<>("insuranceFee"));

        // Add edit and delete buttons to the table
        addEditDeleteButtons();

        // Load dependents from the database
        loadDependents();
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

    @FXML
    private void getAddView(MouseEvent event) {
        try {
            // Load the FXML for the add view
            Parent addView = FXMLLoader.load(getClass().getResource("/Fxml/Customer/PolicyHolder/addMember.fxml"));

            // Create a new scene for the add view
            Scene addScene = new Scene(addView);

            // Create a new stage for the add window
            Stage addStage = new Stage();
            addStage.setScene(addScene);

            // Customize the add window (optional)
            addStage.initStyle(StageStyle.UTILITY);
            addStage.initModality(Modality.APPLICATION_MODAL); // Prevent interaction with main window

            // Get the primary stage (for blur effect if needed)
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Apply blur effect to the primary stage (if needed)
            UIEffects.applyBlurEffect(primaryStage);

            // Show the add window
            addStage.showAndWait(); // Wait for the add window to close

            // Remove blur effect after the add window is closed (if needed)
            UIEffects.removeBlurEffect(primaryStage);

            // Reload dependents and refresh the table
            loadDependents();

        } catch (IOException ex) {
            Logger.getLogger(PolicyHolderMemberController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadDependents() {
        dependentList.clear();

        String query = "SELECT * FROM Dependent WHERE policyHolder_id = ?";

        try (Connection conn = SupabaseJDBC.mintDatabase();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, SharedVariable.loggedInPolicyHolder.getId());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Dependent dependent = new Dependent(
                        rs.getString("dependent_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("accType"),
                        rs.getString("policyHolder_id"),
                        rs.getString("fullname"),
                        rs.getInt("insuranceFee")
                );
                dependentList.add(dependent);
            }
            memberTable.setItems(dependentList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addEditDeleteButtons() {
        Callback<TableColumn<Dependent, String>, TableCell<Dependent, String>> cellFactory = (TableColumn<Dependent, String> param) -> new TableCell<Dependent, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                    FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                    deleteIcon.setStyle("-fx-cursor: hand; -glyph-size:20px; -fx-fill:#ff1744;");
                    editIcon.setStyle("-fx-cursor: hand; -glyph-size:20px; -fx-fill:#00E676;");

//                    deleteIcon.setOnMouseClicked((MouseEvent event) -> {
//                        Dependent dependent = getTableView().getItems().get(getIndex());
//                        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete Member ID: " + dependent.getId() + " ?", ButtonType.YES, ButtonType.NO);
//                        confirmAlert.showAndWait();
//                        if (confirmAlert.getResult() == ButtonType.YES) {
//                            String query = "DELETE FROM Dependent WHERE id = ?";
//                            try (Connection conn = SupabaseJDBC.mintDatabase();
//                                 PreparedStatement pstmt = conn.prepareStatement(query)) {
//
//                                pstmt.setInt(1, dependent.getId());
//                                pstmt.executeUpdate();
//
//                                // Reload dependents and refresh the table
//                                loadDependents();
//
//                            } catch (SQLException e) {
//                                Logger.getLogger(PolicyHolderMemberController.class.getName()).log(Level.SEVERE, null, e);
//                            }
//                        }
//                    });
//
//                    editIcon.setOnMouseClicked((MouseEvent event) -> {
//                        Dependent dependent = getTableView().getItems().get(getIndex());
//                        FXMLLoader loader = new FXMLLoader();
//                        loader.setLocation(getClass().getResource("/Fxml/Customer/PolicyHolder/editMember.fxml"));
//                        try {
//                            loader.load();
//                        } catch (IOException ex) {
//                            Logger.getLogger(PolicyHolderMemberController.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//
//                        EditMemberController editMemberController = loader.getController();
//                        editMemberController.setDependent(dependent);
//                        Parent parent = loader.getRoot();
//                        Stage stage = new Stage();
//                        stage.setScene(new Scene(parent));
//                        stage.initStyle(StageStyle.UTILITY);
//                        stage.show();
//                        loadDependents();
//                    });

                    HBox managebtn = new HBox(editIcon, deleteIcon);
                    managebtn.setStyle("-fx-alignment:center");
                    HBox.setMargin(deleteIcon, new javafx.geometry.Insets(2, 2, 0, 3));
                    HBox.setMargin(editIcon, new javafx.geometry.Insets(2, 3, 0, 2));

                    setGraphic(managebtn);
                    setText(null);
                }
            }
        };
        editCol.setCellFactory(cellFactory);
    }

    private void handleEditAction(Dependent dependent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Customer/PolicyHolder/editMember.fxml"));
            Parent editView = loader.load();

//            EditMemberController controller = loader.getController();
//            controller.setDependent(dependent);

            Scene editScene = new Scene(editView);
            Stage editStage = new Stage();
            editStage.setScene(editScene);
            editStage.initStyle(StageStyle.UTILITY);
            editStage.initModality(Modality.APPLICATION_MODAL);

            Stage primaryStage = (Stage) memberTable.getScene().getWindow();
            UIEffects.applyBlurEffect(primaryStage);

            editStage.showAndWait();
            UIEffects.removeBlurEffect(primaryStage);

            // Reload dependents and refresh the table
            loadDependents();

        } catch (IOException ex) {
            Logger.getLogger(PolicyHolderMemberController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleDeleteAction(Dependent dependent) {
        String query = "DELETE FROM Dependent WHERE dependent_id = ?";

        try (Connection conn = SupabaseJDBC.mintDatabase();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, dependent.getId());
            pstmt.executeUpdate();

            // Reload dependents and refresh the table
            loadDependents();

        } catch (SQLException e) {
            Logger.getLogger(PolicyHolderMemberController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
