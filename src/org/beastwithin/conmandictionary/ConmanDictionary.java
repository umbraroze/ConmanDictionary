/*  ConmanDictionary.java: Main program.
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
 *  $Id: ConmanDictionary.java 15 2006-12-17 12:19:54Z wwwwolf $  
 */


package org.beastwithin.conmandictionary;

import javax.swing.*;
import java.io.File;

/**
 * Main class of the program.
 * 
 * @author wwwwolf
 */
public class ConmanDictionary {
	///Application name. This will appear in window titles etc.
	public final static String APP_NAME = "Conman's Dictionary";
	///Application version.
	public final static String APP_VERSION = "version 0.9";
		
	/**
	 * The main program thread. Fired up by the Swing runner utility.
	 */
	private static class MainThread implements Runnable {
		String[] args;
		public MainThread(String args[]) {
			super();
			this.args = args;
		}
		public void run() {
			mainWin = new MainWindow();    		
			mainWin.setVisible(true);
			if(args.length == 1) {
				currentFile = new File(args[0]);
				doOpen();
			}
		}
	}
	
	/**
	 * Utility method to set the application title.
	 * Uses "/file/name - Appname" format. Use null or file
	 * name with just "" to set to "Appname".
	 * 
	 * @param currentlyOpenFile The file currently opened.
	 */
	public static void setAppTitle(File currentlyOpenFile) {
		if(currentlyOpenFile == null || currentlyOpenFile.toString() == "")
			mainWin.setTitle(APP_NAME);
		else
			mainWin.setTitle(currentlyOpenFile.toString() + " - " + APP_NAME);
	}
	
	/**
	 * Main window.
	 */
	private static MainWindow mainWin = null;
	
	/**
	 * Currently open file.
	 */
	private static File currentFile = null;
	
	/**
	 * The main program for the application.
	 * 
	 * @param args Program arguments.
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new MainThread(args));
	}
	
	/**
	 * Used to get the main window that the application uses.
	 * 
	 * @return the application main window object.
	 */
	public static MainWindow getMainWindow() {
		return mainWin;
	}
	
	/**
	 * Quits the application. Warns about unsaved changes via dialog
	 * if there are unsaved changes. 
	 */
	public static void quit() {
		if(checkUnsavedChanges()) {
			int resp = JOptionPane.showConfirmDialog(mainWin,
					"There are unsaved changes.\nReally quit?",
					"Really quit?",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if(resp != 0)
				return;
		}
		System.exit(0);
	}
	/**
	 * Checks if there are unsaved changes in the dictionaries.
	 * 
	 * @return true if there are unsaved changes.
	 */
	private static boolean checkUnsavedChanges() {
		return mainWin.getLeftLanguagePanel().getModified() ||
			mainWin.getRightLanguagePanel().getModified();
	}
	
	/**
	 * Clears up the dictionaries. Will prompt if there are unsaved
	 * changes. 
	 */
	public static void newDictionary() {
		if(checkUnsavedChanges()) {
			int resp = JOptionPane.showConfirmDialog(
					mainWin,
					"There are unsaved changes.\nReally clear everything?",
					"Really clear everything?",
					JOptionPane.YES_NO_OPTION);
			if(resp != 0)
				return;
		}
		mainWin.getLeftLanguagePanel().clearList();
		mainWin.getRightLanguagePanel().clearList();
		currentFile = null;
		setAppTitle(null);
	}
	
	/**
	 * Opens a dictionary file. Will prompt if there are unsaved
	 * changes. 
	 */
	public static void openDictionary() {
		if(checkUnsavedChanges()) {
			int resp = JOptionPane.showConfirmDialog(
					mainWin,
					"There are unsaved changes.\nReally open another file?",
					"Really open another file?",
					JOptionPane.YES_NO_OPTION);
			if(resp != 0)
				return;
		}
		final JFileChooser fc = new JFileChooser();
		int ret = fc.showOpenDialog(mainWin);
		if(ret != JFileChooser.APPROVE_OPTION)
			return;
		currentFile = fc.getSelectedFile();
		doOpen();
	}
	
	/**
	 * The actual method that opens the file. Set currentFile
	 * before calling this.
	 */
	private static void doOpen() {
		try {
			XmlHelper.loadXmlDocument(currentFile,
					mainWin.getLeftLanguagePanel(),
					mainWin.getRightLanguagePanel());
		} catch (XmlHelper.XmlLoadingException e) {
			JOptionPane.showMessageDialog(
					mainWin,
					e.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			e.printStackTrace();			
		}
		setAppTitle(currentFile);
	}

	/**
	 * The actual method that saves the file. Set currentFile
	 * before calling this.
	 */
	private static void doSave() {		
		try {
			XmlHelper.saveCurrentXmlDocument(currentFile,
					mainWin.getLeftLanguagePanel(),
					mainWin.getRightLanguagePanel());
			mainWin.changesHaveBeenSaved();
		} catch (XmlHelper.XmlSavingException e) {
			JOptionPane.showMessageDialog(
					mainWin,
					e.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			e.printStackTrace();
		}
		setAppTitle(currentFile);
	}
	
	/**
	 * Saves dictionary file. Will call saveDictionaryAs() if the
	 * currently edited file hasn't been saved before.
	 */
	public static void saveDictionary() {
		// What's the file?
		if(currentFile == null) {
			saveDictionaryAs();
		} else {
			doSave();
		}
	}
	/**
	 * Saves dictionary file with a picked name.
	 */
	public static void saveDictionaryAs() {
		final JFileChooser fc = new JFileChooser();
		int ret = fc.showSaveDialog(mainWin);
		if(ret != JFileChooser.APPROVE_OPTION)
			return;
		currentFile = fc.getSelectedFile();
		
		doSave();
	}
	
	/**
	 * Shows dialog to set the names of the languages being edited.
	 */
	public static void setLanguageNames() {
		LanguageNameDialog ld = new LanguageNameDialog(getMainWindow(),"foo","bar");
		ld.setVisible(true);
		
		System.err.println("Ln1: " + ld.getLanguage1() + "\n" +
				"Ln2: " + ld.getLanguage2());
	}
	
	/**
	 * Shows "About this application" dialog.
	 */
	public static void showAboutDialog() {
		String appAboutString =
			APP_NAME + "\n" +
			APP_VERSION +
			"\nCopyright � Urpo Lankinen 2006\n\n" +
			APP_NAME + " comes with ABSOLUTELY NO WARRANTY.\n\n" +
			"This is free software, and you are welcome to redistribute it\n"+
			"under the terms of GNU General Public Licence as published by\n"+
			"the Free Software Foundation; either version 2 of the License, or\n"+
			"(at your option) any later version.\n" +
			"Please see COPYING file for more details.\n\n";

		JOptionPane.showMessageDialog(
				mainWin,
				appAboutString,
				"About " + APP_NAME,
				JOptionPane.INFORMATION_MESSAGE
			);
	}
}
