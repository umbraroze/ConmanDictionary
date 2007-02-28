/*  SearchBox.java: Search box control.
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
 */


package org.beastwithin.conmandictionary;

import java.util.*;
import javax.swing.*;
//import java.awt.*;
import java.awt.event.*;

public class SearchBox extends JPanel {
	static final long serialVersionUID = 1;
	private Vector<SearchBoxListener> searchBoxListeners;
	
	private JTextField search = null;
	private JButton clearButton = null;
	
	private void notifySearchBoxChanged() {
		String t = search.getText();
		for(SearchBoxListener s : searchBoxListeners) {
			s.searchBoxContentsChanged(t);
		}		
	}
	private void notifySearchBoxCleared() {
		for(SearchBoxListener s : searchBoxListeners) {
			s.searchBoxCleared();
		}
	}
	
	public SearchBox() {
		super();
		searchBoxListeners = new Vector<SearchBoxListener>();
		
		search = new JTextField();
		clearButton = new JButton("Clear");
		this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
		search.setVisible(true);
		search.setToolTipText("Enter search terms.");
		clearButton.setVisible(true);
		clearButton.setToolTipText("Forget the search.");
		this.add(search); 
		this.add(clearButton);
		this.setVisible(true);
		clearButton.setActionCommand("clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand() == "clear") {
					search.setText("");
					notifySearchBoxCleared();
				}
			}
		});
		
		search.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent kr) { }
			public void keyPressed(KeyEvent kr) { }
			public void keyReleased(KeyEvent kr) {
				if(search.getText().length() == 0)
					notifySearchBoxCleared();
				else
					notifySearchBoxChanged();				
			}
		});
	}
	public void addSearchBoxListener(SearchBoxListener newListener) {
		searchBoxListeners.add(newListener);
	}
}
