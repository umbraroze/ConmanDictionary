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

package org.beastwithin.conmandictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import org.jdesktop.application.Action;

public class WordClassEditor extends javax.swing.JDialog {
    private List<WordClass> wordClasses;
    private WordClassListModel wordClassListModel;
    
    private class WordClassListModel implements ListModel {

        private List<ListDataListener> listDataListeners;
        private List<WordClass> wordClasses;

        public void WordClassListModel() {
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
        }
    }
    
    public WordClassEditor(java.awt.Frame parent, boolean modal, List<WordClass> list) {
        super(parent, modal);
        wordClassListModel = new WordClassListModel();
        wordClasses = list;
        wordClassListModel.setWordClasses(wordClasses);
        initComponents();
    }

    @Action
    public void add() {
        // Check if this word class already exists
        // Add the entry
        WordClass w = new WordClass();
        w.setName(nameEntry.getText());
        if(abbreviationEntry.getText().length() > 0)
            w.setAbbreviation(abbreviationEntry.getText());
        if(descriptionEntry.getText().length() > 0)
            w.setDescription(descriptionEntry.getText());
        wordClasses.add(w);
        wordClassListModel.refresh();
    }
    
    @Action
    public void modify() {
    }

    @Action
    public void delete() {
    }

    @Action
    public void close() {
        dispose();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
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
        deleteButton = new javax.swing.JButton();
        doneButton = new javax.swing.JButton();
        modifyButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.beastwithin.conmandictionary.ConmanDictionary.class).getContext().getResourceMap(WordClassEditor.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        wordClassScrollPane.setName("wordClassScrollPane"); // NOI18N

        wordClassList.setName("wordClassList"); // NOI18N
        wordClassList.setModel(wordClassListModel);
        wordClassScrollPane.setViewportView(wordClassList);

        nameLabel.setLabelFor(nameEntry);
        nameLabel.setText(resourceMap.getString("nameLabel.text")); // NOI18N
        nameLabel.setName("nameLabel"); // NOI18N

        nameEntry.setText(resourceMap.getString("nameEntry.text")); // NOI18N
        nameEntry.setName("nameEntry"); // NOI18N

        abbreviationLabel.setLabelFor(abbreviationEntry);
        abbreviationLabel.setText(resourceMap.getString("abbreviationLabel.text")); // NOI18N
        abbreviationLabel.setName("abbreviationLabel"); // NOI18N

        abbreviationEntry.setText(resourceMap.getString("abbreviationEntry.text")); // NOI18N
        abbreviationEntry.setName("abbreviationEntry"); // NOI18N

        descriptionLabel.setLabelFor(descriptionEntry);
        descriptionLabel.setText(resourceMap.getString("descriptionLabel.text")); // NOI18N
        descriptionLabel.setName("descriptionLabel"); // NOI18N

        descriptionScrollPane.setName("descriptionScrollPane"); // NOI18N

        descriptionEntry.setColumns(20);
        descriptionEntry.setRows(5);
        descriptionEntry.setName("descriptionEntry"); // NOI18N
        descriptionScrollPane.setViewportView(descriptionEntry);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.beastwithin.conmandictionary.ConmanDictionary.class).getContext().getActionMap(WordClassEditor.class, this);
        deleteButton.setAction(actionMap.get("delete")); // NOI18N
        deleteButton.setText(resourceMap.getString("deleteButton.text")); // NOI18N
        deleteButton.setName("deleteButton"); // NOI18N

        doneButton.setAction(actionMap.get("close")); // NOI18N
        doneButton.setMnemonic('d');
        doneButton.setText(resourceMap.getString("doneButton.text")); // NOI18N
        doneButton.setToolTipText(resourceMap.getString("doneButton.toolTipText")); // NOI18N
        doneButton.setName("doneButton"); // NOI18N

        modifyButton.setAction(actionMap.get("modify")); // NOI18N
        modifyButton.setText(resourceMap.getString("modifyButton.text")); // NOI18N
        modifyButton.setToolTipText(resourceMap.getString("modifyButton.toolTipText")); // NOI18N
        modifyButton.setName("modifyButton"); // NOI18N

        addButton.setAction(actionMap.get("add")); // NOI18N
        addButton.setText(resourceMap.getString("addButton.text")); // NOI18N
        addButton.setToolTipText(resourceMap.getString("addButton.toolTipText")); // NOI18N
        addButton.setName("addButton"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(wordClassScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameLabel)
                    .addComponent(abbreviationLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(abbreviationEntry, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                    .addComponent(nameEntry, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)))
            .addGroup(layout.createSequentialGroup()
                .addComponent(descriptionLabel)
                .addGap(18, 18, 18)
                .addComponent(descriptionScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(196, 196, 196)
                .addComponent(addButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(modifyButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(doneButton))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
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
                    .addComponent(addButton)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
        
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
