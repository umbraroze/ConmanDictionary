/*  LanguagePanel.java: Dictionary list and entry editor panel in main window.
 * 
 *  Conman's Dictionary, a dictionary application for conlang makers.
 *  Copyright (C) 2006,2007,2008,2009,2010,2013  Urpo Lankinen
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
import javax.swing.DefaultListModel;
import javax.swing.event.ListDataEvent;

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
        entryList = new EntryList();
        clearWordClasses();
        clearCategories();
        searchListener = new LanguagePanelSearchBoxListener(this);
        initComponents();
        entryList.setLanguage("");
    }

    public LanguagePanel(String language) {
        this();
        entryList.setLanguage(language);
        languageLabel.setText(language);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        languageLabel = new javax.swing.JLabel();
        searchBox = new org.beastwithin.conmandictionary.ui.SearchBox();
        definitionListScrollPane = new javax.swing.JScrollPane();
        definitionList = new javax.swing.JList();
        languagePanelSeparator1 = new javax.swing.JSeparator();
        definitionPanel = new javax.swing.JPanel();
        definitionTerm = new javax.swing.JTextField();
        wordClassDropDown = new javax.swing.JComboBox();
        flagButton = new javax.swing.JToggleButton();
        definitionEditorScrollPane = new javax.swing.JScrollPane();
        definitionEditor = new javax.swing.JEditorPane();
        categoryDropDown = new javax.swing.JComboBox();
        languagePanelSeparator2 = new javax.swing.JSeparator();
        buttonPanel = new javax.swing.JPanel();
        addButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        modifyButton = new javax.swing.JButton();

        languageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/beastwithin/conmandictionary/ui/UIMessages"); // NOI18N
        languageLabel.setText(bundle.getString("LanguagePanel.languageLabelDefault")); // NOI18N

        definitionList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        definitionListScrollPane.setViewportView(definitionList);

        definitionTerm.setText("jTextField1");

        wordClassDropDown.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        flagButton.setText("F");

        javax.swing.GroupLayout definitionPanelLayout = new javax.swing.GroupLayout(definitionPanel);
        definitionPanel.setLayout(definitionPanelLayout);
        definitionPanelLayout.setHorizontalGroup(
            definitionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(definitionPanelLayout.createSequentialGroup()
                .addComponent(definitionTerm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wordClassDropDown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(flagButton))
        );
        definitionPanelLayout.setVerticalGroup(
            definitionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(definitionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(definitionTerm, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(wordClassDropDown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(flagButton))
        );

        definitionEditorScrollPane.setViewportView(definitionEditor);

        categoryDropDown.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        addButton.setText(bundle.getString("LanguagePanel.addButton")); // NOI18N

        deleteButton.setText(bundle.getString("LanguagePanel.deleteButton")); // NOI18N

        modifyButton.setText(bundle.getString("LanguagePanel.modifyButton")); // NOI18N

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(modifyButton))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modifyButton)
                    .addComponent(deleteButton)
                    .addComponent(addButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(definitionEditorScrollPane, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(definitionPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(languageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(searchBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(languagePanelSeparator1)
                    .addComponent(definitionListScrollPane)
                    .addComponent(categoryDropDown, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(languagePanelSeparator2)
                    .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(languageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(definitionListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(languagePanelSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(definitionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(definitionEditorScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(categoryDropDown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(languagePanelSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JComboBox categoryDropDown;
    private javax.swing.JEditorPane definitionEditor;
    private javax.swing.JScrollPane definitionEditorScrollPane;
    private javax.swing.JList definitionList;
    private javax.swing.JScrollPane definitionListScrollPane;
    private javax.swing.JPanel definitionPanel;
    private javax.swing.JTextField definitionTerm;
    private javax.swing.JButton deleteButton;
    private javax.swing.JToggleButton flagButton;
    private javax.swing.JLabel languageLabel;
    private javax.swing.JSeparator languagePanelSeparator1;
    private javax.swing.JSeparator languagePanelSeparator2;
    private javax.swing.JButton modifyButton;
    private org.beastwithin.conmandictionary.ui.SearchBox searchBox;
    private javax.swing.JComboBox wordClassDropDown;
    // End of variables declaration//GEN-END:variables


    
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
