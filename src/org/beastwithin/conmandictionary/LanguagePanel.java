/*  LanguagePanel.java: Dictionary list and entry editor panel in main window.
 * 
 *  Conman's Dictionary, a dictionary application for conlang makers.
 *  Copyright (C) 2006,2007,2008,2009,2010  Urpo Lankinen
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

package org.beastwithin.conmandictionary;

import java.util.List;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.event.ListDataEvent;
import org.jdesktop.application.Action;

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
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonPanel = new javax.swing.JPanel();
        modifyButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        definitionListScrollPanel = new javax.swing.JScrollPane();
        definitionList = new javax.swing.JList();
        searchBox = new org.beastwithin.conmandictionary.SearchBox();
        definitionPanel = new javax.swing.JPanel();
        definitionTerm = new javax.swing.JTextField();
        definitionEditorPane = new javax.swing.JScrollPane();
        definitionEditor = new javax.swing.JEditorPane();
        flagButton = new javax.swing.JToggleButton();
        wordClassDropDown = new javax.swing.JComboBox();
        categoryDropDown = new javax.swing.JComboBox();
        languageLabel = new javax.swing.JLabel();
        languagePanelSeparator1 = new javax.swing.JSeparator();
        languagePanelSeparator2 = new javax.swing.JSeparator();

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.beastwithin.conmandictionary.ConmanDictionary.class).getContext().getActionMap(LanguagePanel.class, this);
        modifyButton.setAction(actionMap.get("modifySelected")); // NOI18N
        modifyButton.setText("Modify");

        deleteButton.setAction(actionMap.get("deleteSelected")); // NOI18N
        deleteButton.setText("Delete");

        addButton.setAction(actionMap.get("addDefinition")); // NOI18N
        addButton.setText("Add");

        org.jdesktop.layout.GroupLayout buttonPanelLayout = new org.jdesktop.layout.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, buttonPanelLayout.createSequentialGroup()
                .addContainerGap(196, Short.MAX_VALUE)
                .add(addButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(deleteButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(modifyButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 51, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(buttonPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(modifyButton)
                .add(deleteButton)
                .add(addButton))
        );

        definitionList.setModel(entryList);
        definitionList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        definitionList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                definitionListValueChanged(evt);
            }
        });
        definitionListScrollPanel.setViewportView(definitionList);

        searchBox.addSearchBoxListener(searchListener);

        definitionTerm.setToolTipText("Enter the term or word to be defined");

        definitionEditorPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        definitionEditorPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        definitionEditor.setToolTipText("Define the term or word here.");
        definitionEditorPane.setViewportView(definitionEditor);

        flagButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/beastwithin/conmandictionary/resources/flag.png"))); // NOI18N
        flagButton.setToolTipText("Flag this entry as potentially problematic.");
        flagButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                flagButtonClicked(evt);
            }
        });

        wordClassDropDown.setModel(wordClassModel);

        categoryDropDown.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "(None)" }));

        org.jdesktop.layout.GroupLayout definitionPanelLayout = new org.jdesktop.layout.GroupLayout(definitionPanel);
        definitionPanel.setLayout(definitionPanelLayout);
        definitionPanelLayout.setHorizontalGroup(
            definitionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, definitionPanelLayout.createSequentialGroup()
                .add(definitionTerm, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(wordClassDropDown, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(flagButton))
            .add(categoryDropDown, 0, 348, Short.MAX_VALUE)
            .add(definitionEditorPane)
        );
        definitionPanelLayout.setVerticalGroup(
            definitionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(definitionPanelLayout.createSequentialGroup()
                .add(definitionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(flagButton)
                    .add(wordClassDropDown, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(definitionTerm, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(definitionEditorPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(categoryDropDown, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        languageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        languageLabel.setText("Language");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(languageLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
            .add(searchBox, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
            .add(definitionListScrollPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
            .add(languagePanelSeparator1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
            .add(definitionPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(languagePanelSeparator2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, buttonPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(languageLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(definitionListScrollPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(languagePanelSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(definitionPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(languagePanelSeparator2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(buttonPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void definitionListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_definitionListValueChanged
        if (!evt.getValueIsAdjusting()) {
            pickedListItemForEditing();
        }
    }//GEN-LAST:event_definitionListValueChanged

    private void flagButtonClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_flagButtonClicked
        // Do nothing in particular, for now
    }//GEN-LAST:event_flagButtonClicked
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JComboBox categoryDropDown;
    private javax.swing.JEditorPane definitionEditor;
    private javax.swing.JScrollPane definitionEditorPane;
    private javax.swing.JList definitionList;
    private javax.swing.JScrollPane definitionListScrollPanel;
    private javax.swing.JPanel definitionPanel;
    private javax.swing.JTextField definitionTerm;
    private javax.swing.JButton deleteButton;
    private javax.swing.JToggleButton flagButton;
    private javax.swing.JLabel languageLabel;
    private javax.swing.JSeparator languagePanelSeparator1;
    private javax.swing.JSeparator languagePanelSeparator2;
    private javax.swing.JButton modifyButton;
    private org.beastwithin.conmandictionary.SearchBox searchBox;
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
    @Action
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
    @Action
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
    @Action
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
