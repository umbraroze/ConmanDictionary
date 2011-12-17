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

package org.beastwithin.conmandictionary;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LanguageNameDialog extends javax.swing.JDialog {

    private ActionListener actionListener;
    private MainWindow mainWindow;
    
    public LanguageNameDialog(MainWindow parent) {
        super(parent.getFrame(), true);
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
        langNameLabel1.setText("Language 1:");

        langNameLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        langNameLabel2.setText("Language 2:");

        setButton.setText("Set");
        setButton.setToolTipText("Set the languages.");
        setButton.setActionCommand("set");
        setButton.addActionListener(actionListener);

        cancelButton.setText("Cancel");
        cancelButton.setToolTipText("Close and don't set the languages.");
        cancelButton.setActionCommand("cancel");
        cancelButton.addActionListener(actionListener);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, langNameLabel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, langNameLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(langNameField2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                    .add(langNameField1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE))
                .addContainerGap())
            .add(org.jdesktop.layout.GroupLayout.TRAILING, langNameButtonSeparator, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(297, Short.MAX_VALUE)
                .add(cancelButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(setButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(langNameLabel1)
                    .add(langNameField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(langNameLabel2)
                    .add(langNameField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(langNameButtonSeparator, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(setButton)
                    .add(cancelButton))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
