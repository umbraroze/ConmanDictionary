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

package org.beastwithin.conmandictionary.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class SearchBox extends JPanel {
    private JButton clearButton;
    private JTextField search;
    private JPanel searchBoxPanel;

    private List<SearchBoxListener> searchBoxListeners;

    private void notifySearchBoxChanged() {
        String t = search.getText();
        for (SearchBoxListener s : searchBoxListeners) {
            s.searchBoxContentsChanged(t);
        }
    }

    private void notifySearchBoxCleared() {
        for (SearchBoxListener s : searchBoxListeners) {
            s.searchBoxCleared();
        }
    }

    public void addSearchBoxListener(SearchBoxListener newListener) {
        searchBoxListeners.add(newListener);
    }

    public SearchBox() {
        searchBoxListeners = Collections.synchronizedList(new ArrayList<SearchBoxListener>());
        //$$$setupUI$$$();
        createUIComponents();
        search.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                searchKeyTyped(e);
            }
        });
        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clearButtonMouseClicked(e);
            }
        });
    }

    /*
     * Called when user types anything on the search box.
     */
    private void searchKeyTyped(KeyEvent evt) {
        if (search.getText().length() == 0) {
            notifySearchBoxCleared();
        } else {
            notifySearchBoxChanged();
        }
    }

    /*
     * Called when user clicks "Clear" button.
     */
    private void clearButtonMouseClicked(MouseEvent evt) {
        search.setText("");
        notifySearchBoxCleared();
    }

    private void createUIComponents() {
        search = new JTextField();

        clearButton = new JButton();
        clearButton.setText("Clear");

        //searchBoxPanel = new JPanel();

        setLayout(new FlowLayout());
        add(search);
        add(clearButton);
        //searchBoxPanel.setVisible(true);
    }
}
