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
import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import java.awt.dnd.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

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
	/// The language selection window.
	private LanguageNameDialog languageNameDialog;
	/// Notepad.
	private NotePad notePad;

	private Dictionary model; 	

	private boolean isUnsaved() {
		return getModel().isUnsavedChanges();
	}

	public LanguageNameDialog getLanguageNameDialog() {
		return languageNameDialog;
	}
	
	public void quit() {
		if(isUnsaved()) {
			int resp = JOptionPane.showConfirmDialog(this,
					"There are unsaved changes.\nReally quit?",
					"Really quit?",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if(resp != 0)
				return;
		}
		System.exit(0);
	}

	public void openDocument(File f) {
		try {
			Dictionary.validateFile(f);
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(
					this,
					"Unable to open the file "+f+".\n" + ioe.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			ioe.printStackTrace();
			return;
		} catch (SAXException saxe) {
			JOptionPane.showMessageDialog(
					this,
					"The file format for file "+f+" is invalid.\n" + saxe.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			saxe.printStackTrace();
			return;
		}
		try {
			Dictionary loadedDictionary = Dictionary.loadDocument(f); 
			this.setModel(loadedDictionary);				
		} catch (JAXBException jaxbe) {
			JOptionPane.showMessageDialog(
					this,
					"XML error while loading file:\n"+
					jaxbe.getMessage() +
					"\nFurther details printed at console.",
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			jaxbe.printStackTrace();			
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(
					this,
					"Error reading file:\n"+
					ioe.getMessage() +
					"\nFurther details printed at console.",
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			ioe.printStackTrace();			
		}
		this.setAppTitle(f);		
	}
	
	private class MainMenuListener implements ActionListener {
		private MainWindow mainWindow = null;
		public MainMenuListener(MainWindow mainWindow) {
			this.mainWindow = mainWindow;
		}
		private void doSave() {
			try {
				mainWindow.getModel().saveDocument();
			} catch (IOException ioe) {
				JOptionPane.showMessageDialog(
						mainWindow,
						"File error while saving file:\n"+
						ioe.getMessage(),
						"Error",
						JOptionPane.ERROR_MESSAGE
					);
				ioe.printStackTrace();			
			} catch (JAXBException jaxbe) {
				JOptionPane.showMessageDialog(
						mainWindow,
						"XML error while saving file:\n"+
						jaxbe.getMessage() +
						"\nFurther details printed at console.",
						"Error",
						JOptionPane.ERROR_MESSAGE
					);
				jaxbe.printStackTrace();			
			}
			mainWindow.changesHaveBeenSaved();
			setAppTitle(mainWindow.getModel().getCurrentFile());		
		}
		private void saveAs() {
			final JFileChooser fc = new JFileChooser();
			int ret = fc.showSaveDialog(mainWindow);
			if(ret != JFileChooser.APPROVE_OPTION)
				return;
			mainWindow.getModel().setCurrentFile(fc.getSelectedFile());			
			doSave();
		}
		private void newDocument() {
			if(isUnsaved()) {
				int resp = JOptionPane.showConfirmDialog(
						mainWindow,
						"There are unsaved changes.\nReally clear everything?",
						"Really clear everything?",
						JOptionPane.YES_NO_OPTION);
				if(resp != 0)
					return;
			}
			Dictionary newDocument = new Dictionary();
			mainWindow.setModel(newDocument);
			mainWindow.setAppTitle(null);
		}
		public void actionPerformed(ActionEvent e) {
			String c = e.getActionCommand();
			if(c == "file-quit") {
				mainWindow.quit();
			} else if(c == "file-new") {
				newDocument();
			} else if(c == "file-open") {
				if(isUnsaved()) {
					int resp = JOptionPane.showConfirmDialog(
							mainWindow,
							"There are unsaved changes.\nReally open another file?",
							"Really open another file?",
							JOptionPane.YES_NO_OPTION);
					if(resp != 0)
						return;
				}
				final JFileChooser fc = new JFileChooser();
				int ret = fc.showOpenDialog(mainWindow);
				if(ret != JFileChooser.APPROVE_OPTION)
					return;
				mainWindow.openDocument(fc.getSelectedFile());
			} else if(c == "file-save") {
				// What's the file?
				if(mainWindow.getModel().getCurrentFile() == null) {
					saveAs();
				} else {
					doSave();
				}
			} else if(c == "file-save-as") {
				saveAs();
			} else if(c == "file-export-dictd") {
				final JFileChooser fc = new JFileChooser();
				int ret = fc.showSaveDialog(mainWindow);
				if(ret != JFileChooser.APPROVE_OPTION)
					return;		
				mainWindow.getModel().exportAsDictd(fc.getSelectedFile().getPath());
			} else if(c == "research-notepad") {
				mainWindow.getNotePad().setVisible(true);
			} else if(c == "settings-languagenames") {
				mainWindow.getLanguageNameDialog().open();
			} else if(c == "help-about") {
				ConmanDictionary.showAboutDialog();
			} else {
				System.err.println("WARNING: Unhandled menu item " + c +".");
			}
		}					
	}
	public class MainWindowDropTargetListener implements DropTargetListener {
		public void drop(DropTargetDropEvent dtde) {
			System.err.println(dtde.toString());
		}

		public void dragEnter(DropTargetDragEvent dtde) { }
		public void dragExit(DropTargetEvent dte) { }
		public void dragOver(DropTargetDragEvent dtde) { }
		public void dropActionChanged(DropTargetDragEvent dtde) { }
	}
	
	/**
	 * Constructs the menu bar for the application window.
	 */
	private void constructMenuBar() {
		JMenuItem mi;
		ActionListener ml = new MainMenuListener(this);
		
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

		// Set window title.
		this.setTitle(ConmanDictionary.APP_NAME);
		
		// Set icon.
		this.setIconImage(ConmanDictionary.getAppIcon());

		// We want to use quit() to handle our quitting.
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent e) {
				ConmanDictionary.getMainWindow().quit();
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
		
		this.setDropTarget(new DropTarget(selfRef, new MainWindowDropTargetListener()));

		// And some additional dialogs...
		languageNameDialog = new LanguageNameDialog(this);
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
		this.leftLanguagePanel.getEntryList().setModified(false);
		this.rightLanguagePanel.getEntryList().setModified(false);
	}
	
	public NotePad getNotePad() {
		return notePad;
	}
	/**
	 * Will associate this window (and all subwindows etc) with a Dictionary document.
	 * @param newModel
	 */
	public void setModel(Dictionary newModel) {
		model = newModel;
		leftLanguagePanel.setEntryList(newModel.getDefinitions().get(0));
		rightLanguagePanel.setEntryList(newModel.getDefinitions().get(1));
		notePad.setModel(newModel.getNotePadDocument());
		languageNameDialog.setModel(newModel.getDefinitions());
		// FIXME: Other associations go here!
	}
	public Dictionary getModel() {
		return model;
	}
	/**
	 * Utility method to set the application title.
	 * Uses "/file/name - Appname" format. Use null or file
	 * name with just "" to set to "Appname".
	 * 
	 * @param currentlyOpenFile The file currently opened.
	 */
	public void setAppTitle(File currentlyOpenFile) {
		if(currentlyOpenFile == null || currentlyOpenFile.toString() == "")
			this.setTitle(ConmanDictionary.APP_NAME);
		else
			this.setTitle(currentlyOpenFile.toString() + " - " + ConmanDictionary.APP_NAME);
	}

}