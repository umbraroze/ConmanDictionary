// $Id: ConmanDictionary.java 2 2006-09-17 12:33:48Z wwwwolf $

package org.beastwithin.conmandictionary;

import javax.swing.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;

public class ConmanDictionary {
	public final static String APP_NAME = "Conman's Dictionary";
	
	private static class MainThread implements Runnable {
		public void run() {
			ConmanDictionary.bringUpXMLFactories();
			mainWin = new ConmanDictionaryMainWindow();    		
			mainWin.setVisible(true);
		}		
	}
	
	// TODO: The XML saving shit is extremely complicated. There's got to be
	// a more elegant way to do this.
	
	private static Document xmlDocument = null;
	private static DocumentBuilder xmlDocumentBuilder = null;
	public static Document createXmlDocument() {
		return xmlDocumentBuilder.newDocument();
	}
	public static DocumentFragment createXmlDocumentFragment() {
		return xmlDocument.createDocumentFragment();
	}
	public static Element createXmlElement(String name) {
		return xmlDocument.createElement(name);
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

	private static void bringUpXMLFactories() {
		xmlDocument = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			xmlDocumentBuilder = factory.newDocumentBuilder();
			xmlDocument = xmlDocumentBuilder.newDocument(); 
		} catch (ParserConfigurationException pce) {
			System.err.println("Error generating XML");
			pce.printStackTrace();
			System.exit(1);
		}
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

		// Time to open it.
		FileWriter f = null;
		try {
			f = new FileWriter(file);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					mainWin,
					"File was not saved.\n"+
					"Error opening file for writing.",
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			return;
		}

		// Let's build the XML document to save...
		Document toSave = xmlDocumentBuilder.newDocument();
		
		Element root = toSave.createElement("dictionarydatabase");
		Node list1 = mainWin.getLeftLanguagePanel().toXmlElement().getFirstChild();
		toSave.adoptNode(list1);
		root.appendChild(list1);
		Node list2 = mainWin.getRightLanguagePanel().toXmlElement().getFirstChild();
		toSave.adoptNode(list2);
		root.appendChild(list2);
		
		toSave.adoptNode(root);
		toSave.appendChild(root);
		toSave.setXmlStandalone(true);
		
		// Then, we construct this horrenduous monster to save our shit to disk
		TransformerFactory tff = TransformerFactory.newInstance();
		Transformer tf = null;
		DOMSource tfSource = new DOMSource(toSave);
		StreamResult tfDestination = new StreamResult(f);
		
		try {
			tf = tff.newTransformer();
			tf.setOutputProperty(OutputKeys.METHOD, "xml");
	        tf.setOutputProperty(OutputKeys.INDENT, "yes");
	        //tf.setOutputProperty("{ http://xml.apache.org/xslt }indent-amount", "2");
	        tf.transform(tfSource,tfDestination);
		} catch (TransformerConfigurationException tce) {
			JOptionPane.showMessageDialog(
					mainWin,
					"File was not saved properly.\n\n"+
					"A processing error occurred during file save.\n"+
					"Technical information has been printed on console.",
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			tce.printStackTrace();
			return;
		} catch (TransformerException te) {
			JOptionPane.showMessageDialog(
					mainWin,
					"File was not saved properly.\n\n"+
					"A processing error occurred during file save.\n"+
					"Technical information has been printed on console.",
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			te.printStackTrace();
			return;
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
