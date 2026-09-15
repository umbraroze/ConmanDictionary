package org.beastwithin.conmandictionary.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class MainWindowController {
    /** Location of the main window FXML file. */
    public static final String FXML_FILE = "ui/MainWindow.fxml";
    /** Main window title. */
    public static final String WINDOW_TITLE = "Conman's Dictionary";

    @FXML public Text mainLabel;

    @FXML
    public void testAction(ActionEvent actionEvent) {
        mainLabel.setText("Hello world!");
    }
}
