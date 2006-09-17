// $Id: ConmanDictionary.java 3 2006-09-17 13:19:05Z wwwwolf $

package org.beastwithin.conmandictionary;

import javax.swing.*;
import java.io.File;
import org.w3c.dom.Node;

public class ConmanDictionary {
	public final static String APP_NAME = "Conman's Dictionary";
	
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
	
	public static ConmanDictionaryMainWindow getMainWindow() {
		return mainWin;
	}
	
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
	private static boolean checkUnsavedChanges() {
		return mainWin.getLeftLanguagePanel().isModified() ||
			mainWin.getRightLanguagePanel().isModified();
	}
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
	public static void openDictionary() {
		System.err.println("openDictionary() unimplemented!");
	}
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
	public static void saveDictionaryAs() {
		System.err.println("saveDictionaryAs() unimplemented!");
	}
	public static void showAboutDialog() {
		JOptionPane.showMessageDialog(
				mainWin,
				APP_NAME + "\nCopyright © Urpo Lankinen 2006\nDistributed under GNU GPL",
				"About " + APP_NAME,
				JOptionPane.INFORMATION_MESSAGE
			);
	}
}
