// $Id: LanguagePanel.java 2 2006-09-17 12:33:48Z wwwwolf $

package org.beastwithin.conmandictionary;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import org.w3c.dom.*;

public class LanguagePanel extends JPanel {
	/**
	 * This thing listens to the events from buttons. This uses
	 * "reflective" thing: Basically, it registers a link to its
	 * parent object, and asks the parent object to handle the
	 * event in question.
	 */
	class LanguagePanelActionListener implements ActionListener {
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
	 * This thing listens to the list selections. This uses
	 * "reflective" thing: Basically, it registers a link to its
	 * parent object, and asks the parent object to handle the
	 * event in question.
	 */
	class LanguagePanelListSelectionListener implements ListSelectionListener {
		private LanguagePanel parent;
		public LanguagePanelListSelectionListener(LanguagePanel parent) {
			this.parent = parent;
		}
		public void valueChanged(ListSelectionEvent e) {
			if(!e.getValueIsAdjusting()) {	
				this.parent.pickedListItemForEditing();
			}
		}
	}
	
	static final long serialVersionUID = 1; 
	
	private boolean modified;
	
	private EntryList entryList;
	private JList definitionList;
	private JTextField definitionTerm;
	private JEditorPane definitionEditor;
	
	private LanguagePanelActionListener actionListener;
	private LanguagePanelListSelectionListener listSelectionListener;
	
	public LanguagePanel(String name) {
		super();
		
		// Listens to the events
		this.actionListener = new LanguagePanelActionListener(this);
		this.listSelectionListener = new LanguagePanelListSelectionListener(this);
		
		BoxLayout l = new BoxLayout(this,BoxLayout.PAGE_AXIS);
		this.setLayout(l);
		
		// Text that says what we're doing
		this.add(new JLabel(name));

		// Empty list.
		entryList = new EntryList();
		
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

	private void pickedListItemForEditing() {
		int idx = this.definitionList.getSelectedIndex(); 
		if(idx == -1)
			return;
		Entry e = (Entry) entryList.getElementAt(idx);
		this.editEntry(e);
	}
	private void editEntry(Entry e) {
		this.definitionTerm.setText(e.getTerm());
		this.definitionEditor.setText(e.getDefinition());
	}
	private void clearEntries() {
		this.definitionTerm.setText("");
		this.definitionEditor.setText("");
	}

	public void addDefinition() {
		String term = this.definitionTerm.getText();
		String definition = this.definitionEditor.getText();

		Entry newTerm = new Entry(term, definition);
		
		this.entryList.addElement(newTerm);
		this.entryList.sort();
		this.modified = true;
	}
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
	
	public EntryList getEntryList() {
		return entryList;
	}
	
	public DocumentFragment toXmlElement() {
		return entryList.toXmlElement();
	}
	
	public boolean isModified() {
		return this.modified;
	}
	public void clearList() {
		this.entryList.removeAllElements();
		this.modified = false;
		this.clearEntries();
	}
}
