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
 *  $Id: XmlHelper.java 7 2006-09-28 11:09:53Z wwwwolf $
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
	public static void bringUpXmlFactories() {
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
		
		p1.loadContentsFromXml(p1data);
		p2.loadContentsFromXml(p2data);		
	}
	/**
	 * STUPID WORKAROUND for Java bogosity: Convert NodeList into Vector<Node>. 
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
