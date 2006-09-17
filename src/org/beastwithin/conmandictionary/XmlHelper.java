// $Id: XmlHelper.java 3 2006-09-17 13:19:05Z wwwwolf $

package org.beastwithin.conmandictionary;

import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

public abstract class XmlHelper {
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
	public static void bringUpXMLFactories() {
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
	public static Document newDocument() {
		return xmlDocumentBuilder.newDocument();
	}
	
	public static void saveCurrentXmlDocument(File targetFile, Node dictionaries[])
		throws XmlSavingException {
		// Time to open the file for writing.
		FileWriter f = null;
		try {
			f = new FileWriter(targetFile);
		} catch (IOException e) {
			throw new XmlSavingException("File was not saved.\n"+
					"Error opening file for writing.");
		}

		
		// Let's build the XML document to save...
		Document toSave = XmlHelper.newDocument();
		
		Element root = toSave.createElement("dictionarydatabase");
		for(int i = 0; i < dictionaries.length; i++) {
			Node list = dictionaries[i];
			toSave.adoptNode(list);
			root.appendChild(list);
		}
		
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
			tce.printStackTrace();
			throw new XmlSavingException("File was not saved properly.\n\n"+
					"A processing error occurred during file save.\n"+
					"Technical information has been printed on console.");
		} catch (TransformerException te) {
			te.printStackTrace();
			throw new XmlSavingException("File was not saved properly.\n\n"+
					"A processing error occurred during file save.\n"+
					"Technical information has been printed on console.");
		}
	}
}
