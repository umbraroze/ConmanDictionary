/*  NotePad.java: a note pad for... um, notes.
 * 
 *  Conman's Dictionary, a dictionary application for conlang makers.
 *  Copyright (C) 2006,2007  Urpo Lankinen
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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NotePad extends JFrame {
	public static final long serialVersionUID = 1;
	private ActionListener actionListener;
	private JEditorPane editor;
	
	/**
	 * Construct a new notepad object and associate it
	 * with a specified main window.
	 * 
	 */
	public NotePad() {
		final NotePad selfRef = this;
	
		this.setIconImage(ConmanDictionary.getAppIcon());
		
		// Ordinary Stuff.
		this.setTitle("Notepad - " + ConmanDictionary.APP_NAME);
		//this.setRootPane(ConmanDictionary.getMainWindow().getRootPane());		
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setPreferredSize(new Dimension(400,450));
		
		this.actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand() == "close") {
					selfRef.setVisible(false);
				}
			}
		};
		
		// Contents.
		JPanel notePadContents = new JPanel();
		BoxLayout l = new BoxLayout(notePadContents,BoxLayout.Y_AXIS);
		notePadContents.setLayout(l);
		
		editor = new JEditorPane();
		JScrollPane editorScroll = new JScrollPane(editor);
		editorScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		editorScroll.setMinimumSize(new Dimension(400,300));
		editorScroll.setPreferredSize(new Dimension(400,400));
		editor.setEditable(true);
		notePadContents.add(editorScroll);
		notePadContents.add(new JSeparator(JSeparator.HORIZONTAL));

		JPanel buts = new JPanel(new FlowLayout());
		// Close
		JButton closeButton = new JButton("Close");
		closeButton.setActionCommand("close");
		closeButton.addActionListener(this.actionListener);
		closeButton.setToolTipText("Close the notepad.");
		buts.add(closeButton);
		// ...and all buttons are done.
		notePadContents.add(buts);

		this.add(notePadContents);
		this.pack();
	}
	public String getText() {
		return editor.getText();
	}
	public void setText(String text) {
		editor.setText(text);
	}
	public String toString() {
		return editor.getText();
	}
}
