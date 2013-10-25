/*  LanguageNameDialog.java: Dialog for entering language names.
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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LanguageNameDialog extends javax.swing.JDialog {

    private ActionListener actionListener;
    private MainWindow mainWindow;
    
    public LanguageNameDialog(MainWindow parent) {
        super(parent, true);
	this.mainWindow = parent;
	final LanguageNameDialog selfRef = this;

        this.actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().compareTo("set")==0) {
                    selfRef.set();
                } else if (e.getActionCommand().compareTo("cancel")==0) {
                    selfRef.cancel();
                }
            }
        };
        this.addWindowListener(new WindowListener() {

            public void windowClosing(WindowEvent e) {
                selfRef.cancel();
            }

            public void windowActivated(WindowEvent e) {
            }

            public void windowClosed(WindowEvent e) {
            }

            public void windowDeactivated(WindowEvent e) {
            }

            public void windowDeiconified(WindowEvent e) {
            }

            public void windowIconified(WindowEvent e) {
            }

            public void windowOpened(WindowEvent e) {
            }
        });
        initComponents();
    }
   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        langNameField1 = new javax.swing.JTextField();
        langNameLabel1 = new javax.swing.JLabel();
        langNameLabel2 = new javax.swing.JLabel();
        langNameField2 = new javax.swing.JTextField();
        langNameButtonSeparator = new javax.swing.JSeparator();
        setButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Language names");

        langNameLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/beastwithin/conmandictionary/ui/UIMessages"); // NOI18N
        langNameLabel1.setText(bundle.getString("LanguageNameDialog.langNameLabel1")); // NOI18N

        langNameLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        langNameLabel2.setText(bundle.getString("LanguageNameDialog.langNameLabel2")); // NOI18N

        setButton.setText(bundle.getString("LanguageNameDialog.setButton")); // NOI18N
        setButton.setToolTipText(bundle.getString("LanguageNameDialog.setButton.tooltip")); // NOI18N
        setButton.setActionCommand("set");
        setButton.addActionListener(actionListener);

        cancelButton.setText(bundle.getString("LanguageNameDialog.cancelButton")); // NOI18N
        cancelButton.setToolTipText(bundle.getString("LanguageNameDialog.cancelButton.tooltip")); // NOI18N
        cancelButton.setActionCommand("cancel");
        cancelButton.addActionListener(actionListener);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(langNameButtonSeparator, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(langNameLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(langNameLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(langNameField2, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                            .addComponent(langNameField1, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 285, Short.MAX_VALUE)
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(setButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(langNameLabel1)
                    .addComponent(langNameField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(langNameLabel2)
                    .addComponent(langNameField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(langNameButtonSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(setButton)
                    .addComponent(cancelButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JSeparator langNameButtonSeparator;
    private javax.swing.JTextField langNameField1;
    private javax.swing.JTextField langNameField2;
    private javax.swing.JLabel langNameLabel1;
    private javax.swing.JLabel langNameLabel2;
    private javax.swing.JButton setButton;
    // End of variables declaration//GEN-END:variables

    
    private void getLanguagesFromDialog() {
        mainWindow.getLeftLanguagePanel().setLanguage(langNameField1.getText());
        mainWindow.getRightLanguagePanel().setLanguage(langNameField2.getText());
    }

    private void setLanguagesInDialog() {
        langNameField1.setText(mainWindow.getLeftLanguagePanel().getLanguage());
        langNameField2.setText(mainWindow.getRightLanguagePanel().getLanguage());
    }

    public void open() {
        this.setLanguagesInDialog();
        this.setVisible(true);
    }

    public void set() {
        this.getLanguagesFromDialog();
        this.setVisible(false);
    }

    public void cancel() {
        this.setVisible(false);
    }
}
