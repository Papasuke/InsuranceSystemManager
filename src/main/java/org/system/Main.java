package org.system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.system.Application.testingApp;
import org.system.DataConnection.SupabaseJDBC;

import java.io.IOException;

import static javafx.application.Application.launch;

public class Main extends Application {
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(testingApp.class.getResource("/Fxml/Login.fxml"));
        stage.initStyle(StageStyle.UTILITY);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Mint");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}