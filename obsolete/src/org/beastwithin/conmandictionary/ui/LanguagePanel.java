package org.beastwithin.conmandictionary.ui;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.text.*;

public class LanguagePanel {
    @FXML public Text languageLabel;
    @FXML public SearchBox searchBox;
    @FXML public ListView definitionList;
    @FXML public TextField definitionTerm;
    @FXML public ComboBox wordClassDropDown;
    @FXML public ToggleButton flagButton;
    @FXML public TextArea definitionEditor;
    @FXML public ComboBox categoryDropDown;

    @FXML
    public void addDefinition(ActionEvent actionEvent) {
        System.err.println("WARNING: addDefinition unimplemented");
    }

    @FXML
    public void modifySelected(ActionEvent actionEvent) {
        System.err.println("WARNING: modifySelected unimplemented");
    }

    @FXML
    public void deleteSelected(ActionEvent actionEvent) {
        System.err.println("WARNING: deleteSelected unimplemented");
    }
}
