/*  Dictionary.java: Class that represents the dictionary data.
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

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "notePad",
    "definitions"
})
@XmlRootElement(name = "dictionarydatabase")
public class Dictionary {
	private class NotePadDocument {
		protected String text;
		public NotePadDocument() {
			this.text = "";
		}
		public NotePadDocument(String s) {
			this.text = s;
		}
		public void setText(String s) {
			this.text = s;
		}
		public String getText() {
			return text;
		}
	}
	
	@XmlTransient
    private NotePadDocument notePad;
    
    @XmlElement(name="definitions", required=true)
    protected EntryList[] definitions;
      
    public Dictionary() {
    	this.notePad = new NotePadDocument();
    	this.definitions = new EntryList[2];
    	this.definitions[0] = new EntryList();
    	this.definitions[1] = new EntryList();
    }

    @XmlElement(name="notepad", required=false, type=String.class)
    public void setNotePad(String n) {
    	this.notePad = new NotePadDocument(n);
    }
    public String getNotePad() {
    	return this.notePad.getText();
    }
    public EntryList[] getDefinitions() {
        if (definitions == null) {
            definitions = new EntryList[2];
        	definitions[0] = new EntryList();
        	definitions[1] = new EntryList();
        }
        return this.definitions;
    }
}
