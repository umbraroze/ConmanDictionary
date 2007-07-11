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

import java.awt.event.*;
import java.awt.*;

import javax.swing.*;

public class LanguageNameDialog extends JDialog {
	static final long serialVersionUID = 1; 
	private ActionListener actionListener;

	private MainWindow mainWindow;
	private JTextField langNameField1;
	private JTextField langNameField2;

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
	
	private JPanel constructLanguageDialogFields() {
		SpringLayout l = new SpringLayout(); 
		JPanel languageDialogFields = new JPanel(l);

		JLabel langName1 = new JLabel("Language 1:");
		langName1.setMinimumSize(new Dimension(80,20));
		langNameField1 = new JTextField();
		langNameField1.setMinimumSize(new Dimension(200,20));
		JLabel langName2 = new JLabel("Language 2:"); 
		langName2.setMinimumSize(new Dimension(80,20));
		langNameField2 = new JTextField();
		langNameField2.setMinimumSize(new Dimension(200,20));
		
		l.putConstraint(SpringLayout.WEST, langName1,
                5, SpringLayout.WEST, languageDialogFields);
		l.putConstraint(SpringLayout.NORTH, langName1,
                5, SpringLayout.NORTH, languageDialogFields);
		l.putConstraint(SpringLayout.WEST, langNameField1,
                5, SpringLayout.EAST, langName1);
		l.putConstraint(SpringLayout.NORTH, langNameField1,
                5, SpringLayout.NORTH, languageDialogFields);
		languageDialogFields.add(langName1);
		languageDialogFields.add(langNameField1);
		l.putConstraint(SpringLayout.WEST, langName2,
                5, SpringLayout.WEST, languageDialogFields);
		l.putConstraint(SpringLayout.NORTH, langName2,
                5, SpringLayout.SOUTH, langName1);
		l.putConstraint(SpringLayout.WEST, langNameField2,
                5, SpringLayout.EAST, langName2);
		l.putConstraint(SpringLayout.NORTH, langNameField2,
                5, SpringLayout.SOUTH, langNameField1);
		languageDialogFields.add(langName2);
		languageDialogFields.add(langNameField2);
		
		return languageDialogFields; 
	}
	private JPanel constructLanguageDialogButtons() {
		JPanel languageDialogButtons = new JPanel(new FlowLayout());
		JButton setButton = new JButton("Set");
		setButton.setActionCommand("set");
		setButton.addActionListener(this.actionListener);
		setButton.setToolTipText("Set the languages.");
		languageDialogButtons.add(setButton);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("cancel");
		cancelButton.addActionListener(this.actionListener);
		cancelButton.setToolTipText("Close and don't set the languages.");
		languageDialogButtons.add(cancelButton);
		return languageDialogButtons;
	}
	private JPanel constructLanguageDialogContents() {
		JPanel languageDialogContents = new JPanel(new BorderLayout());
		JPanel languageDialogFields = constructLanguageDialogFields();
		JPanel languageDialogButtons = constructLanguageDialogButtons();
		languageDialogContents.add(languageDialogFields, BorderLayout.CENTER);		
		languageDialogContents.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.PAGE_END);
		languageDialogContents.add(languageDialogButtons, BorderLayout.PAGE_END);
		return languageDialogContents;
	}
	
	public LanguageNameDialog(MainWindow mainWindow) {
		super(mainWindow);
		this.mainWindow = mainWindow;
		final LanguageNameDialog selfRef = this;
		this.setModal(true);
		this.setTitle("Language names");
		this.setPreferredSize(new Dimension(300,200));
		this.setMinimumSize(new Dimension(300,200));
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		this.actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand() == "set") {
					selfRef.set();
				} else if(e.getActionCommand() == "cancel") {
					selfRef.cancel();
				}
			}
		};
		this.addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent e) {
				selfRef.cancel();
			}

			public void windowActivated(WindowEvent e) { }
			public void windowClosed(WindowEvent e) { }
			public void windowDeactivated(WindowEvent e) { }
			public void windowDeiconified(WindowEvent e) { }
			public void windowIconified(WindowEvent e) { }
			public void windowOpened(WindowEvent e) { }
		});
		// Contents.
		
		JPanel languageDialogContents = constructLanguageDialogContents(); 
		this.getContentPane().add(languageDialogContents);		
		this.pack();
		this.setVisible(false);
	}
}
