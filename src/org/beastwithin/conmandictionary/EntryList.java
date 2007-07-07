/*  EntryList.java: For storing entries in a dictionary.
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

import javax.swing.*;
import java.util.*;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "entry"
})
@XmlRootElement(name="definitions")
public class EntryList extends DefaultListModel {
	static final long serialVersionUID = 1;
	@XmlAttribute
	protected String language = "";
	
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
	
	public Entry search(String term) {
		// FIXME: Linear search should be funny enough in this case,
		// since the list is kept sorted and reasonably small...
		
		// FIXME: For some effin' reason, DefaultListModel isn't Iterable.
		// for(Entry e : this) {	}		
		for(int i = 0; i < this.size(); i++) {
			Entry e = (Entry)this.elementAt(i); 
			if(e.getTerm().contains(term)) 
				return e;
		}
		return null;
	}
}
