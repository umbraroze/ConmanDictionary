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

//import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class LanguageNameDialog extends JDialog {
	static final long serialVersionUID = 1; 
	private String language1;
	private String language2;
	
	public LanguageNameDialog(JFrame mainWindow, String language1, String language2) {
		super(mainWindow);
		this.language1 = language1;
		this.language2 = language2;
		this.setModal(true);
		this.setTitle("Language names");
		this.setPreferredSize(new Dimension(300,200));
		this.setMinimumSize(new Dimension(300,200));

		BoxLayout l = new BoxLayout(this,BoxLayout.PAGE_AXIS);
		this.getContentPane().setLayout(l);
		
		this.getContentPane().add(new JLabel("Foo"));
		this.getContentPane().add(new JLabel("Foo"));
		this.getContentPane().add(new JLabel("Foo"));
		
		this.pack();
		this.setVisible(true);
	}

	public String getLanguage1() {
		return language1;
	}

	public String getLanguage2() {
		return language2;
	}
}
