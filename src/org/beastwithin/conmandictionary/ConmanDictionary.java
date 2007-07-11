/*  ConmanDictionary.java: Main program.
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


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.xml.sax.SAXException;

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
			Dictionary.validateFile(currentFile);
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(
					mainWin,
					"Unable to open the file "+currentFile+".\n" + ioe.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			ioe.printStackTrace();
			return;
		} catch (SAXException saxe) {
			JOptionPane.showMessageDialog(
					mainWin,
					"The file format for file "+currentFile+" is invalid.\n" + saxe.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			saxe.printStackTrace();
			return;
		}
		try {
			JAXBContext jc = JAXBContext.newInstance(Dictionary.class);
			Unmarshaller um = jc.createUnmarshaller();
			
			Dictionary dictionaryFile = (Dictionary) um.unmarshal(currentFile);
			
			LanguagePanel leftPanel = mainWin.getLeftLanguagePanel();
			LanguagePanel rightPanel = mainWin.getRightLanguagePanel();
			NotePad notePad = mainWin.getNotePad();
						
			// Get list contents
			EntryList leftEntryList = leftPanel.getEntryList();
			EntryList leftEntryListInFile = dictionaryFile.getDefinitions().get(0);
			EntryList rightEntryList = rightPanel.getEntryList();
			EntryList rightEntryListInFile = dictionaryFile.getDefinitions().get(1);
			leftEntryList.replicateContentsFrom(leftEntryListInFile);
			rightEntryList.replicateContentsFrom(rightEntryListInFile);
			// Set titles
			leftPanel.setLanguage(leftEntryListInFile.getLanguage());
			rightPanel.setLanguage(rightEntryListInFile.getLanguage());
			// Set the notepad
			notePad.setText(dictionaryFile.getNotePad());			
		} catch (JAXBException jaxbe) {
			JOptionPane.showMessageDialog(
					mainWin,
					"XML error while loading file:\n"+
					jaxbe.getMessage() +
					"\nFurther details printed at console.",
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			jaxbe.printStackTrace();			
		}
		setAppTitle(currentFile);
	}

	/**
	 * The actual method that saves the file. Set currentFile
	 * before calling this.
	 */
	private static void doSave() {		
		try {
			Dictionary dictionaryFile = new Dictionary();
			
			List<EntryList> l = dictionaryFile.getDefinitions();
			EntryList deflist1 = new EntryList(); 
			EntryList deflist2 = new EntryList();
			deflist1.replicateContentsFrom(mainWin.getLeftLanguagePanel().getEntryList());
			deflist2.replicateContentsFrom(mainWin.getRightLanguagePanel().getEntryList());			
			deflist1.setLanguage(mainWin.getLeftLanguagePanel().getLanguage());
			deflist2.setLanguage(mainWin.getRightLanguagePanel().getLanguage());
			l.add(deflist1);
			l.add(deflist2);
			dictionaryFile.setNotePad(mainWin.getNotePad().getText());
			
			JAXBContext jc = JAXBContext.newInstance(dictionaryFile.getClass());
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
			FileWriter f = new FileWriter(currentFile);
			m.marshal(dictionaryFile, f);
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(
					mainWin,
					"File error while saving file:\n"+
					ioe.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			ioe.printStackTrace();			
		} catch (JAXBException jaxbe) {
			JOptionPane.showMessageDialog(
					mainWin,
					"XML error while saving file:\n"+
					jaxbe.getMessage() +
					"\nFurther details printed at console.",
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			jaxbe.printStackTrace();			
		}
		mainWin.changesHaveBeenSaved();
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
	 * Saves dictionary file with a picked name.
	 */
	public static void exportDictionaryAsDictd() {
		final JFileChooser fc = new JFileChooser();
		int ret = fc.showSaveDialog(mainWin);
		if(ret != JFileChooser.APPROVE_OPTION)
			return;		
		ExportHelper.exportAsDictd(fc.getSelectedFile().getPath());
	}
	
	/**
	 * Shows dialog to set the names of the languages being edited.
	 */
	public static void showLanguageNamesDialog() {
		LanguageNameDialog ld = new LanguageNameDialog(getMainWindow());
		ld.setVisible(true);		
	}
	/**
	 * Shows the notepad dialog.
	 */
	public static void showNotePad() {
		mainWin.getNotePad().setVisible(true);
	}

	/**
	 * Shows "About this application" dialog.
	 */
	public static void showAboutDialog() {
		String appAboutString =
			APP_NAME + "\n" +
			APP_VERSION +
			"\nCopyright \u00a9 Urpo Lankinen 2006,2007\n\n" +
			APP_NAME + " comes with ABSOLUTELY NO WARRANTY.\n\n" +
			"This is free software, and you are welcome to redistribute it\n"+
			"under the terms of GNU General Public Licence as published by\n"+
			"the Free Software Foundation; either version 3 of the License, or\n"+
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
