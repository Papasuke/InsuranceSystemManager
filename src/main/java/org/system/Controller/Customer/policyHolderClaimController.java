package org.system.Controller.Customer;

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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.system.Controller.SharedVariable;
import org.system.Model.Claim;
import org.system.DataConnection.SupabaseJDBC;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private FontAwesomeIconView searchButton;

    @FXML
    private TextField searchBar;

    @FXML
    private TableColumn<Claim, String> statusCol;

    @FXML
    private ComboBox<String> statusFilter;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Claim claim = null;

    ObservableList<Claim> claimList = FXCollections.observableArrayList();
    FilteredList<Claim> filteredData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        setupSearchFilter();
        setupStatusFilter();
    }

    private void loadData() {
        connection = SupabaseJDBC.mintDatabase();
        refreshTable();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        cutsomerCol.setCellValueFactory(new PropertyValueFactory<>("insuredPerson"));
        createCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        exanCol.setCellValueFactory(new PropertyValueFactory<>("examDate"));
        claimCol.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        refresh_button.setOnMouseClicked((MouseEvent event) -> refreshTable());

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
                        loader.setLocation(getClass().getResource("/Fxml/Customer/addClaim.fxml"));
                        try {
                            loader.load();
                        } catch (IOException ex) {
                            Logger.getLogger(policyHolderClaimController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        addClaimCustomer addClaimCustomer = loader.getController();
                        addClaimCustomer.setUpdate(true);
                        addClaimCustomer.setTextField(claim.getId(), claim.getBankName(), claim.getBankAccount(), claim.getGetBankAccountName(), claim.getClaimAmount(), claim.getDescription());
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

    private void setupStatusFilter() {
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
                        resultSet.getString("bankAccount"),
                        resultSet.getString("getBankAccountName"),
                        resultSet.getInt("claimAmount"),
                        resultSet.getString("description"),
                        resultSet.getString("status")));
            }
            setupSearchFilter(); // Refresh search filter after loading data
            setupStatusFilter(); // Refresh status filter after loading data
        } catch (SQLException ex) {
            Logger.getLogger(policyHolderClaimController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void close(MouseEvent event) {
        SharedVariable.openOnce = false;
        javafx.application.Platform.exit();
    }


    @FXML
    private void getAddView(MouseEvent event) {
        try {
            if(!SharedVariable.openOnce) {
                Parent parent = FXMLLoader.load(getClass().getResource("/Fxml/Customer/addClaim.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.initModality(Modality.APPLICATION_MODAL); // This line makes the new stage modal
                stage.show();
                SharedVariable.openOnce = true;
            }

        } catch (IOException ex) {
            Logger.getLogger(policyHolderClaimController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

};
