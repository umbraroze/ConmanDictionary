/*  NotePad.java: a note pad for... um, notes.
 * 
 *  Conman's Dictionary, a dictionary application for conlang makers.
 *  Copyright (C) 2006,2007  Urpo Lankinen
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
 */

package org.beastwithin.conmandictionary;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NotePad extends JDialog {
	public static final long serialVersionUID = 1;
	private ActionListener actionListener;
	private JEditorPane editor;
	
	public NotePad() {
		super();
		final NotePad selfRef = this;
		
		// Ordinary Stuff.
		this.setTitle("Notepad - " + ConmanDictionary.APP_NAME);
		//this.setRootPane(ConmanDictionary.getMainWindow().getRootPane());		
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setPreferredSize(new Dimension(400,300));
		
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
		editor.setMinimumSize(new Dimension(400,300));
		editor.setEditable(true);
		notePadContents.add(editor);
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
}
