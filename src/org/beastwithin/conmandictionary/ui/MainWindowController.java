package org.beastwithin.conmandictionary.ui;

import javafx.event.ActionEvent;
import javafx.scene.text.Text;

public class MainWindowController {
    public Text mainLabel;

    public void testAction(ActionEvent actionEvent) {
        mainLabel.setText("Hello world!");
    }
}
