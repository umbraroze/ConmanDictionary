package org.beastwithin.conmandictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ConmanDictionaryJFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL fxml = getClass().getResource("ui/MainWindow.fxml");
        if(fxml == null) {
            throw new IOException("Can't find main window FXML.");
        }
        Parent root = FXMLLoader.load(fxml);

        Scene scene = new Scene(root, 300, 275);

        primaryStage.setTitle("FXML Welcome");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
