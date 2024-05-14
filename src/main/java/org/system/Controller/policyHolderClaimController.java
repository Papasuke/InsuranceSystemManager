package org.system.Controller;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    private FontAwesomeIconView searcgButton;

    @FXML
    private TextField searchBar;

    @FXML
    private TableColumn<?, ?> statusCol;

    @FXML
    private ComboBox<?> statusFilter;

    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Claim claim = null ;

    ObservableList<Claim> claimList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    private void loadData() {
        connection = SupabaseJDBC.mintDatabase();
        cutsomerCol.setCellValueFactory(new PropertyValueFactory<>("insuredPerson"));
        refreshTable();

    }

    private void refreshTable() {
        try {
            claimList.clear();

            query = "SELECT * FROM \"claims\"";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){

                claimList.add(new Claim(
                        resultSet.getString("insuredPerson")));
                System.out.println(resultSet.getString("insuredPerson"));
                claimTable.setItems(claimList);
            }


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




}
