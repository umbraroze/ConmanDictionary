/*  EntryList.java: For storing entries in a dictionary.
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
 *  $Id: EntryList.java 6 2006-09-28 08:36:23Z wwwwolf $
 */

package org.beastwithin.conmandictionary;

import javax.swing.*;
import java.util.*;
import org.w3c.dom.*;


public class EntryList extends DefaultListModel {
	static final long serialVersionUID = 1;
	private String language = "";
	
	public void setLanguage(String title) {
		this.language = title;
	}
	public String getLanguage() {
		return this.language;
	}
	
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
		root.setAttribute("language",this.language);
		for(int i = 0; i < this.size(); i++) {
			Node e = ((Entry)this.getElementAt(i)).toXmlElement().getFirstChild();
			root.appendChild(e);
		}
		doc.appendChild(root);
		return doc;
	}
}
