/*  LanguagePanel.java: Dictionary list and entry editor panel in main window.
 * 
 *  Conman's Dictionary, a dictionary application for conlang makers.
 *  Copyright (C) 2006  Urpo Lankinen
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 *  
 *  $Id: LanguagePanel.java 14 2006-12-03 16:45:00Z wwwwolf $
 */

package org.beastwithin.conmandictionary;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Dictionary list and entry editor panel in main window. Consists of
 * the list of entries, a keyword entry panel, rich text editor for the
 * definition, and buttons to modify the entry with.
 * 
 * @author wwwwolf
 */
public class LanguagePanel extends JPanel {
	/**
	 * Listens to the events from buttons.
	 *
	 * This will keep a reference to its parent.
	 * FIXME: Will this create a non-GCable circular reference? 
	 * FIXME: Ridiculous name.
	 *  
	 * Usage:
	 * this.actionListener = new LanguagePanelActionListener(this);
	 */
	private class LanguagePanelActionListener implements ActionListener {
		private LanguagePanel parent;
		public LanguagePanelActionListener(LanguagePanel parent) {
			this.parent = parent;
		}
		public void actionPerformed(ActionEvent e) {
			//System.out.println("Event: " + e.getActionCommand());
			if(e.getActionCommand() == "add") {
				parent.addDefinition();
			} else if(e.getActionCommand() == "delete") {
				parent.deleteSelected();
			} else if(e.getActionCommand() == "modify") {
				parent.modifySelected();
			} else {
				System.err.println("WARNING: Unknown event " + e.getActionCommand());
			}
		}
	}

	/**
	 * Listens to the list selections.
	 * 
	 * This will keep a reference to its parent.
	 * FIXME: Will this create a non-GCable circular reference?
	 * FIXME: Ridiculous name. 
	 * 
	 * Usage:
	 * this.listSelectionListener = new LanguagePanelListSelectionListener(this);
	 */
	private class LanguagePanelListSelectionListener implements ListSelectionListener {
		private LanguagePanel parent;
		public LanguagePanelListSelectionListener(LanguagePanel parent) {
			this.parent = parent;
		}
		public void valueChanged(ListSelectionEvent e) {
			System.err.printf(e.toString());
			if(!e.getValueIsAdjusting()) {	
				this.parent.pickedListItemForEditing();
			}
		}
	}
	
	static final long serialVersionUID = 1; 
	
	private boolean modified;
	
	private JLabel languageLabel;
	private EntryList entryList;
	private JList definitionList;
	private JTextField definitionTerm;
	private JEditorPane definitionEditor;
	
	private LanguagePanelActionListener actionListener;
	private LanguagePanelListSelectionListener listSelectionListener;
	
	/**
	 * Constructs the UI.
	 * 
	 * @param language Desired language.
	 */
	public LanguagePanel(String language) {
		super();
		
		// Listens to the events
		this.actionListener = new LanguagePanelActionListener(this);
		this.listSelectionListener = new LanguagePanelListSelectionListener(this);
		
		BoxLayout l = new BoxLayout(this,BoxLayout.PAGE_AXIS);
		this.setLayout(l);
		
		// Text that says what we're doing
		this.languageLabel = new JLabel(language);
		this.add(this.languageLabel);

		// Empty list.
		entryList = new EntryList();
		entryList.setLanguage(language);
		
		// Our list of definitions
		this.definitionList = new JList(entryList);
		this.definitionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.definitionList.addListSelectionListener(listSelectionListener);
		JScrollPane defListScroll = new JScrollPane(this.definitionList);
		defListScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		defListScroll.setPreferredSize(new Dimension(300,150));
		this.add(defListScroll);
		
		// The name of the term we're editing.
		this.definitionTerm = new JTextField();
		this.add(definitionTerm);
		
		// Editor for the definition.
		this.definitionEditor = new JEditorPane();
		JScrollPane defEditorScroll = new JScrollPane(definitionEditor);
		defEditorScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		defEditorScroll.setPreferredSize(new Dimension(300,250));
		this.add(defEditorScroll);
		
		// Buttons to modify the selection
		JPanel buts = new JPanel(new FlowLayout());
		// Add
		JButton addButton = new JButton("Add");
		addButton.setActionCommand("add");
		addButton.addActionListener(this.actionListener);
		buts.add(addButton);
		// Delete
		JButton deleteButton = new JButton("Delete");
		deleteButton.setActionCommand("delete");
		deleteButton.addActionListener(this.actionListener);
		buts.add(deleteButton);
		// Modify
		JButton modifyButton = new JButton("Modify");
		modifyButton.setActionCommand("modify");
		modifyButton.addActionListener(this.actionListener);
		buts.add(modifyButton);
		// ...and all buttons are done.
		this.add(buts);
		
		this.modified = false;
	}

	/**
	 * Sets the list language.
	 * @param language Language.
	 */
	public void setLanguage(String language) {
		this.languageLabel.setText(language);
		entryList.setLanguage(language);
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
		if(idx == -1)
			return;
		Entry e = (Entry) entryList.getElementAt(idx);
		this.editEntry(e);
	}
	/**
	 * Fills the editor with the details from the specified editor.
	 * 
	 * @param e Entry to be edited.
	 */
	private void editEntry(Entry e) {
		this.definitionTerm.setText(e.getTerm());
		this.definitionEditor.setText(e.getDefinition());
	}
	/**
	 * Clears the editor.
	 */
	private void clearEntries() {
		this.definitionTerm.setText("");
		this.definitionEditor.setText("");
	}

	/**
	 * Adds the values in the editor as a new entry in the list.
	 */
	public void addDefinition() {
		String term = this.definitionTerm.getText();
		String definition = this.definitionEditor.getText();

		Entry newTerm = new Entry(term, definition);
		
		this.entryList.addElement(newTerm);
		this.entryList.sort();
		this.modified = true;
	}
	/**
	 * Deletes the selected entry.
	 */
	public void deleteSelected() {
		// TODO: Confirmation dialog!
		int idx = this.definitionList.getSelectedIndex(); 
		if(idx == -1)
			return;
		this.entryList.removeElementAt(idx);
		this.entryList.sort();
		clearEntries();
		this.modified = true;
	}
	/**
	 * Update the selected item with the new details from the editor.
	 */
	public void modifySelected() {
		int idx = this.definitionList.getSelectedIndex(); 
		if(idx == -1)
			return;
		Entry e = (Entry) entryList.getElementAt(idx);
		e.setTerm(this.definitionTerm.getText());
		e.setDefinition(this.definitionEditor.getText());
		this.entryList.sort();
		this.definitionList.repaint();
		this.modified = true;
	}	
	
	/**
	 * Returns the list of entries.
	 * @return The entry list.
	 */
	public EntryList getEntryList() {
		return entryList;
	}
	
	/**
	 * Is this list modified?
	 * @return Modified?
	 */
	public boolean getModified() {
		return this.modified;
	}
	/**
	 * Sets whether this list is modified.
	 * @param modified Modified?
	 */
	public void setModified(boolean modified) {
		this.modified = modified;
	}
	/**
	 * Clear all entries in this list.
	 */
	public void clearList() {
		this.entryList.removeAllElements();
		this.modified = false;
		this.clearEntries();
	}
}
