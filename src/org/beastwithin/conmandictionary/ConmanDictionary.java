// $Id: ConmanDictionary.java 5 2006-09-22 07:18:57Z wwwwolf $

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
	/**
	 * Application name. This will appear in window titles etc.
	 */
	public final static String APP_NAME = "Conman's Dictionary";
	
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
	}
	/**
	 * Opens a dictionary file. Will prompt if there are unsaved
	 * changes. 
	 */
	public static void openDictionary() {
		System.err.println("openDictionary() unimplemented!");
	}
	/**
	 * Saves dictionary file.
	 */
	public static void saveDictionary() {
		// What's the file?

		final JFileChooser fc = new JFileChooser();
		int ret = fc.showSaveDialog(mainWin);
		if(ret != JFileChooser.APPROVE_OPTION)
			return;
		File file = fc.getSelectedFile();

		try {
			Node x[] = { mainWin.getLeftLanguagePanel().toXmlElement().getFirstChild(),
	                mainWin.getRightLanguagePanel().toXmlElement().getFirstChild() };
			XmlHelper.saveCurrentXmlDocument(file,x);
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
	 * Saves dictionary file with a picked name.
	 */
	public static void saveDictionaryAs() {
		System.err.println("saveDictionaryAs() unimplemented!");
	}
	/**
	 * Shows "About this application" dialog.
	 */
	public static void showAboutDialog() {
		JOptionPane.showMessageDialog(
				mainWin,
				APP_NAME + "\nCopyright © Urpo Lankinen 2006\nDistributed under GNU GPL",
				"About " + APP_NAME,
				JOptionPane.INFORMATION_MESSAGE
			);
	}
}
