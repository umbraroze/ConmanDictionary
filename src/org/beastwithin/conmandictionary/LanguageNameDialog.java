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

	public void setLanguages() {
		mainWindow.getLeftLanguagePanel().setLanguage(langNameField1.getText());
		mainWindow.getRightLanguagePanel().setLanguage(langNameField2.getText());
	}
	public void close() {
		this.dispose();
		this.setVisible(false);
	}
	
	public LanguageNameDialog(MainWindow mainWindow) {
		super(mainWindow);
		final LanguageNameDialog selfRef = this;
		this.setModal(true);
		this.setTitle("Language names");
		this.setPreferredSize(new Dimension(300,200));
		this.setMinimumSize(new Dimension(300,200));
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		this.actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand() == "set") {
					selfRef.setLanguages();
					selfRef.close();
				} else if(e.getActionCommand() == "cancel") {
					selfRef.close();
				}
			}
		};
		// Contents.
		JPanel languageDialogContents = new JPanel();
		BoxLayout l = new BoxLayout(languageDialogContents,BoxLayout.Y_AXIS);
		languageDialogContents.setLayout(l);

		langNameField1 = new JTextField();
		langNameField2 = new JTextField();
		langNameField1.setText(mainWindow.getLeftLanguagePanel().getLanguage());
		langNameField2.setText(mainWindow.getRightLanguagePanel().getLanguage());

		JPanel languageDialogFields = new JPanel();
		GridLayout fl = new GridLayout(2,2);
		languageDialogFields.setLayout(fl);
		languageDialogFields.add(new JLabel("Language 1:"));
		languageDialogFields.add(langNameField1);
		languageDialogFields.add(new JLabel("Language 2:"));
		languageDialogFields.add(langNameField2);		
		languageDialogContents.add(languageDialogFields);
		
		languageDialogContents.add(new JSeparator(JSeparator.HORIZONTAL));
		
		JPanel buts = new JPanel(new FlowLayout());
		JButton setButton = new JButton("Set");
		setButton.setActionCommand("set");
		setButton.addActionListener(this.actionListener);
		setButton.setToolTipText("Set the languages.");
		buts.add(setButton);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("cancel");
		cancelButton.addActionListener(this.actionListener);
		cancelButton.setToolTipText("Close and don't set the languages.");
		buts.add(cancelButton);

		// ...and all buttons are done.	
		languageDialogContents.add(buts);

		this.getContentPane().add(languageDialogContents);		
		this.pack();
		this.setVisible(true);
	}
}
