/*  WordClassEditor.java: Editor for word classes.
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

package org.beastwithin.conmandictionary.ui;

import org.beastwithin.conmandictionary.document.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 *
 * @author wwwwolf
 */
public class WordClassEditor extends javax.swing.JDialog {
    
    private Dictionary model;
    private WordClassListModel wordClassListModel;
    private MainWindow parent;
    
    private class WordClassListModel implements ListModel {

        private List<ListDataListener> listDataListeners;
        private List<WordClass> wordClasses;

        public WordClassListModel() {
            listDataListeners = Collections.synchronizedList(new ArrayList<ListDataListener>());
            wordClasses = null;
        }

        public void addListDataListener(ListDataListener l) {
            listDataListeners.add(l);
        }

        public void removeListDataListener(ListDataListener l) {
            listDataListeners.remove(l);
        }

        public void refresh() {
            ListDataEvent e = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, wordClasses.size() - 1);
            for (ListDataListener l : listDataListeners) {
                l.contentsChanged(e);
            }
        }

        public Object getElementAt(int n) {
            return wordClasses.get(n);
        }

        public int getSize() {
            return wordClasses.size();
        }

        public List<WordClass> getWordClasses() {
            return wordClasses;
        }

        public void setWordClasses(List<WordClass> wordClasses) {
            this.wordClasses = wordClasses;
            refresh();
        }
    }
    
    public WordClassEditor(MainWindow parent, boolean modal, Dictionary model) {
        super(parent, modal);
        this.parent = parent;
        wordClassListModel = new WordClassListModel();
        setModel(model);
        initComponents();
    }

    public void setModel(Dictionary model) {
        this.model = model;
        wordClassListModel.setWordClasses(model.getWordClasses());
    }

    // FIXME: I can't remember what this is for. Should this be static?
    private void sortWordClassList(List<WordClass> wordClassList) {
        Object a[] = wordClassList.toArray();
        Arrays.sort(a);
        wordClassList.clear();
        for (int i = 0; i < a.length; i++) {
            wordClassList.add((WordClass) a[i]);
        }
    }

    public void add() {
        String newWordClassName = nameEntry.getText();
        // Is the name empty?
        if(newWordClassName.length() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Word class name cannot be empty.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Does a word class with this name already exist?
        for(WordClass wordClassToCheck : model.getWordClasses()) {
            if(wordClassToCheck.getName().equals(newWordClassName)) {
                JOptionPane.showMessageDialog(this,
                        "Word class \""+newWordClassName+"\" already exists.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        // Add the entry
        WordClass w = new WordClass();
        w.setName(newWordClassName);
        if(abbreviationEntry.getText().length() > 0)
            w.setAbbreviation(abbreviationEntry.getText());
        if(descriptionEntry.getText().length() > 0)
            w.setDescription(descriptionEntry.getText());
        model.getWordClasses().add(w);
        sortWordClassList(model.getWordClasses());
        model.setWordClassesModified(true);
        wordClassListModel.refresh();
        // Clear the text fields
        nameEntry.setText("");
        abbreviationEntry.setText("");
        descriptionEntry.setText("");
    }
    
    public void modify() {
        int idx = this.wordClassList.getSelectedIndex();
        if (idx == -1) {
            return;
        }
        String newName = nameEntry.getText();
        // Is the name empty?
        if(newName.length() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Word class name cannot be empty.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Does this name already exist?
        for(int n = 0; n < model.getWordClasses().size(); n++) {
            // We can edit *this* entry
            if(n == idx)
                continue;
            if(model.getWordClasses().get(n).getName().equals(newName)) {
                JOptionPane.showMessageDialog(this,
                        "Word class named \""+newName+"\" already exists.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        // Otherwise we're golden.
        WordClass w = (WordClass) wordClassListModel.getElementAt(idx);
        w.setName(nameEntry.getText());
        w.setAbbreviation(abbreviationEntry.getText());
        w.setDescription(descriptionEntry.getText());
        sortWordClassList(model.getWordClasses());
        this.wordClassList.repaint();
    }
    /**
     * Called from an event handler when a list item is picked for editing.
     */
    private void pickedListItemForEditing() {
        int idx = this.wordClassList.getSelectedIndex();
        if (idx == -1) {
            return;
        }
        WordClass w = (WordClass) wordClassListModel.getElementAt(idx);
        editEntry(w);
    }
    /**
     * Sets the editor's fields to the values of the WordClass in question.
     * @param w the WordClass to be edited.
     */
    private void editEntry(WordClass w) {
        nameEntry.setText(w.getName());
        abbreviationEntry.setText(w.getAbbreviation());
        descriptionEntry.setText(w.getDescription());
    }


    public void delete() {
        int idx = this.wordClassList.getSelectedIndex();
        if (idx == -1) {
            return;
        }
        WordClass removed = model.getWordClasses().get(idx);
        int resp = JOptionPane.showConfirmDialog(this,
                "Really delete the word class \""+removed.getName()+"\"?",
                "Really delete?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if(resp != JOptionPane.YES_OPTION)
            return;
        // Set wordclass in every entry that uses this wordclass to null.
        for(short n = 0; n <= 1; n++) {
            for(Entry e : model.getDefinitions().get(n).getEntries()) {
                if(e.getWordClass().equals(removed)) {
                    e.setWordClass(null);
                }
            }
        }
        // And nuke it from the list.
        this.wordClassList.remove(idx);
        // Clean stuff up.
        sortWordClassList(model.getWordClasses());
        nameEntry.setText("");
        abbreviationEntry.setText("");
        descriptionEntry.setText("");
    }

    public void close() {
        parent.notifyWordClassChanges();
        dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        wordClassScrollPane = new javax.swing.JScrollPane();
        wordClassList = new javax.swing.JList();
        nameLabel = new javax.swing.JLabel();
        nameEntry = new javax.swing.JTextField();
        abbreviationLabel = new javax.swing.JLabel();
        abbreviationEntry = new javax.swing.JTextField();
        descriptionLabel = new javax.swing.JLabel();
        descriptionScrollPane = new javax.swing.JScrollPane();
        descriptionEntry = new javax.swing.JTextArea();
        addButton = new javax.swing.JButton();
        modifyButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        doneButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        wordClassList.setModel(wordClassListModel);
        wordClassList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        wordClassList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                wordClassListValueChanged(evt);
            }
        });
        wordClassScrollPane.setViewportView(wordClassList);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/beastwithin/conmandictionary/ui/UIMessages"); // NOI18N
        nameLabel.setText(bundle.getString("WordClassEditor.name")); // NOI18N

        abbreviationLabel.setText(bundle.getString("WordClassEditor.abbreviation")); // NOI18N

        descriptionLabel.setText(bundle.getString("WordClassEditor.description")); // NOI18N

        descriptionEntry.setColumns(20);
        descriptionEntry.setRows(5);
        descriptionScrollPane.setViewportView(descriptionEntry);

        addButton.setText(bundle.getString("WordClassEditor.addButton")); // NOI18N

        modifyButton.setText(bundle.getString("WordClassEditor.modifyButton")); // NOI18N

        deleteButton.setText(bundle.getString("WordClassEditor.deleteButton")); // NOI18N

        doneButton.setText(bundle.getString("WordClassEditor.doneButton")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(wordClassScrollPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameEntry))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(abbreviationLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(abbreviationEntry))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(descriptionLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(descriptionScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(addButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(modifyButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(doneButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(wordClassScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameEntry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(abbreviationLabel)
                    .addComponent(abbreviationEntry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(descriptionLabel)
                    .addComponent(descriptionScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(doneButton)
                    .addComponent(deleteButton)
                    .addComponent(modifyButton)
                    .addComponent(addButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void wordClassListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_wordClassListValueChanged
        if (!evt.getValueIsAdjusting()) {
            pickedListItemForEditing();
        }
    }//GEN-LAST:event_wordClassListValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField abbreviationEntry;
    private javax.swing.JLabel abbreviationLabel;
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextArea descriptionEntry;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JScrollPane descriptionScrollPane;
    private javax.swing.JButton doneButton;
    private javax.swing.JButton modifyButton;
    private javax.swing.JTextField nameEntry;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JList wordClassList;
    private javax.swing.JScrollPane wordClassScrollPane;
    // End of variables declaration//GEN-END:variables
}
