/*  MainWindow.java: main window class.
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

import javax.swing.*;
//import java.awt.*;
import java.awt.dnd.*;
import java.awt.event.*;

/**
 * The main dictionary window of the application.
 * 
 * @author wwwwolf
 */
public class MainWindow extends JFrame {		
	static final long serialVersionUID = 1; 
	
	/// The left-side panel showing dictionary entries.
	private LanguagePanel leftLanguagePanel;
	/// The right-side panel showing dictionary entries.
	private LanguagePanel rightLanguagePanel;
	/// Notepad.
	private NotePad notePad;
	
	/**
	 * Constructs the menu bar for the application window.
	 */
	private void constructMenuBar() {
		JMenuItem mi;
		ActionListener ml = new ActionListener() {
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
				} else if(c == "file-export-dictd") {
					ConmanDictionary.exportDictionaryAsDictd();
				} else if(c == "research-notepad") {
					ConmanDictionary.showNotePad();
				} else if(c == "settings-languagenames") {
					ConmanDictionary.setLanguageNames();
				} else if(c == "help-about") {
					ConmanDictionary.showAboutDialog();
				} else {
					System.err.println("WARNING: Unhandled menu item " + c +".");
				}
			}			
		};
		
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
		mi = new JMenuItem("Export to plain text...",KeyEvent.VK_E);
		mi.setActionCommand("file-export-dictd");
		mi.setAccelerator(KeyStroke.getKeyStroke("ctrl E"));
		mi.addActionListener(ml);
		//mi.setEnabled(false);
		fileMenu.add(mi);
		fileMenu.addSeparator();
		mi = new JMenuItem("Quit",KeyEvent.VK_Q);
		mi.setActionCommand("file-quit");
		mi.setAccelerator(KeyStroke.getKeyStroke("ctrl Q"));
		mi.addActionListener(ml);
		fileMenu.add(mi);
		mb.add(fileMenu);

		JMenu researchMenu = new JMenu("Research");
		researchMenu.setMnemonic(KeyEvent.VK_R);
		mi = new JMenuItem("Notepad...",KeyEvent.VK_N);
		mi.setActionCommand("research-notepad");
		mi.addActionListener(ml);
		researchMenu.add(mi);
		mb.add(researchMenu);
		
		JMenu settingsMenu = new JMenu("Settings");
		settingsMenu.setMnemonic(KeyEvent.VK_S);
		mi = new JMenuItem("Language names...",KeyEvent.VK_N);
		mi.setActionCommand("settings-languagenames");
		mi.setEnabled(false);
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
		JPanel mainWinContents = new JPanel();
		BoxLayout l = new BoxLayout(mainWinContents,BoxLayout.X_AXIS);
		mainWinContents.setLayout(l);
		
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
		final MainWindow selfRef = this; 

		this.setTitle(ConmanDictionary.APP_NAME);

		// We want to use quit() to handle our quitting.
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent e) {
				ConmanDictionary.quit();
			}

			public void windowActivated(WindowEvent e) { }
			public void windowClosed(WindowEvent e) { }
			public void windowDeactivated(WindowEvent e) { }
			public void windowDeiconified(WindowEvent e) { }
			public void windowIconified(WindowEvent e) { }
			public void windowOpened(WindowEvent e) { }
		});

		// Construct the window contents.
		constructMenuBar();
		constructContents();
		
		// Drag and drop. This isn't very useful yet, but you can apparently
		// drop stuff on the window and it kind of registers it. Or would,
		// if we'd not comment it out. 
		
		this.setDropTarget(new DropTarget(selfRef, new DropTargetListener() {
			public void drop(DropTargetDropEvent dtde) {
				System.out.println(dtde.toString());
			}

			public void dragEnter(DropTargetDragEvent dtde) { }
			public void dragExit(DropTargetEvent dte) { }
			public void dragOver(DropTargetDragEvent dtde) { }
			public void dropActionChanged(DropTargetDragEvent dtde) { }
		}));
		
		// We want a notepad too.
		notePad = new NotePad(); 
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
	
	public void showNotePad() {
		notePad.setVisible(true);
	}
	
}