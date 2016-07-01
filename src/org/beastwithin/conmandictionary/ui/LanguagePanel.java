/*  LanguagePanel.java: Dictionary list and entry editor panel in main window.
 * 
 *  Conman's Dictionary, a dictionary application for conlang makers.
 *  Copyright (C) 2006,2007,2008,2009,2010,2013,2015  Urpo Lankinen
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
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Dictionary list and entry editor panel in main window. Consists of
 * the list of entries, a keyword entry panel, rich text editor for the
 * definition, and buttons to modify the entry with.
 * 
 * @author wwwwolf
 */
public class LanguagePanel extends javax.swing.JPanel {

    private class EntryListModel extends DefaultListModel {
        // FIXME: Unused as of yet
        // This should implement the Swing stuff currently in EntryList.java
    }
    
    private EntryList entryList;
    private List<WordClass> wordClasses;
    private List<Category> categories;
    private ComboBoxModelWithNullChoice wordClassModel;
    private ComboBoxModelWithNullChoice categoriesModel;
    private LanguagePanelSearchBoxListener searchListener;
        
    public LanguagePanel() {
        super();
        System.out.println("LanguagePanel created");
        entryList = new EntryList();
        clearWordClasses();
        clearCategories();
        searchListener = new LanguagePanelSearchBoxListener(this);
        //initComponents();
        entryList.setLanguage("");
    }

    public LanguagePanel(String language) {
        this();
        entryList.setLanguage(language);
        languageLabel.setText(language);
    }

    // Components

    private JPanel languagePanel;

    private JLabel languageLabel;

    private SearchBox searchBox;

    private JList definitionList;

    private JTextField definitionTerm;
    private JComboBox wordClassDropDown;
    private JToggleButton flagButton;

    private JEditorPane definitionEditor;

    private JComboBox categoryDropDown;

    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;

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
        int idx = this.definitionList.getSelectedIndex();
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
        this.definitionTerm.setText(e.getTerm());
        this.definitionEditor.setText(e.getDefinition());
        this.flagButton.setSelected(e.isFlagged());
        if(e.getWordClass() == null)
            this.wordClassDropDown.setSelectedIndex(0);
        else
            this.wordClassDropDown.setSelectedItem(e.getWordClass());
        if(e.getCategory() == null)
            this.categoryDropDown.setSelectedIndex(0);
        else
            this.categoryDropDown.setSelectedItem(e.getCategory());
    }

    /**
     * Clears the editor.
     */
    public void clearEntries() {
        this.definitionTerm.setText("");
        this.definitionEditor.setText("");
        this.flagButton.setSelected(false);
        this.wordClassDropDown.setSelectedIndex(0);
        this.categoryDropDown.setSelectedIndex(0);
    }

    /**
     * Adds the values in the editor as a new entry in the list.
     */
    public void addDefinition() {
        String term = this.definitionTerm.getText();
        String definition = this.definitionEditor.getText();
        boolean flagged = this.flagButton.isSelected();
        
        WordClass newWordClass = null;
        if(wordClassDropDown.getSelectedIndex() != 0)
            newWordClass = (WordClass)this.wordClassDropDown.getSelectedItem();
        Category newCategory = null;
        if(categoryDropDown.getSelectedIndex() != 0)
            newCategory = (Category)this.categoryDropDown.getSelectedItem();

        Entry newTerm = new Entry(term, definition, flagged);
        if(newWordClass != null)
            newTerm.setWordClass(newWordClass);
        if(newCategory != null)
            newTerm.setCategory(newCategory);

        this.entryList.add(newTerm);
        this.entryList.sort();
    }

    /**
     * Deletes the selected entry.
     */
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
        this.definitionList.setSelectedValue(x, true);
    }

    public void clearListSelection() {
    //FIXME: Should none be selected? How do I do that anyway?
    //this.definitionList.setSelectedValue(null,false);
    }


}
