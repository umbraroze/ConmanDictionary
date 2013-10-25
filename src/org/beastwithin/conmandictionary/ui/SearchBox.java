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

import java.util.*;

public class SearchBox extends javax.swing.JPanel {

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
        initComponents();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        search = new javax.swing.JTextField();
        clearButton = new javax.swing.JButton();

        search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchKeyTyped(evt);
            }
        });

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/beastwithin/conmandictionary/ui/UIMessages"); // NOI18N
        clearButton.setText(bundle.getString("SearchBox.clearButton")); // NOI18N
        clearButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(search, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clearButton))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(clearButton)
                .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /*
     * Called when user types anything on the search box.
     */
    private void searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyTyped
        if (search.getText().length() == 0) {
            notifySearchBoxCleared();
        } else {
            notifySearchBoxChanged();
        }
    }//GEN-LAST:event_searchKeyTyped

    /*
     * Called when user clicks "Clear" button.
     */
    private void clearButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearButtonMouseClicked
        search.setText("");
        notifySearchBoxCleared();
    }//GEN-LAST:event_clearButtonMouseClicked
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearButton;
    private javax.swing.JTextField search;
    // End of variables declaration//GEN-END:variables
    
}
