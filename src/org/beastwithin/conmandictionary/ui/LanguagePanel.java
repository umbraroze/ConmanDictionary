/*  LanguagePanel.java: Dictionary list and entry editor panel in main window.
 * 
 *  Conman's Dictionary, a dictionary application for conlang makers.
 *  Copyright (C) 2006,2007,2008,2009,2010,2013,2015,2016  Urpo Lankinen
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.beastwithin.conmandictionary.ui;

import org.beastwithin.conmandictionary.document.*;

import java.io.*;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

/**
 * Dictionary list and entry editor panel in main window. Consists of
 * the list of entries, a keyword entry panel, rich text editor for the
 * definition, and buttons to modify the entry with.
 * 
 * @author wwwwolf
 */
public class LanguagePanel extends VBox {
    // UI components
    @FXML private Text languageLabel;
    @FXML private SearchBox searchBox;
    @FXML private ListView definitionList;
    @FXML private TextField definitionTerm;
    @FXML private ComboBox wordClassDropDown;
    @FXML private ToggleButton flagButton;
    @FXML private TextArea definitionEditor;
    @FXML private ComboBox categoryDropDown;
    @FXML private Button addButton;
    @FXML private Button modifyButton;
    @FXML private Button deleteButton;

    // Constants
    private final String fxmlFile = "LanguagePanel.fxml";

    // Internal data model
    private EntryList entryList;
    private List<WordClass> wordClasses;
    private List<Category> categories;
    private ComboBoxModelWithNullChoice wordClassModel;
    private ComboBoxModelWithNullChoice categoriesModel;
    private LanguagePanelSearchBoxListener searchListener;
        
    public LanguagePanel() {
        // Load FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        entryList = new EntryList();
        clearWordClasses();
        clearCategories();
        searchListener = new LanguagePanelSearchBoxListener(this);
        entryList.setLanguage("");
    }

    public LanguagePanel(String language) {
        this();
        entryList.setLanguage(language);
        languageLabel.setText(language);
    }

    private class LanguagePanelSearchBoxListener implements SearchBoxListener {
        private LanguagePanel languagePanel;
        LanguagePanelSearchBoxListener(LanguagePanel lp) {
            languagePanel = lp;
        }
        public void searchBoxCleared() {
            languagePanel.clearListSelection();
        }

        public void searchBoxContentsChanged(String newContents) {
            languagePanel.searchForTerm(newContents);
        }
    }
    
    private void resetLanguageLabel() {
        this.languageLabel.setText(entryList.getLanguage());
    }

    /**
     * Sets the list language.
     * @param language Language.
     */
    public void setLanguage(String language) {
        entryList.setLanguage(language);
        resetLanguageLabel();
    }

    /**
     * Gets the list language.
     * @return Language.
     */
    public String getLanguage() {
        return this.languageLabel.getText();
    }

    /**
     * Called when a list item is picked for editing by
     * LanguagePanelListSelectionListener. 
     *
     */
    private void pickedListItemForEditing() {
        int idx = this.definitionList.getSelectionModel().getSelectedIndex();
        if (idx == -1) {
            return;
        }
        Entry e = (Entry) entryList.getElementAt(idx);
        editEntry(e);
    }

    /**
     * Fills the editor with the details from the specified editor.
     * 
     * @param e Entry to be edited.
     */
    private void editEntry(Entry e) {
        SelectionModel dd = wordClassDropDown.getSelectionModel();
        definitionTerm.setText(e.getTerm());
        definitionEditor.setText(e.getDefinition());
        flagButton.setSelected(e.isFlagged());
        if(e.getWordClass() == null)
            dd.selectFirst();
        else
            dd.select(e.getWordClass());
        if(e.getCategory() == null)
            dd.selectFirst();
        else
            dd.select(e.getCategory());
    }

    /**
     * Clears the editor.
     */
    public void clearEntries() {
        definitionTerm.setText("");
        definitionEditor.setText("");
        flagButton.setSelected(false);
        wordClassDropDown.getSelectionModel().selectFirst();
        categoryDropDown.getSelectionModel().selectFirst();
    }

    /**
     * Adds the values in the editor as a new entry in the list.
     */
    @FXML
    public void addDefinition() {
        String term = definitionTerm.getText();
        String definition = definitionEditor.getText();
        boolean flagged = flagButton.isSelected();
        
        WordClass newWordClass = null;
        if(wordClassDropDown.getSelectionModel().getSelectedIndex() != 0)
            newWordClass = (WordClass)wordClassDropDown.getSelectionModel().getSelectedItem();
        Category newCategory = null;
        if(categoryDropDown.getSelectionModel().getSelectedIndex() != 0)
            newCategory = (Category)categoryDropDown.getSelectionModel().getSelectedItem();

        Entry newTerm = new Entry(term, definition, flagged);
        if(newWordClass != null)
            newTerm.setWordClass(newWordClass);
        if(newCategory != null)
            newTerm.setCategory(newCategory);

        entryList.add(newTerm);
        entryList.sort();
    }

