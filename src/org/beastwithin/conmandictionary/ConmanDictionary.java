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
 *  $Id: ConmanDictionary.java 6 2006-09-28 08:36:23Z wwwwolf $  
 */


package org.beastwithin.conmandictionary;

import javax.swing.*;
import java.io.File;
import org.w3c.dom.Node;

/**
 * This is the main class of the program.
 * 
 * @author wwwwolf
 */
public class ConmanDictionary {
	///Application name. This will appear in window titles etc.
	public final static String APP_NAME = "Conman's Dictionary";
	///Application version.
	public final static String APP_VERSION = "version 0.0";
	
	/**
	 * The main program thread. Fired up by the Swing runner utility.
	 */
	private static class MainThread implements Runnable {
		public void run() {
			XmlHelper.bringUpXMLFactories();
			mainWin = new ConmanDictionaryMainWindow();    		
			mainWin.setVisible(true);
		}		
	}
	
	/**
	 * Main window.
	 */
	private static ConmanDictionaryMainWindow mainWin = null;
	
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
		javax.swing.SwingUtilities.invokeLater(new MainThread());
	}
	
	/**
	 * This is used to get the main window that the application uses.
	 * 
	 * @return the application main window object.
	 */
	public static ConmanDictionaryMainWindow getMainWindow() {
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
					JOptionPane.YES_NO_OPTION);
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
		return mainWin.getLeftLanguagePanel().isModified() ||
			mainWin.getRightLanguagePanel().isModified();
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
		System.err.println("openDictionary() unimplemented!");
	}

	private static void doSave() {
		try {
			Node x[] = { mainWin.getLeftLanguagePanel().toXmlElement().getFirstChild(),
	                mainWin.getRightLanguagePanel().toXmlElement().getFirstChild() };
			XmlHelper.saveCurrentXmlDocument(currentFile,x);
		} catch (XmlSavingException e) {
			JOptionPane.showMessageDialog(
					mainWin,
					e.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			e.printStackTrace();
		}
	}
	
	/**
	 * Saves dictionary file.
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
		System.err.println("setLanguageNames() unimplemented!");
	}
	
	/**
	 * Shows "About this application" dialog.
	 */
	public static void showAboutDialog() {
		String appAboutString =
			APP_NAME + "\n" +
			APP_VERSION +
			"\nCopyright © Urpo Lankinen 2006\n\n" +
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
