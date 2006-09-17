// $Id: EntryList.java 3 2006-09-17 13:19:05Z wwwwolf $

package org.beastwithin.conmandictionary;

import javax.swing.*;
import java.util.*;
import org.w3c.dom.*;


public class EntryList extends DefaultListModel {
	static final long serialVersionUID = 1;
	
	// TODO: This is a somewhat ugly way to sort. Is there a more
	// elegant way to do this that didn't involve copying the array?
	public void sort() {
		Object a[] = this.toArray();
		Arrays.sort(a);
		this.removeAllElements();
		for(int i = 0; i < a.length; i++) {
			this.addElement(a[i]);
		}
	}
	public DocumentFragment toXmlElement() {
		DocumentFragment doc = XmlHelper.createXmlDocumentFragment();
		
		Element root = XmlHelper.createXmlElement("definitions");
		for(int i = 0; i < this.size(); i++) {
			Node e = ((Entry)this.getElementAt(i)).toXmlElement().getFirstChild();
			root.appendChild(e);
		}
		doc.appendChild(root);
		return doc;
	}
}