    /**
     * Deletes the selected entry.
     */
    @FXML
    public void deleteSelected() {
        // TODO: Confirmation dialog!
        int idx = this.definitionList.getSelectedIndex();
        if (idx == -1) {
            return;
        }
        this.entryList.remove(idx);
        this.entryList.sort();
        clearEntries();
    }

    /**
     * Update the selected item with the new details from the editor.
     */
    @FXML
    public void modifySelected() {
        int idx = this.definitionList.getSelectedIndex();
        if (idx == -1) {
            return;
        }
        Entry e = (Entry) entryList.getElementAt(idx);
        e.setTerm(this.definitionTerm.getText());
        e.setDefinition(this.definitionEditor.getText());
        e.setFlagged(this.flagButton.isSelected());
        WordClass newWordClass = null;
        if(wordClassDropDown.getSelectedIndex() != 0)
            newWordClass = (WordClass)this.wordClassDropDown.getSelectedItem();
        e.setWordClass(newWordClass);
        Category newCategory = null;
        if(categoryDropDown.getSelectedIndex() != 0)
            newCategory = (Category)this.categoryDropDown.getSelectedItem();
        e.setCategory(newCategory);

        this.entryList.sort();
        this.definitionList.repaint();
    }

    /**
     * Returns the list of entries.
     * @return The entry list.
     */
    public EntryList getEntryList() {
        return entryList;
    }

    public void setEntryList(EntryList l) {
        entryList = l;
        definitionList.setModel(entryList);
        resetLanguageLabel();
    }
    
    public void clearWordClasses() {
        int previousEnd = 0;
        if(wordClasses != null)
            previousEnd = wordClasses.size()-1;
        wordClasses = null;
        Vector<String> v = new Vector<String>();
        wordClassModel = new ComboBoxModelWithNullChoice(v);
        if(previousEnd > 0)
            wordClassDropDown.contentsChanged(new ListDataEvent(v, ListDataEvent.INTERVAL_REMOVED, 0, previousEnd));
    }
    public void setWordClasses(List<WordClass> wordClasses) {
        /*
        int previousEnd = 0;
        if(wordClasses != null)
            previousEnd = wordClasses.size()-1;
         */
        this.wordClasses = wordClasses;
        // Damn you and your vectors
        Vector<WordClass> v = new Vector<WordClass>(this.wordClasses);
        wordClassModel = new ComboBoxModelWithNullChoice(v);
        wordClassDropDown.setModel(wordClassModel);
        //wordClassDropDown.contentsChanged(new ListDataEvent(v, ListDataEvent.CONTENTS_CHANGED, 0, v.size() - 1));
    }
    public void wordClassesChanged() {
        setWordClasses(wordClasses);
    }
    public List<WordClass> getWordClasses() {
        return this.wordClasses;
    }

    public void clearCategories() {
        int previousEnd = 0;
        if(categories != null)
            previousEnd = categories.size()-1;
        categories = null;
        Vector<String> v = new Vector<String>();
        categoriesModel = new ComboBoxModelWithNullChoice(v);
        if(previousEnd > 0)
            categoryDropDown.contentsChanged(new ListDataEvent(v, ListDataEvent.INTERVAL_REMOVED, 0, previousEnd));
    }
    public void setCategories(List<Category> categories) {
        /*
        int previousEnd = 0;
        if(wordClasses != null)
            previousEnd = wordClasses.size()-1;
         */
        this.categories = categories;
        // Damn you and your vectors
        Vector<Category> v = new Vector<Category>(this.categories);
        categoriesModel = new ComboBoxModelWithNullChoice(v);
        categoryDropDown.setModel(categoriesModel);
    }
    public void categoriesChanged() {
        setCategories(categories);
    }
    public List<Category> getCategories() {
        return this.categories;
    }


    /**
     * Clear all entries in this list.
     */
    public void clearList() {
        this.entryList.clear();
        this.clearEntries();
    }

    public void searchForTerm(String term) {
        Entry x = entryList.search(term);
        definitionList.getSelectionModel().select(x);
    }

    public void clearListSelection() {
    //FIXME: Should none be selected? How do I do that anyway?
    //this.definitionList.setSelectedValue(null,false);
    }


}
