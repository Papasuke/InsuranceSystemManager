package org.system.utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    public static void switchSceneCustomer(MouseEvent event, String sceneName) throws IOException {
        Parent root = FXMLLoader.load(SceneController.class.getResource("/Fxml/Customer/PolicyHolder/" + sceneName + ".fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        // Center the stage
        stage.centerOnScreen();
        stage.show();
    }
}
