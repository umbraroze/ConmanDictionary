/*  ConmanDictionaryMainWindow.java: main window class.
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
 *  
 *  $Id: MainWindow.java 15 2006-12-17 12:19:54Z wwwwolf $ 
 */

package org.beastwithin.conmandictionary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The main dictionary window of the application.
 * 
 * @author wwwwolf
 */
public class MainWindow extends JFrame {
	/**
	 * Menu listener for the main window.
	 */
	private class MainWindowMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String c = e.getActionCommand();
			if(c == "file-quit") {
				ConmanDictionary.quit();
			} else if(c == "file-new") {
				ConmanDictionary.newDictionary();
			} else if(c == "file-open") {
				ConmanDictionary.openDictionary();
			} else if(c == "file-save") {
				ConmanDictionary.saveDictionary();
			} else if(c == "file-save-as") {
				ConmanDictionary.saveDictionaryAs();
			} else if(c == "settings-languagenames") {
				ConmanDictionary.setLanguageNames();
			} else if(c == "help-about") {
				ConmanDictionary.showAboutDialog();
			}
		}
	}
		
	static final long serialVersionUID = 1; 
	
	/// The left-side panel showing dictionary entries.
	private LanguagePanel leftLanguagePanel;
	/// The right-side panel showing dictionary entries.
	private LanguagePanel rightLanguagePanel;

	/**
	 * Constructs the menu bar for the application window.
	 */
	private void constructMenuBar() {
		JMenuItem mi;
		MainWindowMenuListener ml = new MainWindowMenuListener();
		
		JMenuBar mb = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		mi = new JMenuItem("New",KeyEvent.VK_N);
		mi.setActionCommand("file-new");
		mi.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		mi.addActionListener(ml);
		fileMenu.add(mi);
		mi = new JMenuItem("Open...",KeyEvent.VK_O);
		mi.setActionCommand("file-open");
		mi.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		mi.addActionListener(ml);
		fileMenu.add(mi);
		mi = new JMenuItem("Save",KeyEvent.VK_S);
		mi.setActionCommand("file-save");
		mi.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		mi.addActionListener(ml);
		fileMenu.add(mi);
		mi = new JMenuItem("Save as...",KeyEvent.VK_A);
		mi.setActionCommand("file-save-as");
		mi.setAccelerator(KeyStroke.getKeyStroke("shift ctrl S"));
		mi.addActionListener(ml);
		fileMenu.add(mi);
		fileMenu.addSeparator();
		mi = new JMenuItem("Quit",KeyEvent.VK_Q);
		mi.setActionCommand("file-quit");
		mi.setAccelerator(KeyStroke.getKeyStroke("ctrl Q"));
		mi.addActionListener(ml);
		fileMenu.add(mi);
		mb.add(fileMenu);
		
		JMenu settingsMenu = new JMenu("Settings");
		settingsMenu.setMnemonic(KeyEvent.VK_S);
		mi = new JMenuItem("Language names...",KeyEvent.VK_N);
		mi.setActionCommand("settings-languagenames");
		mi.addActionListener(ml);
		settingsMenu.add(mi);
		mb.add(settingsMenu);
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		mi = new JMenuItem("About...",KeyEvent.VK_A);
		mi.setActionCommand("help-about");
		mi.addActionListener(ml);
		helpMenu.add(mi);
		mb.add(helpMenu);
		
		this.setJMenuBar(mb);
	}

	/**
	 * Constructs the contents of the application window.
	 */
	private void constructContents() {
		JPanel mainWinContents = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
		
		leftLanguagePanel = new LanguagePanel("Lang1");
		rightLanguagePanel = new LanguagePanel("Lang2");
		
		mainWinContents.add(leftLanguagePanel);
		mainWinContents.add(new JSeparator(JSeparator.VERTICAL));
		mainWinContents.add(rightLanguagePanel);

		this.add(mainWinContents);
		this.pack();
	}

	/**
	 * Creates the main window.
	 */
	public MainWindow() {
		super();

		this.setTitle(ConmanDictionary.APP_NAME);
		// We want to quit when this one's closed.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		constructMenuBar();
		constructContents();
	}

	/**
	 * Get the left-side language panel.
	 * @return the left-side language panel.
	 */
	public LanguagePanel getLeftLanguagePanel() {
		return leftLanguagePanel;
	}

	/**
	 * Get the right-side language panel.
	 * @return the right-side language panel.
	 */
	public LanguagePanel getRightLanguagePanel() {
		return rightLanguagePanel;
	}
	
	public void changesHaveBeenSaved() {
		this.leftLanguagePanel.setModified(false);
		this.rightLanguagePanel.setModified(false);
	}
}