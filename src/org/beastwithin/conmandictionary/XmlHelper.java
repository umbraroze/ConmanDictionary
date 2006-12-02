/*  XmlHelper.java: Helpers for XML parsing and generation.
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
 *  $Id: XmlHelper.java 11 2006-12-02 15:27:57Z wwwwolf $
 */

package org.beastwithin.conmandictionary;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * This class has helper methods that assist in parsing and generating
 * the XML files.
 * 
 * TODO: This code is *severely* ugly.
 * 
 * @author wwwwolf
 */
public abstract class XmlHelper {
	public static class XmlLoadingException extends Exception {
		static final long serialVersionUID = 1;
		public XmlLoadingException(String message) {
			super(message);
		}
	}
	public static class XmlSavingException extends Exception {
		static final long serialVersionUID = 1;
		public XmlSavingException(String message) {
			super(message);
		}
	}

	public static void saveCurrentXmlDocument(File targetFile, LanguagePanel dictionaries[])
		throws XmlSavingException {
		
		DocumentBuilder xmlDocumentBuilder = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			xmlDocumentBuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException pce) {
			System.err.println("Error generating XML");
			pce.printStackTrace();
			System.exit(1);
		}
		
		// Time to open the file for writing.
		FileWriter f = null;
		try {
			f = new FileWriter(targetFile);
		} catch (IOException e) {
			throw new XmlSavingException("File was not saved.\n"+
					"Error opening file for writing.");
		}
		

		// Let's build the XML document to save...
		Document toSave = xmlDocumentBuilder.newDocument();
		
		// OK, here's a document root.
		Element rootElt = toSave.createElement("dictionarydatabase");
		// Next we create...
		for(int dictCount = 0; dictCount < dictionaries.length; dictCount++) {
			EntryList el = dictionaries[dictCount].getEntryList();
			
			// ... a list of definitions for each
			Element definitionListElt = toSave.createElement("definitions");
			definitionListElt.setAttribute("language",el.getLanguage());
			
			for(int elementCount = 0; elementCount < el.size(); elementCount++) {
				// And add elements to them.
				Entry entry = (Entry)el.elementAt(elementCount);
				Element entryElt = toSave.createElement("entry");
				
				Element term = toSave.createElement("term");
				term.setTextContent(entry.getTerm());
				Element definition = toSave.createElement("definition");
				definition.setTextContent(entry.getDefinition());
				
				entryElt.appendChild(term);
				entryElt.appendChild(definition);
				
				// Add entry to the definition list...
				definitionListElt.appendChild(entryElt);	
			}
			// ...and a definition list to the document root element.
			rootElt.appendChild(definitionListElt);
		}
		
		// And that's our document.
		toSave.appendChild(rootElt);
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

	public static void saveCurrentXmlDocument(File targetFile, LanguagePanel p1, LanguagePanel p2)
	throws XmlSavingException {
		LanguagePanel p[] = {p1, p2}; 
		saveCurrentXmlDocument(targetFile, p);
	}
	
	public static void loadXmlDocument(File f, LanguagePanel p1, LanguagePanel p2)
		throws XmlLoadingException {
		Document d = null;
		DocumentBuilder b = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			b = dbf.newDocumentBuilder();
			d = b.parse(f);
		} catch(ParserConfigurationException pce) {
			pce.printStackTrace();
			throw new XmlLoadingException("Internal error:\n"+
					"Can't start up XML parser.\n\n" + pce.getMessage());
		} catch(IOException ioe) {
			ioe.printStackTrace();
			throw new XmlLoadingException("Can't open the file "+
					f.toString() + "\n\n" + ioe.getMessage());
		} catch(SAXException saxe) {
			saxe.printStackTrace();
			throw new XmlLoadingException("Can't parse the file "+
					f.toString() + "\nThe file contains invalid XML markup.\n\n" +
					"Parser error message: " + saxe.getMessage());
		}
		NodeList defs = d.getElementsByTagName("definitions");
		if(defs.getLength() < 2) {
			throw new XmlLoadingException("Can't open the file "+
					f.toString() + "\nThis file has less than 2 dictionary lists.\n");
		}
		if(defs.getLength() > 2) {
			throw new XmlLoadingException("Can't open the file "+
					f.toString() + "\nThis file has more than 2 dictionary lists.\n");
		}
		Node p1data = defs.item(0);
		Node p2data = defs.item(1);

		populateLanguagePanelFromXml(p1,p1data);
		populateLanguagePanelFromXml(p2,p2data);
	}
	
	private static void populateLanguagePanelFromXml(LanguagePanel panel, Node xml) {
		// Set the title
		String lang = xml.getAttributes().getNamedItem("language").getTextContent();
		if(lang == null)
			lang = "Lang1";
		panel.setTitle(lang);
		panel.getEntryList().removeAllElements();
		
		Vector<Node> ndl = XmlHelper.vectorifyNodeList(xml.getChildNodes());
		
		// Process each entry.
		for(Node x : ndl) {
			String term = null;
			String definition = null;
			if(x.getNodeName()=="entry") {
				// Okay, we found <entry>, so we find <term> and <definition> children.
				Vector<Node> c = XmlHelper.vectorifyNodeList(x.getChildNodes());
				for(Node y : c) {
					if(y.getNodeName() == "term") {
						term = y.getTextContent();
					}
					if(y.getNodeName() == "definition") {
						definition = y.getTextContent();
					}
				}
				// If we found both, it's time to stick them into our list. 
				if(term != null && definition != null) {
					Entry e = new Entry(term, definition);
					panel.getEntryList().addElement(e);
				}
			}
		}
		
		// Last tidy-ups.
		panel.getEntryList().sort();
		panel.setModified(false);		
	}
	
	
	/**
	 * FIXME: STUPID WORKAROUND for Java bogosity: Convert NodeList into Vector<Node>. 
	 * 
	 * @param list a NodeList.
	 * @return a vector of Nodes
	 */
	public static Vector<Node> vectorifyNodeList(NodeList list) {
		Vector<Node> x = new Vector<Node>();
		for(int i = 0; i < list.getLength(); i++) {
			x.add(list.item(i));
		}
		return x;
	}
}
