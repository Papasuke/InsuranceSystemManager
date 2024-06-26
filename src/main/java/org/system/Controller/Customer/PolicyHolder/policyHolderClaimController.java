package org.system.Controller.Customer.PolicyHolder;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import org.system.Model.Claim;
import org.system.DataConnection.SupabaseJDBC;
import org.system.utils.SceneController;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.system.Controller.SharedVariable.loggedInPolicyHolder;
import static org.system.utils.UIEffects.applyBlurEffect;
import static org.system.utils.UIEffects.removeBlurEffect;

public class policyHolderClaimController implements Initializable {
    @FXML
    private FontAwesomeIconView addClaim;

    @FXML
    private TableView<Claim> claimTable;
    @FXML
    private TableColumn<Claim, String> claimCol;

    @FXML
    private TableColumn<Claim, String> createCol;

    @FXML
    private TableColumn<Claim, String> customerCol;

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
    private FontAwesomeIconView searchButton;

    @FXML
    private TextField searchBar;

    @FXML
    private TableColumn<Claim, String> statusCol;

    @FXML
    private ComboBox<String> statusFilter;

    @FXML
    private AnchorPane mainMenu;

    @FXML
    private AnchorPane memberMenu;
    @FXML
    private AnchorPane claimMenu;
    @FXML
    private Text displayName;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Claim claim = null;

    ObservableList<Claim> claimList = FXCollections.observableArrayList();
    FilteredList<Claim> filteredData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayName.setText(loggedInPolicyHolder.getFullName().split(" ")[0]);
        loadData();
        setupSearchFilter();
        setupStatusFilter();
    }

    private void loadData() {
        connection = SupabaseJDBC.mintDatabase();
        refreshTable();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("insuredPerson"));
        createCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        exanCol.setCellValueFactory(new PropertyValueFactory<>("examDate"));
        claimCol.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        refresh_button.setOnMouseClicked((MouseEvent event) -> refreshTable());
        claimMenu.setOnMouseClicked((MouseEvent event) -> refreshTable());
        Callback<TableColumn<Claim, String>, TableCell<Claim, String>> cellFactory = (TableColumn<Claim, String> param) -> new TableCell<Claim, String>() {
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

                    deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                        try {
                            claim = claimTable.getSelectionModel().getSelectedItem();
                            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete Claim ID: " + claim.getId() + " ?", ButtonType.YES, ButtonType.NO);
                            confirmAlert.showAndWait();
                            if (confirmAlert.getResult() == ButtonType.YES) {
                                query = "DELETE FROM \"claims\" WHERE \"id\"=" + claim.getId();
                                connection = SupabaseJDBC.mintDatabase();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(policyHolderClaimController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                    editIcon.setOnMouseClicked((MouseEvent event) -> {
                        claim = claimTable.getSelectionModel().getSelectedItem();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/Fxml/Customer/PolicyHolder/addClaim.fxml"));
                        try {
                            loader.load();
                        } catch (IOException ex) {
                            Logger.getLogger(policyHolderClaimController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        addClaimCustomer addClaimCustomer = loader.getController();
                        addClaimCustomer.setUpdate(true);
                        addClaimCustomer.setTextField(claim.getId(), claim.getInsuredPerson(),claim.getBankName(), claim.getBankAccountName(), claim.getBankAccountNum(), claim.getClaimAmount(), claim.getDescription());
                        Parent parent = loader.getRoot();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(parent));
                        stage.initStyle(StageStyle.UTILITY);
                        stage.show();
                        refreshTable();
                    });

                    HBox managebtn = new HBox(editIcon, deleteIcon);
                    managebtn.setStyle("-fx-alignment:center");
                    HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                    HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                    setGraphic(managebtn);
                    setText(null);
                }
            }
        };
        editCol.setCellFactory(cellFactory);
        claimTable.setItems(claimList);
    }

    private void setupSearchFilter() {
        filteredData = new FilteredList<>(claimList, b -> true);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(claim -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (claim.getInsuredPerson().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (claim.getBankName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (claim.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(claim.getId()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(claim.getClaimAmount()).contains(lowerCaseFilter)) {
                    return true;
                } else if (claim.getStatus().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });

            SortedList<Claim> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(claimTable.comparatorProperty());
            claimTable.setItems(sortedData);
        });
    }

    @FXML
    private void setupStatusFilter() {
        if (statusFilter != null) {
            statusFilter.getItems().clear();
            statusFilter.getItems().addAll("Processing", "Approve", "Reject", "All");
            statusFilter.valueProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(claim -> {
                    if (newValue == null || newValue.equals("All")) {
                        return true;
                    }
                    return claim.getStatus().equals(newValue);
                });

                SortedList<Claim> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(claimTable.comparatorProperty());
                claimTable.setItems(sortedData);
            });
        } else {
            Logger.getLogger(policyHolderClaimController.class.getName()).log(Level.SEVERE, "statusFilter ComboBox is null");
        }
    }

    private void refreshTable() {
        try {
            claimList.clear();
            query = "SELECT * FROM \"claims\"";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                claimList.add(new Claim(
                        resultSet.getInt("id"),
                        resultSet.getString("insuredPerson"),
                        resultSet.getDate("createDate"),
                        resultSet.getDate("examDate"),
                        resultSet.getString("bankName"),
                        resultSet.getString("bankAccountName"),
                        resultSet.getString("bankAccountNum"),
                        resultSet.getInt("claimAmount"),
                        resultSet.getString("description"),
                        resultSet.getString("status")));
            }
            resultSet.close();
            setupSearchFilter(); // Refresh search filter after loading data
            setupStatusFilter(); // Refresh status filter after loading data
        } catch (SQLException ex) {
            Logger.getLogger(policyHolderClaimController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void close(MouseEvent event) {
        javafx.application.Platform.exit();
    }


    @FXML
    private void getAddView(MouseEvent event) {
        try {
            // Load the FXML for the edit view
            Parent addView = FXMLLoader.load(getClass().getResource("/Fxml/Customer/PolicyHolder/addClaim.fxml"));

            // Create a new scene for the edit view
            Scene editScene = new Scene(addView);

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

        } catch (IOException ex) {
            Logger.getLogger(policyHolderClaimController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
    private void memberMenuClick(MouseEvent event) {
        try {
            SceneController.switchSceneCustomer(event, "PolicyHolderMembers");
        } catch (IOException e) {
            System.err.println("Error switching scene: " + e.getMessage());
        }
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


};
