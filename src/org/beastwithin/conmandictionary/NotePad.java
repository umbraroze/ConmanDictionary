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
import javax.swing.text.*;

public class NotePad extends javax.swing.JFrame {
    
    /**
     * Construct a new notepad.
     */
    public NotePad() {
        final NotePad selfRef = this;
        initComponents();
        this.setTitle("Notepad - " + ConmanDictionary.APP_NAME);
        this.setIconImage(ConmanDictionary.getAppIcon());

        this.actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().compareTo("close")==0) {
                    selfRef.setVisible(false);
                }
            }
        };
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        notePadToolBar = new javax.swing.JToolBar();
        closeButton = new javax.swing.JButton();
        editorScroll = new javax.swing.JScrollPane();
        editor = new javax.swing.JEditorPane();

        notePadToolBar.setRollover(true);

        closeButton.setText("Close");
        closeButton.setFocusable(false);
        closeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        closeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeButtonMouseClicked(evt);
            }
        });
        notePadToolBar.add(closeButton);

        editorScroll.setViewportView(editor);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(notePadToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
            .addComponent(editorScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(notePadToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editorScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseClicked
        setVisible(false);
    }//GEN-LAST:event_closeButtonMouseClicked
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NotePad().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JEditorPane editor;
    private javax.swing.JScrollPane editorScroll;
    private javax.swing.JToolBar notePadToolBar;
    // End of variables declaration//GEN-END:variables

    private ActionListener actionListener;

    public String getText() {
        return editor.getText();
    }

    public void setText(String text) {
        editor.setText(text);
    }

    public String toString() {
        return editor.getText();
    }

    public void setModel(Document d) {
        editor.setDocument(d);
    }

}
