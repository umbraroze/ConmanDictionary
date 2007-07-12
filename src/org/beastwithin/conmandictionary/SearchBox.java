/*  SearchBox.java: Search box control.
 * 
 *  Conman's Dictionary, a dictionary application for conlang makers.
 *  Copyright (C) 2006  Urpo Lankinen
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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class SearchBox extends JPanel {
	static final long serialVersionUID = 1;
	private List<SearchBoxListener> searchBoxListeners;
	
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
		searchBoxListeners = Collections.synchronizedList(new ArrayList<SearchBoxListener>());
		
		search = new JTextField();
		clearButton = new JButton("Clear");
		SpringLayout l = new SpringLayout();
		this.setLayout(l);
		search.setToolTipText("Enter search terms.");
		clearButton.setToolTipText("Forget the search.");
				
		// Aligned with top corner
		l.putConstraint(SpringLayout.WEST, search,
                5, SpringLayout.WEST, this);
		l.putConstraint(SpringLayout.NORTH, search,
                5, SpringLayout.NORTH, this);
		// Button next to the box
		l.putConstraint(SpringLayout.EAST, search,
                10, SpringLayout.WEST, clearButton);
		// Button on the right top corner
		l.putConstraint(SpringLayout.NORTH, clearButton,
                5, SpringLayout.NORTH, this);
		l.putConstraint(SpringLayout.EAST, clearButton,
                0, SpringLayout.EAST, this);
		// Searchbox bottom = button bottom
		l.putConstraint(SpringLayout.SOUTH, search,
                0, SpringLayout.SOUTH, clearButton);
		
		this.add(search);
		this.add(clearButton);
		search.setVisible(true);
		clearButton.setVisible(true);
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
		this.setMinimumSize(new Dimension(200,40));
		this.setPreferredSize(new Dimension(300,40));
		this.setMaximumSize(new Dimension(300,40));
	}
	public void addSearchBoxListener(SearchBoxListener newListener) {
		searchBoxListeners.add(newListener);
	}
}
