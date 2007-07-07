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

import java.util.*;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "notepad",
    "definitions"
})

@XmlRootElement(name = "dictionarydatabase")
public class Dictionary {
	@XmlElement(name="notepad", required=false)
    protected NotePad notePad;
    @XmlElement(name="definitions", required=true)
    protected List<EntryList> definitions;
    
    public void setNotePad(NotePad n) {
    	this.notePad = n;
    }
    public void setNotePad(String n) {
    	this.notePad = new NotePad();
    	this.notePad.setText(n);
    }
    public List<EntryList> getDefinitions() {
        if (definitions == null) {
            definitions = new ArrayList<EntryList>();
        }
        return this.definitions;
    }
}
