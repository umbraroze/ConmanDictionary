package org.beastwithin.conmandictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import org.beastwithin.conmandictionary.ui.*;

public class ConmanDictionary extends Application {

    private static SearchBoxListener mainSearch;
    private class TestSearchBoxListener implements SearchBoxListener {
        @Override
        public void searchBoxCleared() {
            System.out.println("TEST LISTENER: Cleared search box");
        }

        @Override
        public void searchBoxContentsChanged(String newContents) {
            System.out.println("TEST LISTENER: Search: "+newContents);
        }
    }

    //////////////////////////////////////////////////////////////////////

    /**
     * Main application entry point.
     *
     * @param args Command line arguments from JVM.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initialise the JavaFX application.
     *
     * Loads the main window (from file specified at MAIN_WINDOW_FXML) to primaryStage
     *
     * @param primaryStage The primary JavaFX stage, to be loaded from main window FXML file.
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        URL fxml = getClass().getResource(MainWindowController.FXML_FILE);
        if(fxml == null) {
            throw new IOException("Can't find main window FXML.");
        }
        Parent root = FXMLLoader.load(fxml);

        Scene scene = new Scene(root, 600, 320);

        primaryStage.setTitle(MainWindowController.WINDOW_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
